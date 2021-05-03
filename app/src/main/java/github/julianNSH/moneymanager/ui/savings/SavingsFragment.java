package github.julianNSH.moneymanager.ui.savings;
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

public class SavingsFragment extends Fragment {
    private SavingsViewModel savingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        savingsViewModel = new ViewModelProvider(this).get(SavingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_savings, container, false);
//        final TextView textView = root.findViewById(R.id.text_savings);
//        savingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {textView.setText(s);}
//        });
        return root;
    }
}
