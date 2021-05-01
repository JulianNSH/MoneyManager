package github.julianNSH.moneymanager.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.julianNSH.moneymanager.R;

public class OverviewFragment extends Fragment {

    ////////////////////////////////////////////////////////////////Graph Elements ->TODO makeanotherclass
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
    private OverviewAdapter oAdapter;

    private Integer image[] = {R.drawable.ic_up, R.drawable.ic_down,R.drawable.ic_down,R.drawable.ic_flat,
            R.drawable.ic_down,R.drawable.ic_flat,R.drawable.ic_down,R.drawable.ic_flat,R.drawable.ic_up};
    private  String title[] = {"Salariu","Alimente","Servicii Comunale","Transfer","Îmbrăcăminte",
            "Transfer","Cadou","Transfer","Cash-Back"};
    private String subtitle[] = {"13710 MDL","1000 MDL","2500 MDL","5000 MDL","1500 MDL","3000 MDL",
            "700 MDL","1000 MDL","1500 MDL"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
            View root = inflater.inflate(R.layout.fragment_overview, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rvTransaction);

        overviewModelClasses = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            OverviewModelClass listModelClass = new OverviewModelClass(image[i],title[i],subtitle[i]);

            overviewModelClasses.add(listModelClass);
        }
        oAdapter = new OverviewAdapter(OverviewFragment.this,overviewModelClasses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(oAdapter);

        chart = root.findViewById(R.id.fragment_groupedbarchart_chart);
        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);

        return root;
    }


    private void configureChartAppearance() {
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

        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setAxisMinimum(0);

        chart.getXAxis().setAxisMaximum(MAX_X_VALUE);
    }

    private BarData createChartData() {
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

    private void prepareChartData(BarData data) {
        chart.setData(data);

        chart.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        chart.groupBars(0, groupSpace, BAR_SPACE);

        chart.invalidate();
    }
}
