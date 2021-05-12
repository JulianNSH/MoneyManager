package github.julianNSH.moneymanager.scope;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import github.julianNSH.moneymanager.R;

public class ScopeFragment extends Fragment {
    private ArrayList<ScopeModelClass> scopeModelClasses;
    private RecyclerView recyclerView;
    private ScopeAdapter scopeAdapter;
    private Dialog addScopeDialog;

    private String[] title = {"Casa","Masina","Apartament","Televizor","Mobila",
            "Calculator","Telefon","Frigider"};
    private float[] finalAmount = {1000000, 300000, 4000000, 30000, 70000, 430500, 300000, 300000};
    private float[] currentAmount = {150000, 50000, 400000, 10000, 70000, 430500, 200000, 200000};
    private String[] comment = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
            " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat",
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur",
            " Excepteur sint occaecat cupidatat non proident",
            "sunt in culpa qui officia deserunt mollit anim id est laborum."," "," "," "};

    private Integer[] pbProgressValues = {15, 17, 10, 33, 100, 100, 66, 66};

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_scope, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewScope);
        scopeModelClasses = new ArrayList<>();

        for (int i=0; i<title.length; i++){
            ScopeModelClass listModelClass = new ScopeModelClass(title[i], finalAmount[i], currentAmount[i],
                    String.valueOf(LocalTime.now()), String.valueOf(LocalDate.now()),
                    String.valueOf(LocalTime.now()), String.valueOf(LocalDate.now()),
                    comment[i], pbProgressValues[i]);
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
