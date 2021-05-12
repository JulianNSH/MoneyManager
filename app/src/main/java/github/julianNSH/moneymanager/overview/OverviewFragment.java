package github.julianNSH.moneymanager.overview;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.julianNSH.moneymanager.R;

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

    DatePickerDialog datePicker;
    Button overviewDateButton;

    ////////////////////////////////////////////////////////////////Recycler Elements
    private ArrayList<OverviewModelClass> overviewModelClasses;
    private RecyclerView recyclerView;
    private OverviewAdapter overviewAdapter;

    private String domain;
    private Integer[]  image= {R.drawable.ic_up, R.drawable.ic_down,R.drawable.ic_down,R.drawable.ic_flat,
            R.drawable.ic_down,R.drawable.ic_flat,R.drawable.ic_down,R.drawable.ic_flat,R.drawable.ic_up};
    private  String[]  title= {"Salariu","Alimente","Servicii Comunale","Transfer","Îmbrăcăminte",
            "Transfer","Cadou","Transfer","Cash-Back"};
    private float[] amount = {13710, 1000, 2500, 5000, 1500,3000, 700, 1000, 1500};
    private String[] comment = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur",
            " Excepteur sint occaecat cupidatat non proident",
            "sunt in culpa qui officia deserunt mollit anim id est laborum."," "," "," "};


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_overview, container, false);
        View statisticsView = inflater.inflate(R.layout.fragment_statistics, container, false);

        //////////////DATE PICKER
        final Calendar date = Calendar.getInstance();
        final String[] monthsOfYear = {"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
                "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"};
        overviewDateButton = (Button) root.findViewById(R.id.btn_date);
        overviewDateButton.setText(monthsOfYear[date.get(Calendar.MONTH)]+ " " + date.get(Calendar.YEAR));
        overviewDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(root.getContext(), android.R.style.Theme_Holo_Dialog,new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        overviewDateButton.setText(monthsOfYear[month] + " " + year);
                    }
                }, year, month, day);

                //android.R.style.Theme_Holo_Dialog,
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);

                datePicker.show();

            }
        });

        ////RECICLERVIEW
        recyclerView = (RecyclerView) root.findViewById(R.id.rvTransaction);

        overviewModelClasses = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            switch (image[i]){
                case R.drawable.ic_up:
                    domain = "Venit";
                    break;
                case R.drawable.ic_down:
                    domain = "Cheltuieli";
                    break;
                default:
                    domain = "Altele";
            }

            @SuppressLint("SimpleDateFormat") OverviewModelClass listModelClass = new OverviewModelClass(domain,
                    image[i],title[i], amount[i], String.valueOf(LocalTime.now()), String.valueOf(LocalDate.now()),
                    comment[i]);

            overviewModelClasses.add(listModelClass);
        }
        overviewAdapter = new OverviewAdapter(root.getContext(),overviewModelClasses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(overviewAdapter);



        /////CHART
        chart = root.findViewById(R.id.fragment_groupedbarchart_chart);
        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);

        return root;
    }

    void configureChartAppearance() {
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getDescription().setEnabled(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat mFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
            @Override
            public String getFormattedValue(float value) {
                long millis = (long) value*1000L;
                return mFormat.format(new Date(millis));
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

    BarData createChartData() {
        Random r = new Random();
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        for (int i = 0; i < MAX_X_VALUE; i++) {
            if(i==3){

                values1.add(new BarEntry(i, 14.2f));
                values2.add(new BarEntry(i, 8.5f));
                continue;
            }
            if(i==1){
                values1.add(new BarEntry(i,0));
                values2.add(new BarEntry(i, 0));
                continue;
            }
            values1.add(new BarEntry(i, MIN_Y_VALUE + r.nextFloat() * (MAX_Y_VALUE - MIN_Y_VALUE)));
            values2.add(new BarEntry(i, (MIN_Y_VALUE-4) + r.nextFloat() * ((MAX_Y_VALUE-4) - (MIN_Y_VALUE-4))));
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
