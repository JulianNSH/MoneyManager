package github.julianNSH.moneymanager.overview;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;

public class OverviewFragment extends Fragment {
    ////////////////////////////////////////////////////////////////Chart Elements
    private final int MAX_X_VALUE = 7;
    private final int GROUPS = 2;
    private static String GROUP_1_LABEL;
    private static String GROUP_2_LABEL;
    private static final float BAR_SPACE = 0.05f;
    private static final float BAR_WIDTH = 0.2f;
    private BarChart chart;

    ////////////////////////////////////////////////////////////////Recycler Elements
    private ArrayList<OverviewModelClass> overviewModelClasses;
    private RecyclerView recyclerView;
    private OverviewAdapter overviewAdapter;

    //////////////////////////
    private DatabaseClass databaseClass;
    private TextView incomeOverview, outgoingOverview, inOutView;
    private DatePickerDialog datePicker;
    private Button overviewDateButton;
    private Calendar date;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_overview, container, false);

        //////////////DATE PICKER
        date = Calendar.getInstance();
        final String[] monthsOfYear = {"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
                "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"};
        overviewDateButton = root.findViewById(R.id.btn_date);
        showOverviewData(root, date.get(Calendar.MONTH), date.get(Calendar.YEAR));

        if(overviewDateButton.getText() =="")
            overviewDateButton.setText(monthsOfYear[date.get(Calendar.MONTH)]+ " " + date.get(Calendar.YEAR));
        overviewDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(root.getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        overviewDateButton.setText(monthsOfYear[month] + " " + year);
                        showOverviewData(root, month, year);
                    }
                }, year, month, day);

                //android.R.style.Theme_Holo_Dialog,
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);

                datePicker.show();

            }
        });


        return root;
    }


    @SuppressLint({"ResourceType", "SetTextI18n", "DefaultLocale"})
    public void showOverviewData(View view, int month, int year){
        String date = String.format("%04d-%02d", year, month+1);
        databaseClass = new DatabaseClass(getContext());
        overviewModelClasses = databaseClass.getOverviewData(date);
        float totalIncome=databaseClass.getTotalIncome(date);
        float totalOutgoing=databaseClass.getTotalOutgoing(date);
        float inOutRatio;
        //TOP LAYOUT 
        if(overviewModelClasses!=null) {
            incomeOverview = view.findViewById(R.id.incomeOverview);
            incomeOverview.setText("+"+totalIncome +" "+ getResources().getString(R.string.currency));
            outgoingOverview = view.findViewById(R.id.outgoingOverview);
            outgoingOverview.setText("-"+totalOutgoing +" "+ getResources().getString(R.string.currency));

            //Exclude possibility of divide by zero
            if(totalIncome==0) {
                inOutRatio = totalOutgoing * 100;
            } else {inOutRatio = totalOutgoing*100/totalIncome;}

            inOutView = view.findViewById(R.id.in_out_percent);
            inOutView.setText(String.format("%3.1f",inOutRatio)+"%");
            if(inOutRatio<=50){
                inOutView.setTextColor(getResources().getColor(R.color.lvl1));
                inOutView.getBackground().setColorFilter(getResources().getColor(R.color.lvl1),
                        PorterDuff.Mode.SRC_ATOP);
            }
            if(inOutRatio>50 && inOutRatio<=80){
                inOutView.setTextColor(getResources().getColor(R.color.lvl2));
                inOutView.getBackground().setColorFilter(getResources().getColor(R.color.lvl2),
                        PorterDuff.Mode.SRC_ATOP);
            }
            if(inOutRatio>80){
                inOutView.setTextColor(getResources().getColor(R.color.lvl3));
                inOutView.getBackground().setColorFilter(getResources().getColor(R.color.lvl3),
                        PorterDuff.Mode.SRC_ATOP);
            }
            if (inOutRatio>100)inOutView.setText(">100%");
        }
        
        ////RECICLERVIEW
        recyclerView = view.findViewById(R.id.rvTransaction);

        if(overviewModelClasses!=null) {
            Collections.sort(overviewModelClasses);
            for (int i = 0; i <overviewModelClasses.size(); i++) {
                switch (overviewModelClasses.get(i).getTvDomain()){
                    case "income":
                        overviewModelClasses.get(i).setTvDomain(getResources().getString(R.string.income));
                        overviewModelClasses.get(i).setIvFigure(R.drawable.ic_up);
                        break;
                    case "outgoing":
                        overviewModelClasses.get(i).setTvDomain(getResources().getString(R.string.outgoings));
                        overviewModelClasses.get(i).setIvFigure(R.drawable.ic_down);
                        break;
                    default:
                        overviewModelClasses.get(i).setTvDomain("Scop");
                        overviewModelClasses.get(i).setIvFigure(R.drawable.ic_flat);
                }
            }
        }
        overviewAdapter = new OverviewAdapter(view.getContext(),overviewModelClasses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(overviewAdapter);



        /////CHART
        chart = view.findViewById(R.id.fragment_groupedbarchart_chart);
        BarData data = createChartData(month, year);
        configureChartAppearance(month);
        prepareChartData(data);
    }

    void configureChartAppearance(int month) {
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getDescription().setEnabled(false);

        //TODO fix chart xAxis
        final HashMap<Integer, String> months = new HashMap<>();
        months.put(1, "Ian");
        months.put(2, "Feb");
        months.put(3, "Mar");
        months.put(4, "Apr");
        months.put(5, "Mai");
        months.put(6, "Iun");
        months.put(7, "Iul");
        months.put(8, "Aug");
        months.put(9, "Sep");
        months.put(10, "Oct");
        months.put(11, "Noi");
        months.put(12, "Dec");

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months.get((int)value);
            }

        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(MAX_X_VALUE);
    }

    @SuppressLint("DefaultLocale")
    BarData createChartData(int month, int year) {
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        date = Calendar.getInstance();
        for (int i = 0; i < MAX_X_VALUE; i++) {

            values1.add(new BarEntry(i, databaseClass.getTotalIncome(String.format("%04d-%02d", year, month+i-2))));
            values2.add(new BarEntry(i, databaseClass.getTotalOutgoing(String.format("%04d-%02d", year, month+i-2))));
        }
        GROUP_1_LABEL = getResources().getString(R.string.income);
        GROUP_2_LABEL = getResources().getString(R.string.outgoings);
        BarDataSet set1 = new BarDataSet(values1, GROUP_1_LABEL);
        BarDataSet set2 = new BarDataSet(values2, GROUP_2_LABEL);
        set1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[2]);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);

        return data;
    }

    void prepareChartData(BarData data) {
        chart.setData(data);

        chart.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        chart.groupBars(0, groupSpace, BAR_SPACE);

        chart.invalidate();
    }
}
