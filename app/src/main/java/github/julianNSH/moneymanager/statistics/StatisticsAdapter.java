package github.julianNSH.moneymanager.statistics;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import github.julianNSH.moneymanager.CustomDateParser;
import github.julianNSH.moneymanager.R;
import github.julianNSH.moneymanager.database.DatabaseClass;
import github.julianNSH.moneymanager.savings.OutgoingHandler;


public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.MyViewHolder>{
    Context context;
    DatabaseClass databaseClass;

    private final List<StatisticsModelClass> list;
    Dialog itemStatisticsDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvType,tvPrice, tvDateTime;
        private final ImageView ivFigure;
        private final LinearLayout rvItem;

        public MyViewHolder(View view) {
            super(view);
            rvItem = view.findViewById(R.id.list_element);
            tvType = view.findViewById(R.id.tvType);
            tvPrice = view.findViewById(R.id.tvPrice);
            ivFigure = view.findViewById(R.id.ivFigure);
            tvDateTime = view.findViewById(R.id.date_time);
        }
        public String getCurrency(){return itemView.getResources().getString(R.string.currency);}
    }

    public StatisticsAdapter(Context context, List<StatisticsModelClass> elementsList) {
        this.list = elementsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        itemStatisticsDialog = new Dialog(context);
        itemStatisticsDialog.setContentView(R.layout.statistics_dialog);
        itemStatisticsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /*********************************************************************************************
         *  CREATES DIALOG WINDOW ON CLICK ON A ELEMENT OF LIST
         */
        viewHolder.rvItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceType", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                TextView tv_category = itemStatisticsDialog.findViewById(R.id.tv_category);
                TextView tv_date_time = itemStatisticsDialog.findViewById(R.id.tv_date_time);
                TextView tv_amount = itemStatisticsDialog.findViewById(R.id.tv_amount);
                TextView tv_comment = itemStatisticsDialog.findViewById(R.id.tv_comment);

                tv_category.setText(list.get(viewHolder.getAdapterPosition()).getTvType());
                tv_date_time.setText(list.get(viewHolder.getAdapterPosition()).getTime()+" "+
                        list.get(viewHolder.getAdapterPosition()).getDate());
                tv_amount.setText(list.get(viewHolder.getAdapterPosition()).getTvAmount() + " "+
                        v.getResources().getString(R.string.currency));
                tv_comment.setText(list.get(viewHolder.getAdapterPosition()).getComment());

                //CALL METHODS FOR BUTTON CLICKS
                onDeleteButtonClick(itemView, viewHolder);

                onUpdateButtonClick(itemView, list, viewHolder);
            }
        });

        return viewHolder;

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        StatisticsModelClass element = list.get(position);
        holder.tvType.setText(element.getTvType());
        holder.tvPrice.setText(element.getTvAmount() + " " +holder.getCurrency());
        holder.tvDateTime.setText(CustomDateParser.customDateParser(element.getDate(), "dd MMMM")+" "+element.getTime());
        holder.ivFigure.setImageResource(element.getIvIcon());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    /*********************************************************************************************
     *  DELETE ELEMENT FROM LIST THROUGH DIALOG WINDOW
     */
    public void onDeleteButtonClick(View itemView, MyViewHolder viewHolder) {
        Button btn_delete = itemStatisticsDialog.findViewById(R.id.btn_delete);

        databaseClass = new DatabaseClass(itemView.getContext());

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseClass.deleteOutgoing(list.get(viewHolder.getAdapterPosition()).getId());
                Toast.makeText(context, list.get(viewHolder.getAdapterPosition()).getTvType() +
                        " was deleted", Toast.LENGTH_SHORT).show();
                itemStatisticsDialog.dismiss();
                

            }
        });
    }
    /*********************************************************************************************
     *  UPDATE ELEMENT FROM LIST THROUGH DIALOG WINDOW
     */
    public  void onUpdateButtonClick( View itemView, List<StatisticsModelClass> list, MyViewHolder viewHolder){
        Button btn_edit = itemStatisticsDialog.findViewById(R.id.btn_edit);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Button EDIT Clicked",  Toast.LENGTH_SHORT).show();
                itemStatisticsDialog.dismiss();
                OutgoingHandler handler = new OutgoingHandler();
                handler.callOutgoingHandler(itemView, list, viewHolder);
            }
        });
        itemStatisticsDialog.show();
    }
}
