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

import com.app.infideap.stylishwidget.view.AProgressBar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;

public class StatisticsFragment extends Fragment {
    private RecyclerView recyclerView;
    private StatisticsAdapter statisticsAdapter;
    private DatabaseClass databaseClass;
    private ArrayList<StatisticsModelClass> statisticsModelClasses;

    DatePickerDialog datePicker;
    Button statisticsDateButton;

    private Integer[] image = {R.drawable.cat_food, R.drawable.cat_shoping, R.drawable.cat_house, R.drawable.cat_phone,
            R.drawable.cat_gift, R.drawable.cat_coffe, R.drawable.ic_flat, R.drawable.ic_flat};
    private  String[] title = {"Alimente","Îmbrăcăminte","Chirie","Telefonie","Cadouri",
            "Cafea","Dezinfectant","Frizer"};
    private float[] amount = {2640, 1500, 2500, 300, 700, 50, 25, 150};
    private String[] comment = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur",
            " Excepteur sint occaecat cupidatat non proident",
            "sunt in culpa qui officia deserunt mollit anim id est laborum."," "," "," "};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ResourceType", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        //DATE BUTTON
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

                datePicker = new DatePickerDialog(root.getContext(), android.R.style.Theme_Holo_Dialog,new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        statisticsDateButton.setText(monthsOfYear[month] + " " + year);
                    }
                }, year, month, day);

                //android.R.style.Theme_Holo_Dialog,
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);

                datePicker.show();

            }
        });
        ///////////////////////PROGRES BAR
        AProgressBar iconProgressBar =  (AProgressBar) root.findViewById(R.id.progressBar_statistics);
        iconProgressBar.setProgressValues(31, 60, 78, 86, 90, 100);
        iconProgressBar.setProgressColors(
                Color.parseColor(getResources().getString(R.color.stat_elem1)),
                Color.parseColor(getResources().getString(R.color.stat_elem2)),
                Color.parseColor(getResources().getString(R.color.stat_elem3)),
                Color.parseColor(getResources().getString(R.color.stat_elem4)),
                Color.parseColor(getResources().getString(R.color.stat_elem5)),
                Color.parseColor(getResources().getString(R.color.stat_elem6))
        );

        ///////////////////////List of elements
        databaseClass = new DatabaseClass(getContext());

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_statistics_list);

        statisticsModelClasses = databaseClass.getAllOutgoingData();
        statisticsAdapter = new StatisticsAdapter(root.getContext() ,statisticsModelClasses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statisticsAdapter);

        return root;
    }
}
