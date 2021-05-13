package github.julianNSH.moneymanager.statistics;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;

public class StatisticsFragment extends Fragment {
    private RecyclerView recyclerView;
    private StatisticsAdapter statisticsAdapter;
    private DatabaseClass databaseClass;
    private ArrayList<StatisticsModelClass> statisticsModelClasses, sortedClass;
    private LinearLayout ll1,ll2,ll3,ll4,ll5,ll6;
    AProgressBar iconProgressBar;

    private float totalSpending;
    private DatePickerDialog datePicker;
    private Button statisticsDateButton;
    private TextView spendingAmount;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ResourceType", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        //DATE BUTTON //TODO get data by selected month and year
        final Calendar date = Calendar.getInstance();
        final String[] monthsOfYear = {"Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
                "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"};
        statisticsDateButton = (Button) root.findViewById(R.id.btn_date);
        statisticsDateButton.setText(monthsOfYear[date.get(Calendar.MONTH)]+ " " + date.get(Calendar.YEAR));
        statisticsDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(root.getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        statisticsDateButton.setText(monthsOfYear[month] + " " + year);
                        //refreshFragment();
                    }
                }, year, month, day);

                //android.R.style.Theme_Holo_Dialog,
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);

                datePicker.show();

            }
        });
        
        ///////////////////////List of elements
        databaseClass = new DatabaseClass(getContext());

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_statistics_list);

        statisticsModelClasses = databaseClass.getAllOutgoingData();
        statisticsAdapter = new StatisticsAdapter(root.getContext() ,statisticsModelClasses);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statisticsAdapter);

        // Total spending
        totalSpending = 0;
        float[] biggestCateg = new float[6];
        int[] pbValues = new int[6];
        biggestCateg[5]=0; pbValues[5] = 100;
        sortedClass = statisticsModelClasses;
        iconProgressBar =  (AProgressBar) root.findViewById(R.id.progressBar_statistics);

        Collections.sort(sortedClass);

        for (int i = 0; i<statisticsModelClasses.size(); i++){
            totalSpending+=statisticsModelClasses.get(i).getTvAmount();
            if(i>=5) biggestCateg[5]+= sortedClass.get(i).getTvAmount();
            if(i<=4) biggestCateg[i] = sortedClass.get(i).getTvAmount();
        }
        pbValues[0] = (int) (totalSpending*biggestCateg[0])/100;
        for (int i = 1 ;i<6; i++) pbValues[i] += (int) pbValues[i-1] +(totalSpending*biggestCateg[i])/100;

        spendingAmount = (TextView) root.findViewById(R.id.spendingAmount);
        spendingAmount.setText(totalSpending + " "+getResources().getString(R.string.currency));
        TextView cat1,cat2,cat3,cat4,cat5, catval1,catval2,catval3,catval4,catval5,catval6;
        if(sortedClass.size()>=1) {
            ll1 = (LinearLayout) root.findViewById(R.id.ll1);
            ll1.setVisibility(LinearLayout.VISIBLE);
            cat1 = (TextView) root.findViewById(R.id.cat1);
            cat1.setText(sortedClass.get(0).getTvType());
            catval1 = (TextView) root.findViewById(R.id.catval1);
            catval1.setText(biggestCateg[0] +" "+getResources().getString(R.string.currency));
            iconProgressBar.setProgressValues(31);
            iconProgressBar.setProgressValues(pbValues[0]);
        }

        if(sortedClass.size()>=2) {
            ll2 = (LinearLayout) root.findViewById(R.id.ll2);
            ll2.setVisibility(LinearLayout.VISIBLE);
            cat2 = (TextView) root.findViewById(R.id.cat2);
            cat2.setText(sortedClass.get(1).getTvType());
            catval2 = (TextView) root.findViewById(R.id.catval2);
            catval2.setText(biggestCateg[1] + " "+getResources().getString(R.string.currency));
            iconProgressBar.setProgressValues(pbValues[0], pbValues[1]);
        }

        if(sortedClass.size()>=3) {
            ll3 = (LinearLayout)root.findViewById(R.id.ll3);
            ll3.setVisibility(LinearLayout.VISIBLE);
            cat3 = (TextView) root.findViewById(R.id.cat3);
            cat3.setText(sortedClass.get(2).getTvType());
            catval3 = (TextView) root.findViewById(R.id.catval3);
            catval3.setText(biggestCateg[2] + " "+getResources().getString(R.string.currency));
            iconProgressBar.setProgressValues(pbValues[0], pbValues[1], pbValues[2]);
        }

        if(sortedClass.size()>=4) {
            ll4 = (LinearLayout) root.findViewById(R.id.ll4);
            ll4.setVisibility(LinearLayout.VISIBLE);
            cat4 = (TextView) root.findViewById(R.id.cat4);
            cat4.setText(sortedClass.get(3).getTvType());
            catval4 = (TextView) root.findViewById(R.id.catval4);
            catval4.setText(biggestCateg[3] + " "+getResources().getString(R.string.currency));
            iconProgressBar.setProgressValues(pbValues[0], pbValues[1], pbValues[2], pbValues[3]);
        }

        if(sortedClass.size()>=5) {
            ll5 = (LinearLayout) root.findViewById(R.id.ll5);
            ll5.setVisibility(LinearLayout.VISIBLE);
            cat5 = (TextView) root.findViewById(R.id.cat5);
            cat5.setText(sortedClass.get(4).getTvType());
            catval5 = (TextView) root.findViewById(R.id.catval5);
            catval5.setText(biggestCateg[4]+ " "+getResources().getString(R.string.currency));
            iconProgressBar.setProgressValues(pbValues[0], pbValues[1], pbValues[2], pbValues[3],
                    pbValues[4]);
        }
        if(sortedClass.size()>=6){
            catval6 = (TextView) root.findViewById(R.id.catval6);
            ll6 = (LinearLayout) root.findViewById(R.id.ll6);
            ll6.setVisibility(LinearLayout.VISIBLE);
            catval6.setText(biggestCateg[5] + " "+getResources().getString(R.string.currency));
            iconProgressBar.setProgressValues(pbValues[0], pbValues[1], pbValues[2], pbValues[3],
                    pbValues[4],pbValues[5]);
        }

        ///////////////////////PROGRES BAR
       iconProgressBar.setProgressColors(
                Color.parseColor(getResources().getString(R.color.stat_elem1)),
                Color.parseColor(getResources().getString(R.color.stat_elem2)),
                Color.parseColor(getResources().getString(R.color.stat_elem3)),
                Color.parseColor(getResources().getString(R.color.stat_elem4)),
                Color.parseColor(getResources().getString(R.color.stat_elem5)),
                Color.parseColor(getResources().getString(R.color.stat_elem6))
        );

        return root;
    }

//
//    public void refreshFragment(){
//        if (getFragmentManager() != null) {
//            getFragmentManager()
//                    .beginTransaction()
//                    .detach(StatisticsFragment.this)
//                    .attach(StatisticsFragment.this)
//                    .commit();
//        }
//    }
}
