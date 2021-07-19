package github.julianNSH.moneymanager.scope;

import android.annotation.SuppressLint;
import android.app.*;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import java.util.ArrayList;
import java.util.Calendar;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;

public class ScopeFragment extends Fragment {
    private ArrayList<ScopeModelClass> scopeModelClasses;
    private RecyclerView recyclerView;
    private ScopeAdapter scopeAdapter;
    private Dialog addScopeDialog;
    private EditText startScopeTime, startScopeDate, endScopeTime, endScopeDate;
    private TimePickerDialog timePicker;
    private DatePickerDialog datePicker;
    private DatabaseClass databaseClass;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_scope, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewScope);
        scopeModelClasses = new ArrayList<>();
        databaseClass = new DatabaseClass(getContext());
        scopeModelClasses = databaseClass.getAllScopes();

        LinearLayout linearLayoutText = root.findViewById(R.id.infoLayout);
        if(scopeModelClasses.size()==0){
            linearLayoutText.setVisibility(LinearLayout.VISIBLE);
        } else {
            linearLayoutText.setVisibility(LinearLayout.GONE);
        }

        scopeAdapter = new ScopeAdapter(root.getContext(), scopeModelClasses);
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(scopeAdapter);


        //ADD SCOPE DIALOG
        Button addScopeButton = root.findViewById(R.id.btn_dialog);
        addScopeButton.setOnClickListener(this::showScopeDialog);
        return root;
    }

    @SuppressLint({"CutPasteId", "DefaultLocale"})
    public void showScopeDialog(View root){
        //INIT Dialog View
        addScopeDialog = new Dialog(root.getContext());
        addScopeDialog.setContentView(R.layout.scope_add_dialog);
        addScopeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //////////////////////////////////PICK TIME FROM CLOCK
        startScopeTime = addScopeDialog.findViewById(R.id.add_start_time);
        startScopeTime.setInputType(InputType.TYPE_NULL);
        startScopeTime.setOnClickListener(v -> {
            Calendar time = Calendar.getInstance();
            int hour = time.get(Calendar.HOUR_OF_DAY);
            int minute = time.get(Calendar.MINUTE);

            timePicker = new TimePickerDialog(addScopeDialog.getContext(),
                    (view, hourOfDay, minuteOfHour) ->
                            startScopeTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour)), hour, minute, true);
            timePicker.show();
        });
        endScopeTime = addScopeDialog.findViewById(R.id.add_end_time);
        endScopeTime.setInputType(InputType.TYPE_NULL);
        endScopeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);
                timePicker = new TimePickerDialog(addScopeDialog.getContext(),
                        (view, hourOfDay, minuteOfHour) ->
                                endScopeTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour)), hour, minute, true);
                timePicker.show();
            }
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        startScopeDate = addScopeDialog.findViewById(R.id.add_start_date);
        startScopeDate.setInputType(InputType.TYPE_NULL);
        startScopeDate.setOnClickListener(v -> {
            final Calendar date = Calendar.getInstance();
            int day = date.get(Calendar.DAY_OF_MONTH);
            int month = date.get(Calendar.MONTH);
            int year = date.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(addScopeDialog.getContext(),
                    (view, year1, month1, dayOfMonth) ->
                            startScopeDate.setText(String.format("%04d-%02d-%02d", year1, month1 +1, dayOfMonth)), year, month, day);

            datePicker.show();
        });
        endScopeDate = addScopeDialog.findViewById(R.id.add_end_date);
        endScopeDate.setInputType(InputType.TYPE_NULL);
        endScopeDate.setOnClickListener(v -> {
            final Calendar date = Calendar.getInstance();
            int day = date.get(Calendar.DAY_OF_MONTH);
            int month = date.get(Calendar.MONTH);
            int year = date.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(addScopeDialog.getContext(),
                    (view, year12, month12, dayOfMonth) ->
                            endScopeDate.setText(String.format("%04d-%02d-%02d", year12, month12 +1, dayOfMonth)), year, month, day);

            datePicker.show();
        });

        //Collect and send data to database

        ScopeModelClass scopeData = new ScopeModelClass();
        EditText scopeTitle = addScopeDialog.findViewById(R.id.scope_title);
        EditText currentAmount = addScopeDialog.findViewById(R.id.current_amount);
        EditText neededAmount = addScopeDialog.findViewById(R.id.total_amount);
        EditText comment = addScopeDialog.findViewById(R.id.scope_comment);


        databaseClass = new DatabaseClass(getContext());

        Button addScope = addScopeDialog.findViewById(R.id.btn_add_scope);
        addScope.setOnClickListener(v -> {
            //check if fields arent empty and add values into the object
            if(scopeTitle.getText().toString().isEmpty()){
                scopeTitle.setError("Introduceți titlul");
            } else {
                scopeData.setTvTitle(String.valueOf(scopeTitle.getText()));
            }
            if(currentAmount.getText().toString().isEmpty()){
                scopeData.setTvInitialAmount(0.0f);
            } else {
                scopeData.setTvInitialAmount(Float.parseFloat(String.valueOf(currentAmount.getText())));
            }
            if(neededAmount.getText().toString().isEmpty()){
                neededAmount.setError("Introduceți suma");
            } else {
                scopeData.setTvFinalAmount(Float.parseFloat(String.valueOf(neededAmount.getText())));
            }
            if(startScopeTime.getText().toString().isEmpty()){
                startScopeTime.setError("Introduceți ora");
            } else {
                scopeData.setStartTime(String.valueOf(startScopeTime.getText()));
            }
            if(startScopeDate.getText().toString().isEmpty()){
                startScopeDate.setError("Introduceți data");
            } else {
                scopeData.setStartDate(String.valueOf(startScopeDate.getText()));
            }
            if(endScopeTime.getText().toString().isEmpty()){
                endScopeTime.setError("Introduceți ora");
            } else {
                scopeData.setEndTime(String.valueOf(endScopeTime.getText()));
            }
            if(endScopeDate.getText().toString().isEmpty()){
                endScopeDate.setError("Introduceți data");
            } else {
                scopeData.setEndDate(String.valueOf(endScopeDate.getText()));
            }

            scopeData.setComment(String.valueOf(comment.getText()));

            //insert data into database by given object
            if(!(scopeTitle.getText().toString().isEmpty() || neededAmount.getText().toString().isEmpty() ||
                    startScopeTime.getText().toString().isEmpty() || startScopeDate.getText().toString().isEmpty()||
                    endScopeTime.getText().toString().isEmpty() || endScopeDate.getText().toString().isEmpty())) {
                databaseClass.addScope(scopeData);
                Toast.makeText(root.getContext(), scopeData.getTvTitle() + " a fost adăugat", Toast.LENGTH_SHORT).show();
            }
        });
        addScopeDialog.show();
    }
}
