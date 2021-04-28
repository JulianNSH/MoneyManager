package github.julianNSH.moneymanager.ui.invest;
//TODO REFACTOR TO SAVINGS
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import github.julianNSH.moneymanager.R;

public class InvestFragment extends Fragment {
    private InvestViewModel investViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        investViewModel = new ViewModelProvider(this).get(InvestViewModel.class);
        View root = inflater.inflate(R.layout.fragment_invest, container, false);
        final TextView textView = root.findViewById(R.id.text_invest);
        investViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {textView.setText(s);}
        });
        return root;
    }
}
