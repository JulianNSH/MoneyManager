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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

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
    private static final int MAX_X_VALUE = 7;
    private static final int MAX_Y_VALUE = 20;
    private static final int MIN_Y_VALUE = 12;
    private static final int GROUPS = 2;
    private static final String GROUP_1_LABEL = "Venit";
    private static final String GROUP_2_LABEL = "Cheltuieli";
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
        overviewDateButton = (Button) root.findViewById(R.id.btn_date);
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
        String date = month+1+"/"+year;
        databaseClass = new DatabaseClass(getContext());
        overviewModelClasses = databaseClass.getOverviewData(date);
        float totalIncome=databaseClass.getTotalIncome(date);
        float totalOutgoing=databaseClass.getTotalOutgoing(date);
        float inOutRatio;
        //TOP LAYOUT 
        if(overviewModelClasses!=null) {
            incomeOverview = (TextView) view.findViewById(R.id.incomeOverview);
            incomeOverview.setText("+"+totalIncome +" "+ getResources().getString(R.string.currency));
            outgoingOverview = (TextView) view.findViewById(R.id.outgoingOverview);
            outgoingOverview.setText("-"+totalOutgoing +" "+ getResources().getString(R.string.currency));

            //Exclude possibility of divide by zero
            if(totalIncome==0) {
                inOutRatio = totalOutgoing * 100;
            } else {inOutRatio = totalOutgoing*100/totalIncome;}

            inOutView = (TextView) view.findViewById(R.id.in_out_percent);
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
        recyclerView = (RecyclerView) view.findViewById(R.id.rvTransaction);

        if(overviewModelClasses!=null) {
            Collections.sort(overviewModelClasses);
            for (int i = 0; i <overviewModelClasses.size(); i++) {
                switch (overviewModelClasses.get(i).getTvDomain()){
                    case "income":
                        overviewModelClasses.get(i).setTvDomain("Venit");
                        overviewModelClasses.get(i).setIvFigure(R.drawable.ic_up);
                        break;
                    case "outgoing":
                        overviewModelClasses.get(i).setTvDomain("Cheltuieli");
                        overviewModelClasses.get(i).setIvFigure(R.drawable.ic_down);
                        break;
                    default:
                        overviewModelClasses.get(i).setTvDomain("Altele");
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

        final String[] months1 = {"Ian", "Feb", "Mar", "Apr", "Mai", "Iun", "Iul", "Aug", "Sep",
                "Oct", "Noi", "Dec"};

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return months1[(int)value+month];
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

    BarData createChartData(int month, int year) {
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        date = Calendar.getInstance();
        for (int i = 0; i < MAX_X_VALUE; i++) {

            values1.add(new BarEntry(i, databaseClass.getTotalIncome(month+i-2+"/"+year)));
            values2.add(new BarEntry(i, databaseClass.getTotalOutgoing(month+i-2+"/"+year)));
        }

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
