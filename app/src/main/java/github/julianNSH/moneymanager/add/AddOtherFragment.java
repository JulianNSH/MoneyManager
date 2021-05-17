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
import java.util.HashMap;
import java.util.Map;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;
import github.julianNSH.moneymanager.scope.ScopeModelClass;

public class AddOtherFragment extends Fragment {
    public AddOtherFragment(){}

    private DatePickerDialog datePicker;
    private EditText otherTransactionDate;
    private TimePickerDialog timePicker;
    private EditText otherTransactionTime, amount, comment;
    private DatabaseClass databaseClass;
    private Button addAtSource;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_other, container, false);

        //////////////////////////////////INPUT OTHER SOURCES
        databaseClass = new DatabaseClass(getContext());
        HashMap<Integer, String> titles = databaseClass.getScopes();
        AutoCompleteTextView otherSource = root.findViewById(R.id.other_source);
        ArrayList<String> title = new ArrayList<>(titles.values());
        
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,title);
        final String[] selection = new String[1];
        otherSource.setAdapter(arrayAdapter);
        otherSource.setInputType(InputType.TYPE_NULL);
        otherSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                otherSource.showDropDown();
                selection[0] = (String) parent.getItemAtPosition(position);
                //Toast.makeText(getContext(), selection[0], Toast.LENGTH_SHORT).show();
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
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        otherTransactionTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour));
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
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        otherTransactionDate.setText(String.format("%04d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, year, month, day);

                datePicker.show();
            }
        });
        /**********************************************************************************************
         ADD SOURCE VALUE
         */
        addAtSource = (Button) root.findViewById(R.id.add_scope_btn);
        amount = (EditText) root.findViewById(R.id.current_amount);
        comment = (EditText) root.findViewById(R.id.commentScopeVal);

        ScopeModelClass inputSourceVal = new ScopeModelClass();


        //TODO Reload

        databaseClass = new DatabaseClass(getContext());

        addAtSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int commonId = 0;
                for (Map.Entry<Integer, String> i:titles.entrySet()) {
                    if(i.getValue().equals(selection[0])){
                        commonId=i.getKey();
                        break;
                    }
                }
                inputSourceVal.setTvTitle(selection[0]);
                inputSourceVal.setTvInitialAmount(Float.parseFloat(String.valueOf(amount.getText())));
                inputSourceVal.setComment(String.valueOf(comment.getText()));
                inputSourceVal.setTime(String.valueOf(otherTransactionTime.getText()));
                inputSourceVal.setDate(String.valueOf(otherTransactionDate.getText()));
                inputSourceVal.setGeneralId(commonId);
                inputSourceVal.setRepeat(0);
                long id = databaseClass.addScopeValue(inputSourceVal);
                Toast.makeText(getContext(), "Added Source values with ID "+id, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
