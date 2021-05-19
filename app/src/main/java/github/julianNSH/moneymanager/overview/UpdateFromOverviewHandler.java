package github.julianNSH.moneymanager.overview;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;
import github.julianNSH.moneymanager.statistics.StatisticsAdapter;
import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class UpdateFromOverviewHandler {
    UpdateFromOverviewHandler(){}
    private Button updElement;
    private EditText amount, comment;
    DatabaseClass databaseClass;

    private DatePickerDialog datePicker;
    private EditText date;
    Dialog updateDialog;

    private TimePickerDialog timePicker;
    private EditText time;
    private String[] selection;

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void updateHandler(@NotNull View root, List<OverviewModelClass> list, OverviewAdapter.MyViewHolder viewHolder){

        updateDialog = new Dialog(root.getContext());
        updateDialog.setContentView(R.layout.general_upd_dialog);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //////////////////////////////////INPUT SOURCE OF OUTGOING
        databaseClass = new DatabaseClass(root.getContext());
        ArrayList<String> title = databaseClass.getDistinctOutgoings();
        AutoCompleteTextView source = updateDialog.findViewById(R.id.outgoing_source);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_list_item_1, title);
        selection = new String[1];
        source.setAdapter(arrayAdapter);
        source.setCursorVisible(true);
        source.setText(list.get(viewHolder.getAdapterPosition()).getTvType());
        source.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                source.showDropDown();
                selection[0] = (String) parent.getItemAtPosition(position);
                Toast.makeText(root.getContext(), selection[0], Toast.LENGTH_SHORT).show();
            }
        });

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source.showDropDown();
            }
        });
        //////////////////////////////////PICK TIME FROM CLOCK
        time = updateDialog.findViewById(R.id.add_income_time);
        time.setInputType(InputType.TYPE_NULL);
        time.setText(list.get(viewHolder.getAdapterPosition()).getTime());
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        UpdateFromOverviewHandler.this.time.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour));
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        date = updateDialog.findViewById(R.id.add_income_date);
        date.setInputType(InputType.TYPE_NULL);
        date.setText(list.get(viewHolder.getAdapterPosition()).getDate());
        date.setOnClickListener(new View.OnClickListener() {
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
                        UpdateFromOverviewHandler.this.date.setText(String.format("%04d-%02d-%02d", year, month+1, dayOfMonth));
                    }
                }, year, month, day);

                datePicker.show();
            }
        });


        /**********************************************************************************************
         Update OUTGOING
         */


        updElement = updateDialog.findViewById(R.id.btn_edit);
        amount = updateDialog.findViewById(R.id.amount);
        amount.setText(String.valueOf(list.get(viewHolder.getAdapterPosition()).getTvAmount()));
        comment = updateDialog.findViewById(R.id.comment);
        if(list.get(viewHolder.getAdapterPosition()).getComment()!=null)
            comment.setText(list.get(viewHolder.getAdapterPosition()).getComment());

        OverviewModelClass element = new OverviewModelClass();

        databaseClass = new DatabaseClass(root.getContext());

        updElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element.setId(list.get(viewHolder.getAdapterPosition()).getId());
                element.setTvDomain(String.valueOf(list.get(viewHolder.getAdapterPosition()).getTvDomain()));
                element.setTvType(String.valueOf(source.getText()));
                element.setTvAmount(Float.parseFloat(String.valueOf(amount.getText())));
                element.setComment(String.valueOf(comment.getText()));
                element.setTime(String.valueOf(time.getText()));
                element.setDate(String.valueOf(date.getText()));
                element.setRepeat(0);
                long id = databaseClass.updateFromOverview(element);
                Toast.makeText(root.getContext(), source.getText()+" SALVAT", Toast.LENGTH_SHORT).show();
                updateDialog.dismiss();
            }

        });

        updateDialog.show();
    }
}
