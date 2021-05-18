package github.julianNSH.moneymanager.statistics;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.julianNSH.moneymanager.CustomDateParser;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;

public class StatisticsFragment extends Fragment {
    private RecyclerView recyclerView;
    private StatisticsAdapter statisticsAdapter;
    private DatabaseClass databaseClass;
    private ArrayList<StatisticsModelClass> statisticsModelClasses;
    private LinearLayout ll1,ll2,ll3,ll4,ll5,ll6;


    private float totalSpending;
    private DatePickerDialog datePicker;
    private Button statisticsDateButton, nextMonth, prevMonth;
    private TextView infoText;
    Calendar date;
    int currentMonth, currentYear;
    //////////////////////////////////
    private PieChart pieChart;
    private ArrayList<Integer> pieColors;
    private ArrayList<PieEntry> pieEntries;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ResourceType", "SetTextI18n", "DefaultLocale"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        date = Calendar.getInstance();

        currentMonth = date.get(Calendar.MONTH)+1;
        currentYear = date.get(Calendar.YEAR);
        statisticsDateButton = (Button) root.findViewById(R.id.btn_date);
        nextMonth = root.findViewById(R.id.btn_stat_next);
        prevMonth = root.findViewById(R.id.btn_stat_back);

        showStatisticsData(root, currentMonth, currentYear);
        //move to next month
        nextMonth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentMonth==12){
                    currentMonth = 1;
                    currentYear++;
                } else {currentMonth++;}
                statisticsDateButton.setText(CustomDateParser.customDateParser(String.format("%04d-%02d",currentYear,currentMonth)));
                showStatisticsData(root, currentMonth, currentYear);
            }
        });
        //move to previous month
        prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMonth==1){
                    currentMonth = 12;
                    currentYear--;
                } else {currentMonth--;}
                statisticsDateButton.setText(CustomDateParser.customDateParser(String.format("%04d-%02d", currentYear, currentMonth)));
                showStatisticsData(root, currentMonth, currentYear);
            }
        });

        //DATE BUTTON
        final Calendar date = Calendar.getInstance();


        if(statisticsDateButton.getText() =="")
            statisticsDateButton.setText(CustomDateParser.customDateParser(String.format("%04d-%02d", currentYear, currentMonth)));

        statisticsDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(root.getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        currentMonth =month+1;
                        currentYear = year;
                        statisticsDateButton.setText(CustomDateParser.customDateParser(String.format("%04d-%02d", currentYear, currentMonth)));
                        showStatisticsData(root, currentMonth,currentYear);
                    }
                }, year, month, day);

                //android.R.style.Theme_Holo_Dialog,
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);

                datePicker.show();

            }
        });

        //Update checker
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"ResourceType", "SetTextI18n"})
    public void showStatisticsData(View view, int month,int year){
//        getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
        @SuppressLint("DefaultLocale") String date = String.format("%04d-%02d", year, month);
        ///////////////////////List of elements
        databaseClass = new DatabaseClass(getContext());
        infoText = (TextView) view.findViewById(R.id.infoText);
        pieChart = view.findViewById(R.id.pieChart);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_statistics_list);

        ArrayList<StatisticsModelClass> distinctMC = databaseClass.getDistinctOutgoingsAmountByDate(date);
        statisticsModelClasses = databaseClass.getOutgoingDataByMonthYear(date);
        statisticsAdapter = new StatisticsAdapter(view.getContext() ,statisticsModelClasses);


        if(statisticsModelClasses.size()==0){
            infoText.setVisibility(LinearLayout.VISIBLE);
            pieChart.setVisibility(LinearLayout.GONE);

        } else {
            pieChart.setVisibility(LinearLayout.VISIBLE);
            infoText.setVisibility(LinearLayout.GONE);
        }
        // Total spending
        totalSpending = 0;
        float[] biggestCateg = new float[6];
        float[] pbValues = new float[6];
        biggestCateg[5]=0;


        //SORTING AND ORGANIZING CATEGORIES (DESC)
        Collections.sort(distinctMC);
        for (int i = 0; i<distinctMC.size(); i++){
            totalSpending+=distinctMC.get(i).getTvAmount();
            if(i>=5) biggestCateg[5]+= distinctMC.get(i).getTvAmount();
            if(i<=4) biggestCateg[i] = distinctMC.get(i).getTvAmount();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statisticsAdapter);


        //SETTING PROGRESS VALUES(percents) for existing categories
        pieEntries = new ArrayList<>();
        pieColors = new ArrayList<>();
        for (int i = 0 ;i<6; i++) {
            pbValues[i] =(biggestCateg[i]*100) / totalSpending;
            if (i==distinctMC.size()) break;
        }

        TextView cat1,cat2,cat3,cat4,cat5, catval1,catval2,catval3,catval4,catval5,catval6;
        ll1 = (LinearLayout) view.findViewById(R.id.ll1);
        ll1.setVisibility(LinearLayout.INVISIBLE);
        ll2 = (LinearLayout) view.findViewById(R.id.ll2);
        ll2.setVisibility(LinearLayout.INVISIBLE);
        ll3 = (LinearLayout) view.findViewById(R.id.ll3);
        ll3.setVisibility(LinearLayout.INVISIBLE);
        ll4 = (LinearLayout) view.findViewById(R.id.ll4);
        ll4.setVisibility(LinearLayout.INVISIBLE);
        ll5 = (LinearLayout) view.findViewById(R.id.ll5);
        ll5.setVisibility(LinearLayout.INVISIBLE);
        ll6 = (LinearLayout) view.findViewById(R.id.ll6);
        ll6.setVisibility(LinearLayout.INVISIBLE);
        LinearLayout l1 = view.findViewById(R.id.row1_layout);
        l1.setVisibility(LinearLayout.GONE);
        LinearLayout l2 = view.findViewById(R.id.row2_layout);
        l2.setVisibility(LinearLayout.GONE);

        if(distinctMC.size()>=1) {
            l1.setVisibility(LinearLayout.VISIBLE);
            ll1.setGravity(Gravity.CENTER_HORIZONTAL);
            ll1.setVisibility(LinearLayout.VISIBLE);
            ll2.setVisibility(LinearLayout.GONE);
            cat1 = (TextView) view.findViewById(R.id.cat1);
            cat1.setText(distinctMC.get(0).getTvType());
            catval1 = (TextView) view.findViewById(R.id.catval1);
            catval1.setText(biggestCateg[0] +" "+getResources().getString(R.string.currency));
            pieEntries.add(new PieEntry(pbValues[0],distinctMC.get(0).getTvType()));
            pieColors.add(view.getContext().getColor(R.color.stat_elem1));
        }

        if(distinctMC.size()>=2) {
            ll2.setVisibility(LinearLayout.VISIBLE);
            ll2.setGravity(Gravity.CENTER_HORIZONTAL);
            cat2 = (TextView) view.findViewById(R.id.cat2);
            cat2.setText(distinctMC.get(1).getTvType());
            catval2 = (TextView) view.findViewById(R.id.catval2);
            catval2.setText(biggestCateg[1] + " "+getResources().getString(R.string.currency));
            pieEntries.add(new PieEntry(pbValues[1],distinctMC.get(1).getTvType()));
            pieColors.add(view.getContext().getColor(R.color.stat_elem2));
        }

        if(distinctMC.size()>=3) {
            ll3.setVisibility(LinearLayout.VISIBLE);
            ll3.setGravity(Gravity.CENTER_HORIZONTAL);
            cat3 = (TextView) view.findViewById(R.id.cat3);
            cat3.setText(distinctMC.get(2).getTvType());
            catval3 = (TextView) view.findViewById(R.id.catval3);
            catval3.setText(biggestCateg[2] + " "+getResources().getString(R.string.currency));
            pieEntries.add(new PieEntry(pbValues[2],distinctMC.get(2).getTvType()));
            pieColors.add(view.getContext().getColor(R.color.stat_elem3));
        }

        if(distinctMC.size()>=4) {
            l2.setVisibility(LinearLayout.VISIBLE);
            ll4.setVisibility(LinearLayout.VISIBLE);
            ll5.setGravity(Gravity.CENTER_HORIZONTAL);
            cat4 = (TextView) view.findViewById(R.id.cat4);
            cat4.setText(distinctMC.get(3).getTvType());
            catval4 = (TextView) view.findViewById(R.id.catval4);
            catval4.setText(biggestCateg[3] + " "+getResources().getString(R.string.currency));
            pieEntries.add(new PieEntry(pbValues[3],distinctMC.get(3).getTvType()));
            pieColors.add(view.getContext().getColor(R.color.stat_elem4));
        }

        if(distinctMC.size()>=5) {
            ll5.setVisibility(LinearLayout.VISIBLE);
            ll5.setGravity(Gravity.CENTER_HORIZONTAL);
            cat5 = (TextView) view.findViewById(R.id.cat5);
            cat5.setText(distinctMC.get(4).getTvType());
            catval5 = (TextView) view.findViewById(R.id.catval5);
            catval5.setText(biggestCateg[4]+ " "+getResources().getString(R.string.currency));
            pieEntries.add(new PieEntry(pbValues[4],distinctMC.get(4).getTvType()));
            pieColors.add(view.getContext().getColor(R.color.stat_elem5));
        }
        if(distinctMC.size()>=6){
            catval6 = (TextView) view.findViewById(R.id.catval6);
            ll6.setVisibility(LinearLayout.VISIBLE);
            ll6.setGravity(Gravity.CENTER_HORIZONTAL);
            catval6.setText(biggestCateg[5] + " "+getResources().getString(R.string.currency));
            pieEntries.add(new PieEntry(pbValues[5],distinctMC.get(5).getTvType()));
            pieColors.add(view.getContext().getColor(R.color.stat_elem6));
        }
        /////////////////////SETTING PIE CHART

        pieChart = view.findViewById(R.id.pieChart);
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(pieColors);
        PieData pieData = new PieData(pieDataSet);

        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription(null);
        pieChart.getLegend().setEnabled(false);

        pieChart.setCenterTextSize(18);
        pieChart.setEntryLabelColor(view.getResources().getColor(R.color.white));
        pieChart.setCenterText("Cheltuieli Totale\n"+totalSpending + " "+getResources().getString(R.string.currency));
        pieChart.setHoleRadius(60);
        pieChart.setTransparentCircleRadius(70);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

}
