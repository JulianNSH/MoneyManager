package github.julianNSH.moneymanager.ui.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import github.julianNSH.moneymanager.MainActivity;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.ui.scope.ScopeViewModel;

public class OverviewFragment extends Fragment {
//    private ArrayList<OverviewModelClass> overviewModelClasses;
//    private RecyclerView recyclerView;
//    private OverviewAdapter oAdapter;
//
//    private Integer image[] = {R.drawable.ic_up, R.drawable.ic_down,R.drawable.ic_down,R.drawable.ic_flat,
//            R.drawable.ic_down,R.drawable.ic_flat,R.drawable.ic_down,R.drawable.ic_flat,R.drawable.ic_up};
//    private  String title[] = {"Salariu","Alimente","Servicii Comunale","Transfer","Îmbrăcăminte",
//            "Transfer","Cadou","Transfer","Cash-Back"};
//    private String subtitle[] = {"MDL 23050","MDL 1000","MDL 2500","MDL 5000","MDL 1500","MDL 3000",
//            "MDL 700","MDL 1000","MDL 1500"};
    private OverviewViewModel overviewViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
            overviewViewModel = new ViewModelProvider(this).get(OverviewViewModel.class);
            View root = inflater.inflate(R.layout.fragment_overview, container, false);
//            final TextView textView = root.findViewById(R.id.text_overview);
//            overviewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//                @Override
//                public void onChanged(String s) {textView.setText(s);}
//            });
        return root;
    }
}
