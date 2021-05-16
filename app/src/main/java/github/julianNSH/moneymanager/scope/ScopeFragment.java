package github.julianNSH.moneymanager.scope;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewScope);
        scopeModelClasses = new ArrayList<>();
        databaseClass = new DatabaseClass(getContext());
        scopeModelClasses = databaseClass.getAllScopes();
        scopeAdapter = new ScopeAdapter(root.getContext(), scopeModelClasses);
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(scopeAdapter);


        //ADD SCOPE DIALOG
        Button addScopeButton = (Button) root.findViewById(R.id.btn_dialog);
        addScopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showScopeDialog(v);
            }
        });
        return root;
    }

    @SuppressLint("CutPasteId")
    public void showScopeDialog(View root){
        //INIT Dialog View
        addScopeDialog = new Dialog(root.getContext());
        addScopeDialog.setContentView(R.layout.scope_add_dialog);
        addScopeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //////////////////////////////////PICK TIME FROM CLOCK
        startScopeTime = (EditText) addScopeDialog.findViewById(R.id.add_start_time);
        startScopeTime.setInputType(InputType.TYPE_NULL);
        startScopeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(addScopeDialog.getContext(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        startScopeTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour));
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });
        endScopeTime = (EditText) addScopeDialog.findViewById(R.id.add_end_time);
        endScopeTime.setInputType(InputType.TYPE_NULL);
        endScopeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);
                timePicker = new TimePickerDialog(addScopeDialog.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        endScopeTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour));
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        startScopeDate = (EditText) addScopeDialog.findViewById(R.id.add_start_date);
        startScopeDate.setInputType(InputType.TYPE_NULL);
        startScopeDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(addScopeDialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startScopeDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                /*android.R.style.Theme_Holo_Dialog,
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);
                */
                datePicker.show();
            }
        });
        endScopeDate = (EditText) addScopeDialog.findViewById(R.id.add_end_date);
        endScopeDate.setInputType(InputType.TYPE_NULL);
        endScopeDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(addScopeDialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endScopeDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                datePicker.show();
            }
        });

        //Collect and send data to database

        ScopeModelClass scopeData = new ScopeModelClass();
        EditText scopeTitle = (EditText) addScopeDialog.findViewById(R.id.scope_title);
        EditText currentAmount = (EditText) addScopeDialog.findViewById(R.id.current_amount);
        EditText neededAmount = (EditText) addScopeDialog.findViewById(R.id.total_amount);
        EditText comment = (EditText) addScopeDialog.findViewById(R.id.scope_comment);


        databaseClass = new DatabaseClass(getContext());

        Button addScope = (Button) addScopeDialog.findViewById(R.id.btn_add_scope);
        addScope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scopeData.setTvTitle(String.valueOf(scopeTitle.getText()));
                scopeData.setTvInitialAmount(Float.parseFloat(String.valueOf(currentAmount.getText())));
                scopeData.setTvFinalAmount(Float.parseFloat(String.valueOf(neededAmount.getText())));
                scopeData.setStartTime(String.valueOf(startScopeTime.getText()));
                scopeData.setStartDate(String.valueOf(startScopeDate.getText()));
                scopeData.setEndTime(String.valueOf(endScopeTime.getText()));
                scopeData.setEndDate(String.valueOf(endScopeDate.getText()));
                scopeData.setComment(String.valueOf(comment.getText()));
                long id = databaseClass.addScope(scopeData);
                Toast.makeText(root.getContext(), "Item "+id+" added", Toast.LENGTH_SHORT).show();
            }
        });
        addScopeDialog.show();
    }
}
