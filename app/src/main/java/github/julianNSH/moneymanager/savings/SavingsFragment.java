package github.julianNSH.moneymanager.savings;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

import github.julianNSH.moneymanager.R;

public class SavingsFragment extends Fragment {
    private ArrayList<SavingsModelClass> savingsModelClasses;
    private RecyclerView recyclerView;
    private SavingsAdapter savingsAdapter;

    private LineChart chart;

    private TextView savingTotal, savingActual, savingMaxim, savingMinim;
    private String title[] = {"Septembrie 2020","Octombrie 2020","Noiembrie 2020","Decembrie 2020","Ianuarie 2021",
            "Februarie 2021","Martie 2021","Aprilie 2021"};
    private int income[] = {15000, 20000, 18000, 16500, 34000, 12000, 14760, 19999};
    private int outcome[] = {11000, 14230, 19230, 9400, 20000, 14800, 9500, 16730};
    //
    private String color;
    private int total = 0;
    private int min = income[1]-outcome[1];
    private int max = income[1]-outcome[1];
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_savings, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.reciclerViewSavings);
        savingsModelClasses = new ArrayList<>();


        for (int i=0; i<title.length; i++){
            //
            int temp = income[i]-outcome[i];
            if(min > temp){
                min = temp;
            }

            if(max < temp){
                max = temp;
            }
            //
            total+= temp;
            if (income[i] > outcome[i]) {
                color = "#44bd32";
            } else {
                color = "#e84118";
            }
            //
            SavingsModelClass listModelClass = new SavingsModelClass(title[i], income[i] + " MDL",
                    outcome[i] + " MDL", income[i]-outcome[i] + " MDL", color);
            savingsModelClasses.add(listModelClass);
        }
        /////////////////////////////
        savingTotal = (TextView) root.findViewById(R.id.savingTotal);
        savingTotal.setText(total + " MDL");
        savingActual = (TextView) root.findViewById(R.id.savingActual);
        savingActual.setText(income[7]-outcome[7] + " MDL");

        savingMaxim = (TextView) root.findViewById(R.id.savingMaxim);
        savingMaxim.setText(max + " MDL");
        savingMinim = (TextView) root.findViewById(R.id.savingMinim);
        savingMinim.setText(min + " MDL");

        ///////////////////////////////////
        savingsAdapter = new SavingsAdapter(SavingsFragment.this, savingsModelClasses);
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(savingsAdapter);

        /////////////////////////

        return root;
    }

}
