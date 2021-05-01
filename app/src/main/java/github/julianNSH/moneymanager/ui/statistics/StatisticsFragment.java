package github.julianNSH.moneymanager.ui.statistics;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.infideap.stylishwidget.view.AProgressBar;
import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.ui.overview.OverviewAdapter;
import github.julianNSH.moneymanager.ui.overview.OverviewFragment;
import github.julianNSH.moneymanager.ui.overview.OverviewModelClass;
import github.julianNSH.moneymanager.ui.scope.ScopeViewModel;

public class StatisticsFragment extends Fragment {
    private ArrayList<StatisticsModelClass> statisticsModelClasses;
    private RecyclerView recyclerView;
    private StatisticsAdapter statisticsAdapter;

    private Integer image[] = {R.drawable.cat_food, R.drawable.cat_shoping, R.drawable.cat_house, R.drawable.cat_phone,
            R.drawable.cat_gift, R.drawable.cat_coffe, R.drawable.ic_flat, R.drawable.ic_flat};
    private  String title[] = {"Alimente","Îmbrăcăminte","Chirie","Telefonie","Cadouri",
            "Cafea","Dezinfectant","Frizer"};
    private String subtitle[] = {"2640 MDL","1500 MDL","2500 MDL","300 MDL","700 MDL",
            "50 MDL","25 MDL", "150 MDL"};

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

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
