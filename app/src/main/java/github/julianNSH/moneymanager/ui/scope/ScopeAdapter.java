package github.julianNSH.moneymanager.ui.scope;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.infideap.stylishwidget.view.AProgressBar;

import java.util.List;

import github.julianNSH.moneymanager.R;

public class ScopeAdapter extends RecyclerView.Adapter<ScopeAdapter.MyViewHolder> {
    private Context context;

    private List<ScopeModelClass> list;
    Dialog itemScopeDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView scopeTitle, value_of_progress, scope_start_DT, scope_end_DT;
        private AProgressBar progressBar;
        private LinearLayout rvItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rvItem = (LinearLayout) itemView.findViewById(R.id.scope_element);
            scope_start_DT = (TextView) itemView.findViewById(R.id.scope_start_DT);
            scope_end_DT = (TextView) itemView.findViewById(R.id.scope_end_DT);
            scopeTitle = (TextView) itemView.findViewById(R.id.scopeTitle);
            value_of_progress = (TextView) itemView.findViewById(R.id.value_of_progress);
            progressBar = (AProgressBar) itemView.findViewById(R.id.pb_scope);
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


        viewHolder.rvItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
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
                tvTotalAmount.setText(list.get(viewHolder.getAdapterPosition()).getTvFinalAmount()+" MDL");
                tvCurrentAmount.setText(list.get(viewHolder.getAdapterPosition()).getTvCurrentAmount()+ " MDL");
                progressBar.setProgressValue(list.get(viewHolder.getAdapterPosition()).getPbProgressValue());
                tvProgressValue.setText(list.get(viewHolder.getAdapterPosition()).getPbProgressValue() + " %");
                tvComment.setText(list.get(viewHolder.getAdapterPosition()).getComment());

                Button btn_delete = (Button) itemScopeDialog.findViewById(R.id.btn_delete);
                Button btn_edit = (Button) itemScopeDialog.findViewById(R.id.btn_edit);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Button DELETE Clicked",  Toast.LENGTH_SHORT).show();
                    }
                });
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Button EDIT Clicked",  Toast.LENGTH_SHORT).show();
                    }
                });

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
        holder.scope_start_DT.setText(element.getStartTime()+" "+element.getStartDate());
        holder.scope_end_DT.setText(element.getEndTime()+" "+element.getEndDate());
        holder.value_of_progress.setText(element.getTvCurrentAmount()+" / "+element.getTvFinalAmount() + " MDL");
        holder.progressBar.setProgressValue(element.getPbProgressValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}