package github.julianNSH.moneymanager.scope;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;

public class ScopeHandler {
    public ScopeHandler(){}
    private Button updateButton;

    private EditText amount, comment;
    DatabaseClass databaseClass;

    private DatePickerDialog datePicker;
    Dialog updateDialog;

    private TimePickerDialog timePicker;
    private EditText startScopeTime,startScopeDate,endScopeTime,endScopeDate;
    private Button updButton;

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n", "CutPasteId", "ResourceAsColor"})
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void callOutgoingHandler(@NotNull View root, List<ScopeModelClass> list, ScopeAdapter.MyViewHolder viewHolder){
        updateDialog = new Dialog(root.getContext());
        updateDialog.setContentView(R.layout.scope_add_dialog);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updButton = (Button) updateDialog.findViewById(R.id.btn_add_scope);
        updButton.setText("SALVARE");

        //////////////////////////////////PICK TIME FROM CLOCK
        startScopeTime = (EditText) updateDialog.findViewById(R.id.add_start_time);
        startScopeTime.setInputType(InputType.TYPE_NULL);
        startScopeTime.setText(list.get(viewHolder.getAdapterPosition()).getStartTime());
        startScopeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(updateDialog.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        startScopeTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour));
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });
        endScopeTime = (EditText) updateDialog.findViewById(R.id.add_end_time);
        endScopeTime.setInputType(InputType.TYPE_NULL);
        endScopeTime.setText(list.get(viewHolder.getAdapterPosition()).getEndTime());
        endScopeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(updateDialog.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        endScopeTime.setText(String.format("%02d:%02d",hourOfDay, minuteOfHour));
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        //////////////////////////////////PICK A DATE FROM CALENDAR
        startScopeDate = (EditText) updateDialog.findViewById(R.id.add_start_date);
        startScopeDate.setInputType(InputType.TYPE_NULL);
        startScopeDate.setText(list.get(viewHolder.getAdapterPosition()).getStartDate());
        startScopeDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(updateDialog.getContext(), new DatePickerDialog.OnDateSetListener() {
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
        endScopeDate = (EditText) updateDialog.findViewById(R.id.add_end_date);
        endScopeDate.setInputType(InputType.TYPE_NULL);
        endScopeDate.setText(list.get(viewHolder.getAdapterPosition()).getEndDate());
        endScopeDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar date = Calendar.getInstance();
                int day = date.get(Calendar.DAY_OF_MONTH);
                int month = date.get(Calendar.MONTH);
                int year = date.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(updateDialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endScopeDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
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
         Update SCOPE
         */

        TextView title = (TextView) updateDialog.findViewById(R.id.dialog_title);
        title.setText("Modificarea scopului selectat");
        updateButton = (Button) updateDialog.findViewById(R.id.btn_add_scope);
        EditText scopeTitle = (EditText) updateDialog.findViewById(R.id.scope_title);
        EditText currentAmount = (EditText) updateDialog.findViewById(R.id.current_amount);
        EditText neededAmount = (EditText) updateDialog.findViewById(R.id.total_amount);
        EditText comment = (EditText) updateDialog.findViewById(R.id.scope_comment);
        scopeTitle.setText(list.get(viewHolder.getAdapterPosition()).getTvTitle());
        currentAmount.setText(String.valueOf(list.get(viewHolder.getAdapterPosition()).getTvInitialAmount()));
        neededAmount.setText(String.valueOf(list.get(viewHolder.getAdapterPosition()).getTvFinalAmount()));
        if(list.get(viewHolder.getAdapterPosition()).getComment()!=null)
            comment.setText(list.get(viewHolder.getAdapterPosition()).getComment());

        ScopeModelClass scopeData = new ScopeModelClass();

        databaseClass = new DatabaseClass(root.getContext());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scopeData.setId(list.get(viewHolder.getAdapterPosition()).getId());
                scopeData.setTvTitle(String.valueOf(scopeTitle.getText()));
                scopeData.setTvInitialAmount(Float.parseFloat(String.valueOf(currentAmount.getText())));
                scopeData.setTvFinalAmount(Float.parseFloat(String.valueOf(neededAmount.getText())));
                scopeData.setStartTime(String.valueOf(startScopeTime.getText()));
                scopeData.setStartDate(String.valueOf(startScopeDate.getText()));
                scopeData.setEndTime(String.valueOf(endScopeTime.getText()));
                scopeData.setEndDate(String.valueOf(endScopeDate.getText()));
                scopeData.setComment(String.valueOf(comment.getText()));
                scopeData.setIsCompleted(0);
                long id = databaseClass.updateScope(scopeData);
                Toast.makeText(root.getContext(), "UPDATED Scope with ID "+id, Toast.LENGTH_SHORT).show();
                updateDialog.dismiss();
            }
        });

        updateDialog.show();
    }
}
