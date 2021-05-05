package github.julianNSH.moneymanager.ui.statistics;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.app.infideap.stylishwidget.view.AProgressBar;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.julianNSH.moneymanager.R;

public class StatisticsFragment extends Fragment {
    private ArrayList<StatisticsModelClass> statisticsModelClasses;
    private RecyclerView recyclerView;
    private StatisticsAdapter statisticsAdapter;

    DatePickerDialog datePicker;
    Button statisticsDateButton;

    private Integer image[] = {R.drawable.cat_food, R.drawable.cat_shoping, R.drawable.cat_house, R.drawable.cat_phone,
            R.drawable.cat_gift, R.drawable.cat_coffe, R.drawable.ic_flat, R.drawable.ic_flat};
    private  String title[] = {"Alimente","Îmbrăcăminte","Chirie","Telefonie","Cadouri",
            "Cafea","Dezinfectant","Frizer"};
    private String subtitle[] = {"2640 MDL","1500 MDL","2500 MDL","300 MDL","700 MDL",
            "50 MDL","25 MDL", "150 MDL"};

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

        recyclerView = (RecyclerView) root.findViewById(R.id.rvTransaction);
        statisticsModelClasses = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            StatisticsModelClass listModelClass = new StatisticsModelClass(image[i],title[i],subtitle[i]);

            statisticsModelClasses.add(listModelClass);
        }
        statisticsAdapter = new StatisticsAdapter(StatisticsFragment.this,statisticsModelClasses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statisticsAdapter);

        return root;
    }
}
