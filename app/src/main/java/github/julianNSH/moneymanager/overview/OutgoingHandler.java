package github.julianNSH.moneymanager.overview;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.InputType;
import android.view.View;
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

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;
import github.julianNSH.moneymanager.statistics.StatisticsAdapter;
import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

import static android.app.PendingIntent.getActivity;

public class OutgoingHandler {
    public OutgoingHandler(){}
    private Button addOutgoing;
    private Integer icon;
    private EditText amount, comment;
    DatabaseClass databaseClass;

    private DatePickerDialog datePicker;
    private EditText outgoingDate;
    Dialog updateDialog;

    private TimePickerDialog timePicker;
    private EditText outgoingTime;
    private String[] selection;

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void callOutgoingHandler(@NotNull View root, List<StatisticsModelClass> list, StatisticsAdapter.MyViewHolder viewHolder){

        updateDialog = new Dialog(root.getContext());
        updateDialog.setContentView(R.layout.update_outgoing_dialog);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //////////////////////////////////INPUT ICON OF OUTGOING
        Integer[]  icons= {R.drawable.cat_food,R.drawable.cat_shoping,R.drawable.cat_coffe,
                R.drawable.cat_house,R.drawable.cat_phone,R.drawable.cat_car,R.drawable.cat_heal,
                R.drawable.cat_fit,R.drawable.cat_pet,R.drawable.cat_movie,R.drawable.cat_gift,
                R.drawable.cat_transport,R.drawable.cat_game,R.drawable.cat_bank,R.drawable.cat_other,};

        Spinner outgoingIconSpinner = updateDialog.findViewById(R.id.icon_spinner);

        ArrayAdapter<Integer> arrayIconAdapter = new ArrayAdapter<Integer>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, icons);
        selection = new String[1];
        outgoingIconSpinner.setAdapter(arrayIconAdapter);
        outgoingIconSpinner.setForeground(root.getResources().getDrawable(list.get(viewHolder.
                getAdapterPosition()).getIvIcon()));
        outgoingIconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), "Icon " + getResources().getDrawable(icons[position])+" was selected", Toast.LENGTH_SHORT).show();
                outgoingIconSpinner.setForeground(root.getResources().getDrawable(icons[position]));
                icon = icons[position];
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @RequiresApi(api = Build.VERSION_CODES.M)

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                outgoingIconSpinner.setForeground(root.getResources().getDrawable(R.drawable.ic_down));
            }
        });

        //////////////////////////////////INPUT SOURCE OF OUTGOING
        String[]  title= {"Salariu","Cadou","Transfer","Cash-Back"};
        AutoCompleteTextView outgoingSource = updateDialog.findViewById(R.id.outgoing_source);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_list_item_1, title);
        selection = new String[1];
        outgoingSource.setAdapter(arrayAdapter);
        outgoingSource.setCursorVisible(true);
        outgoingSource.setText(list.get(viewHolder.getAdapterPosition()).getTvType());
        outgoingSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                outgoingSource.showDropDown();
                selection[0] = (String) parent.getItemAtPosition(position);
                Toast.makeText(root.getContext(), selection[0], Toast.LENGTH_SHORT).show();
            }
        });

        outgoingSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outgoingSource.showDropDown();
            }
        });
        //////////////////////////////////PICK TIME FROM CLOCK
        outgoingTime = updateDialog.findViewById(R.id.add_income_time);
        outgoingTime.setInputType(InputType.TYPE_NULL);
        outgoingTime.setText(list.get(viewHolder.getAdapterPosition()).getTime());
        outgoingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        outgoingTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour));
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        outgoingDate = updateDialog.findViewById(R.id.add_income_date);
        outgoingDate.setInputType(InputType.TYPE_NULL);
        outgoingDate.setText(list.get(viewHolder.getAdapterPosition()).getDate());
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
         Update OUTGOING
         */


        addOutgoing = updateDialog.findViewById(R.id.btn_edit);
        amount = updateDialog.findViewById(R.id.outgoingAmount);
        amount.setText(String.valueOf(list.get(viewHolder.getAdapterPosition()).getTvAmount()));
        comment = updateDialog.findViewById(R.id.commentOutgoing);
        if(list.get(viewHolder.getAdapterPosition()).getComment()!=null)
            comment.setText(list.get(viewHolder.getAdapterPosition()).getComment());

        StatisticsModelClass inputOutgoing = new StatisticsModelClass();

        databaseClass = new DatabaseClass(root.getContext());

        addOutgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputOutgoing.setId(list.get(viewHolder.getAdapterPosition()).getId());
                inputOutgoing.setIvIcon(icon);
                inputOutgoing.setTvType(String.valueOf(outgoingSource.getText()));
                inputOutgoing.setTvAmount(Float.parseFloat(String.valueOf(amount.getText())));
                inputOutgoing.setComment(String.valueOf(comment.getText()));
                inputOutgoing.setTime(String.valueOf(outgoingTime.getText()));
                inputOutgoing.setDate(String.valueOf(outgoingDate.getText()));
                inputOutgoing.setRepeat(0);
                long id = databaseClass.updateOutgoing(inputOutgoing);
                Toast.makeText(root.getContext(), "UPDATED Outgoing with ID "+id, Toast.LENGTH_SHORT).show();
                updateDialog.dismiss();
            }
        });

        updateDialog.show();
    }
}
