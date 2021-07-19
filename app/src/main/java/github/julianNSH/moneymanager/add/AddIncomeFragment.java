package github.julianNSH.moneymanager.add;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.widget.*;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;
import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class AddIncomeFragment extends Fragment {
    public AddIncomeFragment(){}
    private Button addIncome;
    private DatePickerDialog datePicker;
    private EditText incomeDate;

    private TimePickerDialog timePicker;
    private EditText incomeTime, amount, comment;
    private String[] selection;
    private DatabaseClass databaseClass;

    @SuppressLint("DefaultLocale")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root =  inflater.inflate(R.layout.fragment_add_income, container, false);
        //////////////////////////////////INPUT SOURCE OF INCOME
        databaseClass = new DatabaseClass(getContext());
        ArrayList<String> titles = databaseClass.getDistinctIncome();
        AutoCompleteTextView incomeSource = root.findViewById(R.id.income_source);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, titles);
        selection = new String[1];
        incomeSource.setAdapter(arrayAdapter);
        incomeSource.setCursorVisible(true);
        incomeSource.setOnItemClickListener((parent, view, position, id) -> {
            incomeSource.showDropDown();
            selection[0] = (String) parent.getItemAtPosition(position);
           // Toast.makeText(getContext(), selection[0], Toast.LENGTH_SHORT).show();
        });
        incomeSource.setOnClickListener(v -> incomeSource.showDropDown());

        //////////////////////////////////PICK TIME FROM CLOCK
        incomeTime = root.findViewById(R.id.add_income_time);
        incomeTime.setInputType(InputType.TYPE_NULL);
        incomeTime.setOnClickListener(v -> {
            Calendar time = Calendar.getInstance();
            int hour = time.get(Calendar.HOUR_OF_DAY);
            int minute = time.get(Calendar.MINUTE);

            timePicker = new TimePickerDialog(root.getContext(),
                    (view, hourOfDay, minuteOfHour) ->
                            incomeTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour)), hour, minute, true);
            timePicker.show();
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        incomeDate = root.findViewById(R.id.add_income_date);
        incomeDate.setInputType(InputType.TYPE_NULL);
        incomeDate.setOnClickListener(v -> {
            final Calendar date = Calendar.getInstance();
            int day = date.get(Calendar.DAY_OF_MONTH);
            int month = date.get(Calendar.MONTH);
            int year = date.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(root.getContext(),
                    (view, year1, month1, dayOfMonth) ->
                            incomeDate.setText(String.format("%04d-%02d-%02d", year1, month1 +1, dayOfMonth)), year, month, day);

            datePicker.show();
        });
        /**********************************************************************************************
         ADD Income
         */
        addIncome = root.findViewById(R.id.add_income_btn);
        amount = root.findViewById(R.id.incomeAmount);
        comment = root.findViewById(R.id.commentIncome);

        StatisticsModelClass inputOutgoing = new StatisticsModelClass();

        databaseClass = new DatabaseClass(getContext());

    addIncome.setOnClickListener(v -> {
        if (incomeSource.getText().toString().isEmpty()) {
            incomeSource.setError("Indicați sursa");
        } else {
            inputOutgoing.setTvType(String.valueOf(incomeSource.getText()));
        }
        if (amount.getText().toString().isEmpty()) {
            amount.setError("Indicați suma");
        } else {
            inputOutgoing.setTvAmount(Float.parseFloat(String.valueOf(amount.getText())));
        }
        if (incomeTime.getText().toString().isEmpty()) {
            incomeTime.setError("Indicați ora");
        } else {
            inputOutgoing.setTime(String.valueOf(incomeTime.getText()));
        }
        if(incomeDate.getText().toString().isEmpty()){
            incomeDate.setError("Indicați data");
        }else {
            inputOutgoing.setDate(String.valueOf(incomeDate.getText()));
        }
        inputOutgoing.setComment(String.valueOf(comment.getText()));
        inputOutgoing.setRepeat(0);

        //insert values into the database if fields arent empty
        if(!(incomeSource.getText().toString().isEmpty() || incomeSource.getText().toString().isEmpty() ||
        incomeSource.getText().toString().isEmpty() || incomeSource.getText().toString().isEmpty())) {
            databaseClass.addIncome(inputOutgoing);
            Toast.makeText(getContext(), incomeSource.getText().toString() + " a fost adăugat", Toast.LENGTH_SHORT).show();
        }

    });
        return root;
    }
}
