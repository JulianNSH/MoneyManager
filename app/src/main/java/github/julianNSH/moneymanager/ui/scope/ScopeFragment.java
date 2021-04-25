package github.julianNSH.moneymanager.ui.scope;

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

public class ScopeFragment extends Fragment {

    private ScopeViewModel scopeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        scopeViewModel = new ViewModelProvider(this).get(ScopeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scope, container, false);
        final TextView textView = root.findViewById(R.id.text_scope);
        scopeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {textView.setText(s);}
        });
        return root;
    }
}
