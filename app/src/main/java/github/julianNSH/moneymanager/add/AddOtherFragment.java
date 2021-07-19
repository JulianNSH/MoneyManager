package github.julianNSH.moneymanager.add;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.widget.*;

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
    @SuppressLint("DefaultLocale")
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
        otherSource.setOnItemClickListener((parent, view, position, id) -> {
            otherSource.showDropDown();
            selection[0] = (String) parent.getItemAtPosition(position);
            //Toast.makeText(getContext(), selection[0], Toast.LENGTH_SHORT).show();
        });

        otherSource.setOnClickListener(v-> otherSource.showDropDown());

        //////////////////////////////////PICK TIME FROM CLOCK
        otherTransactionTime = (EditText) root.findViewById(R.id.add_income_time);
        otherTransactionTime.setInputType(InputType.TYPE_NULL);
        otherTransactionTime.setOnClickListener(v -> {
            Calendar time = Calendar.getInstance();
            int hour = time.get(Calendar.HOUR_OF_DAY);
            int minute = time.get(Calendar.MINUTE);

            timePicker = new TimePickerDialog(root.getContext(),
                    (view, hourOfDay, minuteOfHour) ->
                            otherTransactionTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour)), hour, minute, true);
            timePicker.show();
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        otherTransactionDate = (EditText) root.findViewById(R.id.add_income_date);
        otherTransactionDate.setInputType(InputType.TYPE_NULL);
        otherTransactionDate.setOnClickListener(v-> {
                final Calendar date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);
                datePicker = new DatePickerDialog(root.getContext(),
                        (view, year1, month1, dayOfMonth) ->
                                otherTransactionDate.setText(String.format("%04d-%02d-%02d", year1, month1 +1, dayOfMonth)), year, month, day);
                datePicker.show();

        });
        /**********************************************************************************************
         ADD SOURCE VALUE
         */
        addAtSource = (Button) root.findViewById(R.id.add_scope_btn);
        amount = (EditText) root.findViewById(R.id.current_amount);
        comment = (EditText) root.findViewById(R.id.commentScopeVal);

        ScopeModelClass inputSourceVal = new ScopeModelClass();

        databaseClass = new DatabaseClass(getContext());

        addAtSource.setOnClickListener(v -> {
            int commonId = 0;
            for (Map.Entry<Integer, String> i:titles.entrySet()) {
                if(i.getValue().equals(selection[0])){
                    commonId=i.getKey();
                    break;
                }
            }

            @SuppressLint("CutPasteId") AutoCompleteTextView otherSource1 = root.findViewById(R.id.other_source);
            if(otherSource1.getText().toString().isEmpty()){
                otherSource1.setError("Indicați scopul");
            } else {
                inputSourceVal.setTvTitle(selection[0]);
            }
            if (amount.getText().toString().isEmpty()){
                amount.setError("Indicați suma");
            }else{
                inputSourceVal.setTvInitialAmount(Float.parseFloat(String.valueOf(amount.getText())));
            }
            if(otherTransactionTime.getText().toString().isEmpty()){
                otherTransactionTime.setError("Introduceți ora");
            }else{
                inputSourceVal.setTime(String.valueOf(otherTransactionTime.getText()));
            }
            if(otherTransactionDate.getText().toString().isEmpty()){
             otherTransactionDate.setError("Introduceți data");
            } else {
                inputSourceVal.setDate(String.valueOf(otherTransactionDate.getText()));
            }
            inputSourceVal.setGeneralId(commonId);
            inputSourceVal.setRepeat(0);
            if(!(otherSource1.getText().toString().isEmpty() || amount.getText().toString().isEmpty()||
                    otherTransactionTime.getText().toString().isEmpty()||otherTransactionDate.getText().toString().isEmpty())) {
                databaseClass.addScopeValue(inputSourceVal);
                Toast.makeText(getContext(), selection[0] + " a fost adăugat", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
