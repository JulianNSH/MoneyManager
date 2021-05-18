package github.julianNSH.moneymanager.scope;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.infideap.stylishwidget.view.AProgressBar;

import java.util.List;

import github.julianNSH.moneymanager.CustomDateParser;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;

public class ScopeAdapter extends RecyclerView.Adapter<ScopeAdapter.MyViewHolder> {
    private Context context;

    private List<ScopeModelClass> list;
    Dialog itemScopeDialog;
    DatabaseClass databaseClass;
    Button updButton, btnComplete;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView scopeTitle, value_of_progress, scope_start_DT, scope_end_DT;
        private AProgressBar progressBar;
        private LinearLayout rvItem;
        private ImageView isCompleted;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rvItem = (LinearLayout) itemView.findViewById(R.id.scope_element);
            scope_start_DT = (TextView) itemView.findViewById(R.id.scope_start_DT);
            scope_end_DT = (TextView) itemView.findViewById(R.id.scope_end_DT);
            scopeTitle = (TextView) itemView.findViewById(R.id.scopeTitle);
            value_of_progress = (TextView) itemView.findViewById(R.id.value_of_progress);
            progressBar = (AProgressBar) itemView.findViewById(R.id.pb_scope);
            isCompleted = (ImageView) itemView.findViewById(R.id.isCompletedCheck);
        }
    }

    public ScopeAdapter(Context context, List<ScopeModelClass> list ){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scope_element, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        //Popup dialog window for elements
        itemScopeDialog = new Dialog(context);
        itemScopeDialog.setContentView(R.layout.scope_dialog);
        itemScopeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        databaseClass = new DatabaseClass(context);

        for (int i =0; i<list.size(); i++){
            list.get(i).setCurrentAmount(databaseClass.getTotalScopeById(list.get(i).getId()));
        }

        viewHolder.rvItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View v) {
                TextView tvTitle = (TextView) itemScopeDialog.findViewById(R.id.tv_title);
                TextView tvStartDateTime = (TextView) itemScopeDialog.findViewById(R.id.tv_start_datetime);
                TextView tvEndDateTime = (TextView) itemScopeDialog.findViewById(R.id.tv_end_datetime);
                TextView tvTotalAmount = (TextView) itemScopeDialog.findViewById(R.id.tv_total_amount);
                TextView tvCurrentAmount = (TextView) itemScopeDialog.findViewById(R.id.tv_current_amount);
                AProgressBar progressBar = (AProgressBar) itemScopeDialog.findViewById(R.id.pb_scope);
                TextView tvProgressValue = (TextView) itemScopeDialog.findViewById(R.id.value_of_progress);
                TextView tvComment = (TextView) itemScopeDialog.findViewById(R.id.tv_comment);

                tvTitle.setText(list.get(viewHolder.getAdapterPosition()).getTvTitle());
                tvStartDateTime.setText(list.get(viewHolder.getAdapterPosition()).getStartTime()+" "+
                        list.get(viewHolder.getAdapterPosition()).getStartDate());
                tvEndDateTime.setText(list.get(viewHolder.getAdapterPosition()).getEndTime()+" "+
                        list.get(viewHolder.getAdapterPosition()).getEndDate());
                tvTotalAmount.setText(list.get(viewHolder.getAdapterPosition()).getTvFinalAmount()+" "+
                        v.getResources().getString(R.string.currency));
                tvCurrentAmount.setText(list.get(viewHolder.getAdapterPosition()).getCurrentAmount()+ " "+
                        v.getResources().getString(R.string.currency));
                progressBar.setProgressValue(list.get(viewHolder.getAdapterPosition()).getCurrentAmount()*100/
                        list.get(viewHolder.getAdapterPosition()).getTvFinalAmount());
                tvProgressValue.setText(String.format("%3.1f",list.get(viewHolder.getAdapterPosition()).getCurrentAmount()*100/
                        list.get(viewHolder.getAdapterPosition()).getTvFinalAmount())+ " %");
                tvComment.setText(list.get(viewHolder.getAdapterPosition()).getComment());

                //Hide update button on scope completed
                onDeleteButtonClick(itemView, viewHolder);
                onUpdateButtonClick(itemView,viewHolder);

                //if scope get necessary amount of money user can complete it and all scope transactions
                //transfers to outgoings
                btnComplete = (Button) itemScopeDialog.findViewById(R.id.btn_complete);
                btnComplete.setVisibility(LinearLayout.GONE);

                updButton = itemScopeDialog.findViewById(R.id.btn_edit);
                if(list.get(viewHolder.getAdapterPosition()).getIsCompleted()==0)
                    updButton.setVisibility(LinearLayout.VISIBLE);

                if(list.get(viewHolder.getAdapterPosition()).getCurrentAmount()>=
                        list.get(viewHolder.getAdapterPosition()).getTvFinalAmount() ) {
                    btnComplete.setVisibility(LinearLayout.VISIBLE);
                    onCompleteButtonClick(viewHolder);
                }



                itemScopeDialog.show();
            }
        });

        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScopeModelClass element = list.get(position);
        holder.scopeTitle.setText(element.getTvTitle());
        holder.scope_start_DT.setText("De la:  "+element.getStartTime()+" "+CustomDateParser.customDateParser(element.getStartDate(), "dd MMMM yyyy"));
        holder.scope_end_DT.setText("Până la: "+element.getEndTime()+" "+ CustomDateParser.customDateParser(element.getEndDate(), "dd MMMM yyyy"));
        holder.value_of_progress.setText(element.getCurrentAmount()+" / "+element.getTvFinalAmount() + " "+ context.getResources().getString(R.string.currency));
        holder.progressBar.setProgressValue(element.getCurrentAmount()*100/element.getTvFinalAmount());
        if(element.getIsCompleted()==1) holder.isCompleted.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /*********************************************************************************************
     *  DELETE ELEMENT FROM LIST THROUGH DIALOG WINDOW
     */
    public void onDeleteButtonClick(View itemView, MyViewHolder viewHolder) {
        Button btn_delete = (Button) itemScopeDialog.findViewById(R.id.btn_delete);

        DatabaseClass databaseClass = new DatabaseClass(itemView.getContext());

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseClass.deleteScope(list.get(viewHolder.getAdapterPosition()).getId());
                Toast.makeText(context, list.get(viewHolder.getAdapterPosition()).getTvTitle() +
                        " was deleted", Toast.LENGTH_SHORT).show();
                itemScopeDialog.dismiss();


            }
        });
    }
    /*********************************************************************************************
     *  UPDATE ELEMENT FROM LIST THROUGH DIALOG WINDOW
     */
    public  void onUpdateButtonClick(View itemView, MyViewHolder viewHolder){
        Button btn_edit = (Button) itemScopeDialog.findViewById(R.id.btn_edit);

        DatabaseClass databaseClass = new DatabaseClass(itemView.getContext());
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button EDIT Clicked",  Toast.LENGTH_SHORT).show();
                itemScopeDialog.dismiss();
                ScopeHandler handler = new ScopeHandler();
                handler.callOutgoingHandler(itemView,list, viewHolder);
            }
        });
        itemScopeDialog.show();
    }
    /*********************************************************************************************
     *  COMPLETED SCOPE -> OUTGOINGS
     */
    public  void onCompleteButtonClick(MyViewHolder viewHolder){
        updButton = itemScopeDialog.findViewById(R.id.btn_edit);
        if(list.get(viewHolder.getAdapterPosition()).getIsCompleted()==1) {
            btnComplete.setVisibility(LinearLayout.GONE);
            updButton.setVisibility(LinearLayout.GONE);
        }

        Button btnComplete = (Button) itemScopeDialog.findViewById(R.id.btn_complete);
        DatabaseClass databaseClass = new DatabaseClass(context);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(viewHolder.getAdapterPosition()).setIsCompleted(1);
                list.get(viewHolder.getAdapterPosition()).setTvInitialAmount(0);
                list.get(viewHolder.getAdapterPosition()).setTvInitialAmount(
                        list.get(viewHolder.getAdapterPosition()).getTvFinalAmount());
                databaseClass.updateScope(list.get(viewHolder.getAdapterPosition()));
                databaseClass.convertScopesToOutgoings(list.get(viewHolder.getAdapterPosition()).getId());
                Toast.makeText(context, "Scope completed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}