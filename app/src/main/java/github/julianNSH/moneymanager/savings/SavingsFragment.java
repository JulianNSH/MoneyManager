package github.julianNSH.moneymanager.savings;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import github.julianNSH.moneymanager.CustomDateParser;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;

public class SavingsFragment extends Fragment {
    private ArrayList<SavingsModelClass> savingsModelClasses;
    private RecyclerView recyclerView;
    private SavingsAdapter savingsAdapter;
    private DatabaseClass databaseClass;
    private ArrayList<Entry> chartData;

    private LineChart chart;

    private float max, min,current, total, income, outgoing;
    private TextView savingTotal, savingActual, savingMaxim, savingMinim;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_savings, container, false);

        recyclerView = root.findViewById(R.id.reciclerViewSavings);
        savingsModelClasses = new ArrayList<>();
        databaseClass = new DatabaseClass(root.getContext());
        chartData = new ArrayList<>();


        ArrayList<String> distinctDates = databaseClass.getDistinctDates();
        LinearLayout layoutContent = root.findViewById(R.id.contentLayout);
        TextView infoText = root.findViewById(R.id.infoText);
        if(distinctDates.size()==0){
            infoText.setVisibility(LinearLayout.VISIBLE);
            layoutContent.setVisibility(LinearLayout.GONE);
            return root;
        }
        total=0;
        max = databaseClass.getIncomeByDate(distinctDates.get(0))-databaseClass.getOutgoingByDate(distinctDates.get(0));
        min = databaseClass.getIncomeByDate(distinctDates.get(0))-databaseClass.getOutgoingByDate(distinctDates.get(0));
        for (int i =0; i<distinctDates.size(); i++) {
            SavingsModelClass savingElement = new SavingsModelClass();

            income = databaseClass.getTotalIncome(distinctDates.get(i));
            outgoing = databaseClass.getTotalOutgoing(distinctDates.get(i));
            if(income-outgoing>=max) max =income-outgoing;
            if(income-outgoing<=min) min =income-outgoing;
            savingElement.setTvTitlePeriod(CustomDateParser.customDateParser(distinctDates.get(i)));
            savingElement.setTvIncome(income);
            savingElement.setTvOutgoings(outgoing);
            savingElement.setTvResult(income-outgoing);

            savingsModelClasses.add(savingElement);

            total+= income-outgoing;
            if(i==0) current = income-outgoing;
        }

        /////////////////////////////
        savingTotal = root.findViewById(R.id.savingTotal);
        savingTotal.setText(total + root.getResources().getString(R.string.currency));
        savingActual = root.findViewById(R.id.savingActual);
        savingActual.setText(current + " "+root.getResources().getString(R.string.currency));

        savingMaxim = root.findViewById(R.id.savingMaxim);
        savingMaxim.setText(max + " "+ root.getResources().getString(R.string.currency));
        savingMinim = root.findViewById(R.id.savingMinim);
        savingMinim.setText(min + " "+ root.getResources().getString(R.string.currency));

        ///////////////////////////////////
        savingsAdapter = new SavingsAdapter(SavingsFragment.this, savingsModelClasses);
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(savingsAdapter);

        /////////////////////////

        ArrayList<String> months = new ArrayList<>();
        int n = 0;
        for (int i=savingsModelClasses.size()-1; i>=0; i--){
            chartData.add(new Entry(n++, savingsModelClasses.get(i).tvResult));
            months.add(savingsModelClasses.get(i).tvTitlePeriod.substring(0,3));
        }

        chart = root.findViewById(R.id.savings_linechart);
        LineDataSet lineDataSet = new LineDataSet(chartData,null);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        lineDataSet.setColor(getResources().getColor(R.color.blue1));

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return months.get(((int) value));
            }
        });

        xAxis.setGranularity(1);
        xAxis.setLabelCount(months.size());

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setValueFormatter(new LargeValueFormatter());

        chart.getAxisRight().setEnabled(false);
        chart.setDescription(null);
        chart.setDrawBorders(true);
        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(Color.WHITE);

        chart.setBorderColor(Color.GRAY);
        chart.getLegend().setEnabled(false);


        LineData data = new LineData(dataSets);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.invalidate();


        return root;
    }


}
