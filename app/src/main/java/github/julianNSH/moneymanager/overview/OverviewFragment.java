package github.julianNSH.moneymanager.overview;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.*;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.julianNSH.moneymanager.CustomDateParser;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;
import github.julianNSH.moneymanager.overview.SortData.SortMode;

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
    private Button overviewDateButton, nextMonth, prevMonth;
    private Calendar date;
    private int  currentMonth, currentYear;

    ///////////////////////////////////
    private String[] sortParameters = {"Perioada Desc.", "Perioada Asc." , "Suma Desc.", "Suma Asc.","A-Z","Z-A"};
    private String sortParamKey;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "NonConstantResourceId", "DefaultLocale"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_overview, container, false);


        //////////////DATE PICKER
        date = Calendar.getInstance();

        currentMonth = date.get(Calendar.MONTH)+1;
        currentYear = date.get(Calendar.YEAR);

        sortParamKey = "Perioada Desc.";
        showOverviewData(root, currentMonth, currentYear,sortParamKey); //on first run

        overviewDateButton = root.findViewById(R.id.btn_date);
        nextMonth = root.findViewById(R.id.btn_date_next);
        prevMonth = root.findViewById(R.id.btn_date_prev);

        //move to next month
        nextMonth.setOnClickListener(v -> {
            if (currentMonth==12){
                currentMonth = 1;
                currentYear++;
            } else {currentMonth++;}
            overviewDateButton.setText(CustomDateParser.customDateParser(String.format("%04d-%02d",currentYear,currentMonth)));
            showOverviewData(root, currentMonth, currentYear,sortParamKey);
        });
        //move to previous month
        prevMonth.setOnClickListener(v -> {
            if (currentMonth==1){
                currentMonth = 12;
                currentYear--;
            } else {currentMonth--;}
            overviewDateButton.setText(CustomDateParser.customDateParser(String.format("%04d-%02d", currentYear, currentMonth)));
            showOverviewData(root, currentMonth,currentYear,sortParamKey);
        });

        if(overviewDateButton.getText() =="")
            overviewDateButton.setText(CustomDateParser.customDateParser(String.format("%04d-%02d", currentYear, currentMonth)));
        overviewDateButton.setOnClickListener(v -> {
            int day = date.get(Calendar.DAY_OF_MONTH);
            int month = date.get(Calendar.MONTH);
            int year = date.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(root.getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, (view, year1, month1, dayOfMonth) -> {
                currentMonth = month1 +1;
                currentYear = year1;
                overviewDateButton.setText(CustomDateParser.customDateParser(String.format("%04d-%02d", currentYear, currentMonth)));
                showOverviewData(root, currentMonth, currentYear,sortParamKey);
            }, year, month, day);

            //android.R.style.Theme_Holo_Dialog,
            datePicker.getDatePicker().setSpinnersShown(true);
            datePicker.getDatePicker().setCalendarViewShown(false);

            datePicker.show();

        });

        ////On sort spinner click
        Spinner spinner = root.findViewById(R.id.spinnerSort);
        ArrayAdapter arrayAdapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_spinner_dropdown_item, sortParameters);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortParamKey = sortParameters[position];
                showOverviewData(root,currentMonth,currentYear,sortParamKey);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    @SuppressLint({"ResourceType", "SetTextI18n", "DefaultLocale"})
    public void showOverviewData(View view, int month, int year, String sortParam){
        String date = String.format("%04d-%02d", year, month);
        databaseClass = new DatabaseClass(getContext());
        overviewModelClasses = databaseClass.getOverviewData(date);
        float totalIncome=databaseClass.getTotalIncome(date);
        float totalOutgoing=databaseClass.getTotalOutgoing(date);
        float inOutRatio;

        TextView infoText = view.findViewById(R.id.infoText);
        LinearLayout linearLayoutChart = view.findViewById(R.id.barChartLayout);
        LinearLayout linearLayoutOverview = view.findViewById(R.id.overviewLayout);
        LinearLayout parentLayout = view.findViewById(R.id.parentLayout);

        if(overviewModelClasses.size()==0){
            parentLayout.setGravity(Gravity.CENTER_VERTICAL);
            infoText.setVisibility(LinearLayout.VISIBLE);
            linearLayoutChart.setVisibility(LinearLayout.GONE);
            linearLayoutOverview.setVisibility(LinearLayout.GONE);
        } else {
            linearLayoutChart.setVisibility(LinearLayout.VISIBLE);
            infoText.setVisibility(LinearLayout.GONE);
            linearLayoutOverview.setVisibility(LinearLayout.VISIBLE);
        }

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

            if(sortParam.equals("Perioada Asc.")) Collections.sort(overviewModelClasses, new SortMode((byte) 2));
            if(sortParam.equals("Perioada Desc.")) Collections.sort(overviewModelClasses, new SortMode((byte)3));
            if(sortParam.equals("Suma Asc.")) Collections.sort(overviewModelClasses, new SortMode((byte) 0));
            if(sortParam.equals("Suma Desc.")) Collections.sort(overviewModelClasses, new SortMode((byte) 1));
            if(sortParam.equals("A-Z")) Collections.sort(overviewModelClasses, new SortMode((byte)4));
            if(sortParam.equals("Z-A")) Collections.sort(overviewModelClasses, new SortMode((byte)5));

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
        configureChartAppearance();
        prepareChartData(data);
    }

    @SuppressLint("DefaultLocale")
    void configureChartAppearance() {
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(new LargeValueFormatter());

        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(MAX_X_VALUE);
        chart.setDrawBorders(true);
        chart.setDrawGridBackground(true);
    }

    @SuppressLint("DefaultLocale")
    BarData createChartData(int month, int year) {
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        int dYear, dMonth;


        date = Calendar.getInstance();
        for (int i = 0; i < MAX_X_VALUE; i++) {
            dYear=year; dMonth=month;
            if(month==3){ if (i==0){ dYear--; dMonth = 15; } }
            if(month==2){ if (i<=1){ dYear--; dMonth = 14;} }
            if(month==1){ if (i<=2){ dYear--; dMonth = 13; } }
            if(month==12){ if (i>=4){ dYear++; dMonth = 0; } }
            if(month==11){ if (i>=5){ dYear++; dMonth = -1;} }
            if(month==10){ if (i==6){ dYear++; dMonth = -2; } }

            values1.add(new BarEntry(i, databaseClass.getTotalIncome(String.format("%04d-%02d", dYear, dMonth+i-3))));
            values2.add(new BarEntry(i, databaseClass.getTotalOutgoing(String.format("%04d-%02d", dYear, dMonth+i-3))));
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
        data.setValueFormatter(new LargeValueFormatter());

        ArrayList<String> months = new ArrayList<>();
        for (int i=1; i<=MAX_X_VALUE; i++){
            months.add(CustomDateParser.customDateParser(String.format("%04d-%02d-00",year,month+i-3),"MMM"));
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new MyAxysValueFormatter(months));
        return data;
    }

    void prepareChartData(BarData data) {
        chart.setData(data);

        chart.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        chart.groupBars(0, groupSpace, BAR_SPACE);

        chart.invalidate();
    }
    public static class MyAxysValueFormatter extends IndexAxisValueFormatter {
        private List labels;

        public MyAxysValueFormatter(List<String> labels){
            this.labels=labels;
        }
        @Override
        public String getFormattedValue(float value) {
            try {
                int index = (int) value;
                return String.valueOf(labels.get(index));
            } catch (Exception e) {
                return "";
            }
        }
    }
}
