package github.julianNSH.moneymanager.overview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import github.julianNSH.moneymanager.CustomDateParser;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;
import github.julianNSH.moneymanager.savings.OutgoingHandler;


public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.MyViewHolder> {
    private final Context context;
    private final List<OverviewModelClass> list;
    Dialog itemOverviewDialog;
    DatabaseClass databaseClass;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout rvItem;
        private final TextView tvType;
        private final TextView tvPrice;
        private final TextView tvDateTime;
        private final ImageView ivFigure;

        public MyViewHolder(View view) {
            super(view);
            rvItem = view.findViewById(R.id.list_element);
            tvType = view.findViewById(R.id.tvType);
            tvPrice = view.findViewById(R.id.tvPrice);
            ivFigure = view.findViewById(R.id.ivFigure);
            tvDateTime = view.findViewById(R.id.date_time);

        }
    }

    public OverviewAdapter(Context context, List<OverviewModelClass> elementsList) {
        this.list = elementsList;
        this.context = context;
    }

    @NotNull
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);

        //Popup dialog window

        itemOverviewDialog = new Dialog(context);
        itemOverviewDialog.setContentView(R.layout.overview_dialog);
        itemOverviewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.rvItem.setOnClickListener(v -> {

            TextView tv_domain = itemOverviewDialog.findViewById(R.id.tv_domain);
            TextView tv_category = itemOverviewDialog.findViewById(R.id.tv_category);
            TextView tv_date_time = itemOverviewDialog.findViewById(R.id.tv_date_time);
            TextView tv_amount = itemOverviewDialog.findViewById(R.id.tv_amount);
            TextView tv_comment = itemOverviewDialog.findViewById(R.id.tv_comment);

            onDeleteButtonClick(itemView, viewHolder);
            onUpdateButtonClick(itemView, list, viewHolder);

            tv_domain.setText(list.get(viewHolder.getAdapterPosition()).getTvDomain());
            tv_category.setText(list.get(viewHolder.getAdapterPosition()).getTvType());
            tv_date_time.setText(list.get(viewHolder.getAdapterPosition()).getTime()+" " +
                    list.get(viewHolder.getAdapterPosition()).getDate());
            tv_comment.setText(list.get(viewHolder.getAdapterPosition()).getComment());
            tv_amount.setText(list.get(viewHolder.getAdapterPosition()).getTvAmount() + " "+itemView.getResources().getString(R.string.currency));

            //Toast.makeText(context, "Test Click" + String.valueOf(viewHolder.getAdapterPosition()),  Toast.LENGTH_SHORT).show();
            itemOverviewDialog.show();
        });

        itemOverviewDialog.setOnDismissListener(dialog -> { });
        return viewHolder;

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        OverviewModelClass element = list.get(position);
        holder.tvType.setText(element.getTvType());
        holder.tvPrice.setText(element.getTvAmount() + " MDL");
        holder.ivFigure.setImageResource(element.getIvFigure());
        holder.tvDateTime.setText(CustomDateParser.customDateParser(element.getDate(), "dd MMMM")+" "+element.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    /*********************************************************************************************
     *  DELETE ELEMENT FROM LIST THROUGH DIALOG WINDOW
     */
    public void onDeleteButtonClick(View itemView, OverviewAdapter.MyViewHolder viewHolder) {
        Button btn_delete = itemOverviewDialog.findViewById(R.id.btn_delete);

        databaseClass = new DatabaseClass(itemView.getContext());

        btn_delete.setOnClickListener(v-> {
                databaseClass.deleteFromOverview(list.get(viewHolder.getAdapterPosition()).getId(),
                        list.get(viewHolder.getAdapterPosition()).getTvDomain());
                Toast.makeText(context, list.get(viewHolder.getAdapterPosition()).getTvType() +
                        " a fost șters", Toast.LENGTH_SHORT).show();
                itemOverviewDialog.dismiss();
        });
    }
    /*********************************************************************************************
     *  UPDATE ELEMENT FROM LIST THROUGH DIALOG WINDOW
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onUpdateButtonClick(View itemView, List<OverviewModelClass> list, OverviewAdapter.MyViewHolder viewHolder) {
        Button btn_update = itemOverviewDialog.findViewById(R.id.btn_edit);

        databaseClass = new DatabaseClass(itemView.getContext());

        btn_update.setOnClickListener(v->{
//                    Toast.makeText(context, "Button EDIT Clicked", Toast.LENGTH_SHORT).show();
                    itemOverviewDialog.dismiss();
                    UpdateFromOverviewHandler handler = new UpdateFromOverviewHandler();
                    handler.updateHandler(itemView, list, viewHolder);
        });
    }
}
