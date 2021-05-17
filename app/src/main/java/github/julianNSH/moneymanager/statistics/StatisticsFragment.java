package github.julianNSH.moneymanager.statistics;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.infideap.stylishwidget.view.AProgressBar;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
    private ArrayList<StatisticsModelClass> statisticsModelClasses, sortedClass;
    private LinearLayout ll1,ll2,ll3,ll4,ll5,ll6;


    private float totalSpending;
    private DatePickerDialog datePicker;
    private Button statisticsDateButton, nextMonth, prevMonth;
    private TextView spendingAmount;
    Calendar date;
    int currentMonth, currentYear;

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

    @SuppressLint({"ResourceType", "SetTextI18n"})
    public void showStatisticsData(View view, int month,int year){
//        getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
        @SuppressLint("DefaultLocale") String date = String.format("%04d-%02d", year, month);
        ///////////////////////List of elements
        databaseClass = new DatabaseClass(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_statistics_list);

        statisticsModelClasses = databaseClass.getOutgoingDataByMonthYear(date);

        // Total spending
        totalSpending = 0;
        float[] biggestCateg = new float[6];
        int[] pbValues = new int[6];
        biggestCateg[5]=0;
        sortedClass = statisticsModelClasses;

        //SORTING AND ORGANIZING CATEGORIES (DESC)
        Collections.sort(sortedClass);
        for (int i = 0; i<statisticsModelClasses.size(); i++){
            totalSpending+=statisticsModelClasses.get(i).getTvAmount();
            if(i>=5) biggestCateg[5]+= sortedClass.get(i).getTvAmount();
            if(i<=4) biggestCateg[i] = sortedClass.get(i).getTvAmount();
        }

        statisticsModelClasses = databaseClass.getOutgoingDataByMonthYear(date);
        statisticsAdapter = new StatisticsAdapter(view.getContext() ,statisticsModelClasses);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statisticsAdapter);
        //SETTING PROGRESS VALUES(percents) for existing categories
        pbValues[0] = (int) ((int) (biggestCateg[0]*100)/totalSpending)+1;
        for (int i = 1 ;i<6; i++) {
            pbValues[i] += (int) pbValues[i - 1] + (biggestCateg[i]*100) / totalSpending;
            if (i==sortedClass.size()) break;
        }
        spendingAmount = (TextView) view.findViewById(R.id.spendingAmount);
        spendingAmount.setText(totalSpending + " "+getResources().getString(R.string.currency));
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

        if(sortedClass.size()>=1) {
            ll1.setVisibility(LinearLayout.VISIBLE);
            cat1 = (TextView) view.findViewById(R.id.cat1);
            cat1.setText(sortedClass.get(0).getTvType());
            catval1 = (TextView) view.findViewById(R.id.catval1);
            catval1.setText(biggestCateg[0] +" "+getResources().getString(R.string.currency));
        }

        if(sortedClass.size()>=2) {
            ll2.setVisibility(LinearLayout.VISIBLE);
            cat2 = (TextView) view.findViewById(R.id.cat2);
            cat2.setText(sortedClass.get(1).getTvType());
            catval2 = (TextView) view.findViewById(R.id.catval2);
            catval2.setText(biggestCateg[1] + " "+getResources().getString(R.string.currency));
        }

        if(sortedClass.size()>=3) {
            ll3.setVisibility(LinearLayout.VISIBLE);
            cat3 = (TextView) view.findViewById(R.id.cat3);
            cat3.setText(sortedClass.get(2).getTvType());
            catval3 = (TextView) view.findViewById(R.id.catval3);
            catval3.setText(biggestCateg[2] + " "+getResources().getString(R.string.currency));
        }

        if(sortedClass.size()>=4) {
            ll4.setVisibility(LinearLayout.VISIBLE);
            cat4 = (TextView) view.findViewById(R.id.cat4);
            cat4.setText(sortedClass.get(3).getTvType());
            catval4 = (TextView) view.findViewById(R.id.catval4);
            catval4.setText(biggestCateg[3] + " "+getResources().getString(R.string.currency));
        }

        if(sortedClass.size()>=5) {
            ll5.setVisibility(LinearLayout.VISIBLE);
            cat5 = (TextView) view.findViewById(R.id.cat5);
            cat5.setText(sortedClass.get(4).getTvType());
            catval5 = (TextView) view.findViewById(R.id.catval5);
            catval5.setText(biggestCateg[4]+ " "+getResources().getString(R.string.currency));
        }
        if(sortedClass.size()>=6){
            catval6 = (TextView) view.findViewById(R.id.catval6);
            ll6.setVisibility(LinearLayout.VISIBLE);
            catval6.setText(biggestCateg[5] + " "+getResources().getString(R.string.currency));
        }
        /////////////////////SETTING PROGRESS BAR
        AProgressBar iconProgressBar = view.findViewById(R.id.progressBar_statistics);

          for (int i = 0; i<sortedClass.size();i++){
              iconProgressBar.setProgressValue(i, pbValues[i]);
          }

        iconProgressBar.setProgressColors(
                Color.parseColor(getResources().getString(R.color.stat_elem1)),
                Color.parseColor(getResources().getString(R.color.stat_elem2)),
                Color.parseColor(getResources().getString(R.color.stat_elem3)),
                Color.parseColor(getResources().getString(R.color.stat_elem4)),
                Color.parseColor(getResources().getString(R.color.stat_elem5)),
                Color.parseColor(getResources().getString(R.color.stat_elem6))
        );

    }

}
