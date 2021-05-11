package github.julianNSH.moneymanager.ui.scope;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import github.julianNSH.moneymanager.R;

public class ScopeFragment extends Fragment {
    private ArrayList<ScopeModelClass> scopeModelClasses;
    private RecyclerView recyclerView;
    private ScopeAdapter scopeAdapter;
    private Dialog addScopeDialog;

    private String title[] = {"Casa","Masina","Apartament","Televizor","Mobila",
            "Calculator","Telefon","Frigider"};
    private String values[] = {"150,000 / 1,000,000 MDL","50,000 / 300,000 MDL","400,000 / 4,000,000 MDL",
            "10,000 / 30,000 MDL","70,000 / 70,000 MDL", "430,500 / 430,500 MDL","200,000 / 300,000 MDL",
            "200,000 / 300,000 MDL"};
    private Integer pbProgressValues[] = {15, 17, 10, 33, 100, 100, 66, 66};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_scope, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewScope);
        scopeModelClasses = new ArrayList<>();

        for (int i=0; i<title.length; i++){
            ScopeModelClass listModelClass = new ScopeModelClass(title[i], values[i], pbProgressValues[i]);
            scopeModelClasses.add(listModelClass);
        }
        scopeAdapter = new ScopeAdapter(root.getContext(), scopeModelClasses);
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(scopeAdapter);


        //ADD SCOPE DIALOG
        addScopeDialog = new Dialog(root.getContext());
        addScopeDialog.setContentView(R.layout.scope_add_dialog);
        addScopeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button addScopeButton = (Button) root.findViewById(R.id.btn_dialog);
        addScopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button addScope = (Button) addScopeDialog.findViewById(R.id.btn_add_scope);
                addScope.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(root.getContext(), "Button ADD Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                addScopeDialog.show();
            }
        });
        return root;
    }
}
