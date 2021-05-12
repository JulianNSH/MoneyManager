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
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;
import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class AddOutgoingFragment extends Fragment {
    public AddOutgoingFragment(){}
    private Button addOutgoing;
    private Integer icon;
    private EditText amount, comment;
    DatabaseClass databaseClass;

    private DatePickerDialog datePicker;
    private EditText outgoingDate;


    private TimePickerDialog timePicker;
    private EditText outgoingTime;
    private String[] selection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_add_outgoing, container, false);

        //////////////////////////////////INPUT ICON OF OUTGOING
        Integer[]  icons= {R.drawable.cat_food,R.drawable.cat_shoping,R.drawable.cat_coffe,
                R.drawable.cat_house,R.drawable.cat_phone,R.drawable.cat_car,R.drawable.cat_heal,
                R.drawable.cat_fit,R.drawable.cat_pet,R.drawable.cat_movie,R.drawable.cat_gift,
                R.drawable.cat_transport,R.drawable.cat_game,R.drawable.cat_bank,R.drawable.cat_other,};
        Spinner outgoingIconSpinner = (Spinner) root.findViewById(R.id.icon_spinner);
        ArrayAdapter<Integer> arrayIconAdapter = new ArrayAdapter<Integer>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, icons);
        selection = new String[1];
        outgoingIconSpinner.setAdapter(arrayIconAdapter);
        outgoingIconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), "Icon " + getResources().getDrawable(icons[position])+" was selected", Toast.LENGTH_SHORT).show();
                outgoingIconSpinner.setForeground(getResources().getDrawable(icons[position]));
                icon = icons[position];
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @RequiresApi(api = Build.VERSION_CODES.M)

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                outgoingIconSpinner.setForeground(getResources().getDrawable(R.drawable.ic_down));
            }
        });

        //////////////////////////////////INPUT SOURCE OF OUTGOING
        String[]  title= {"Salariu","Cadou","Transfer","Cash-Back"};
        AutoCompleteTextView outgoingSource = root.findViewById(R.id.outgoing_source);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, title);
        selection = new String[1];
        outgoingSource.setAdapter(arrayAdapter);
        outgoingSource.setCursorVisible(true);
        outgoingSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                outgoingSource.showDropDown();
                selection[0] = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), selection[0], Toast.LENGTH_SHORT).show();
            }
        });

        outgoingSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outgoingSource.showDropDown();
            }
        });
        //////////////////////////////////PICK TIME FROM CLOCK
        outgoingTime = (EditText) root.findViewById(R.id.add_income_time);
        outgoingTime.setInputType(InputType.TYPE_NULL);
        outgoingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        outgoingTime.setText(hourOfDay + " : " + minuteOfHour);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        outgoingDate = (EditText) root.findViewById(R.id.add_income_date);
        outgoingDate.setInputType(InputType.TYPE_NULL);
        outgoingDate.setOnClickListener(new View.OnClickListener() {
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
                        outgoingDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                datePicker.show();
            }
        });

    /**********************************************************************************************
        ADD OUTGOING
    */
        addOutgoing = (Button) root.findViewById(R.id.add_outgoing_btn);
        amount = (EditText) root.findViewById(R.id.outgoingAmount);
        comment = (EditText) root.findViewById(R.id.commentOutgoing);

        StatisticsModelClass inputOutgoing = new StatisticsModelClass();

        databaseClass = new DatabaseClass(getContext());

        addOutgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputOutgoing.setIvIcon(icon);
                inputOutgoing.setTvType(String.valueOf(outgoingSource.getText()));
                inputOutgoing.setTvAmount(Float.parseFloat(String.valueOf(amount.getText())));
                inputOutgoing.setComment(String.valueOf(comment.getText()));
                inputOutgoing.setTime(String.valueOf(outgoingTime.getText()));
                inputOutgoing.setDate(String.valueOf(outgoingDate.getText()));
                inputOutgoing.setRepeat(0);
               long id = databaseClass.addOutgoing(inputOutgoing);
               Toast.makeText(getContext(), "Added Outgoing with ID "+id, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
