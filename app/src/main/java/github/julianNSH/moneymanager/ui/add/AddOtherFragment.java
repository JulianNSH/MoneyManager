package github.julianNSH.moneymanager.ui.add;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import github.julianNSH.moneymanager.R;

public class AddOtherFragment extends Fragment {
    public AddOtherFragment(){}

    DatePickerDialog datePicker;
    EditText otherTransactionDate;

    TimePickerDialog timePicker;
    EditText otherTransactionTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_other, container, false);

        //////////////////////////////////INPUT OTHER SOURCES
        String[]  title= {"Salariu","Cadou","Transfer","Cash-Back"};
        AutoCompleteTextView otherSource = root.findViewById(R.id.other_source);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, title);
        final String[] selection = new String[1];
        otherSource.setAdapter(arrayAdapter);
        otherSource.setCursorVisible(true);
        otherSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                otherSource.showDropDown();
                selection[0] = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), selection[0], Toast.LENGTH_SHORT).show();
            }
        });

        otherSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherSource.showDropDown();
            }
        });

        //////////////////////////////////PICK TIME FROM CLOCK
        otherTransactionTime = (EditText) root.findViewById(R.id.add_income_time);
        otherTransactionTime.setInputType(InputType.TYPE_NULL);
        otherTransactionTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        otherTransactionTime.setText(hourOfDay + " : " + minuteOfHour);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        otherTransactionDate = (EditText) root.findViewById(R.id.add_income_date);
        otherTransactionDate.setInputType(InputType.TYPE_NULL);
        otherTransactionDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(root.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        otherTransactionDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                /*android.R.style.Theme_Holo_Dialog,
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);
                */
                datePicker.show();
            }
        });

        return root;
    }
}
