package github.julianNSH.moneymanager.add;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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
        incomeSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                incomeSource.showDropDown();
                selection[0] = (String) parent.getItemAtPosition(position);
               // Toast.makeText(getContext(), selection[0], Toast.LENGTH_SHORT).show();
            }
        });
        incomeSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeSource.showDropDown();
            }
        });

        //////////////////////////////////PICK TIME FROM CLOCK
        incomeTime = root.findViewById(R.id.add_income_time);
        incomeTime.setInputType(InputType.TYPE_NULL);
        incomeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        incomeTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour));
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        incomeDate = root.findViewById(R.id.add_income_date);
        incomeDate.setInputType(InputType.TYPE_NULL);
        incomeDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(root.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        incomeDate.setText(String.format("%04d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, year, month, day);

                /*android.R.style.Theme_Holo_Dialog,
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);
                */
                datePicker.show();
            }
        });
        /**********************************************************************************************
         ADD Income
         */
        addIncome = root.findViewById(R.id.add_income_btn);
        amount = root.findViewById(R.id.incomeAmount);
        comment = root.findViewById(R.id.commentIncome);

        StatisticsModelClass inputOutgoing = new StatisticsModelClass();

        databaseClass = new DatabaseClass(getContext());

        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputOutgoing.setTvType(String.valueOf(incomeSource.getText()));
                inputOutgoing.setTvAmount(Float.parseFloat(String.valueOf(amount.getText())));
                inputOutgoing.setComment(String.valueOf(comment.getText()));
                inputOutgoing.setTime(String.valueOf(incomeTime.getText()));
                inputOutgoing.setDate(String.valueOf(incomeDate.getText()));
                inputOutgoing.setRepeat(0);
                long id = databaseClass.addIncome(inputOutgoing);
                Toast.makeText(getContext(), "Added Income with ID "+id, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
