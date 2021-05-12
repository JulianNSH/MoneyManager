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
import github.julianNSH.moneymanager.R;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.MyViewHolder>{
    Context context;

    private List<StatisticsModelClass> list;
    Dialog itemStatisticsDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvType,tvPrice, tvDateTime;
        private final ImageView ivFigure;
        private final LinearLayout rvItem;

        public MyViewHolder(View view) {
            super(view);

            rvItem = (LinearLayout) view.findViewById(R.id.list_element);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            ivFigure = (ImageView) view.findViewById(R.id.ivFigure);
            tvDateTime = (TextView) view.findViewById(R.id.date_time);

        }
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
        //Popup dialog window
        itemStatisticsDialog = new Dialog(context);
        itemStatisticsDialog.setContentView(R.layout.statistics_dialog);
        itemStatisticsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.rvItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                TextView tv_category = (TextView) itemStatisticsDialog.findViewById(R.id.tv_category);
                TextView tv_date_time = (TextView) itemStatisticsDialog.findViewById(R.id.tv_date_time);
                TextView tv_amount = (TextView) itemStatisticsDialog.findViewById(R.id.tv_amount);
                TextView tv_comment = (TextView) itemStatisticsDialog.findViewById(R.id.tv_comment);
                Button btn_delete = (Button) itemStatisticsDialog.findViewById(R.id.btn_delete);
                Button btn_edit = (Button) itemStatisticsDialog.findViewById(R.id.btn_edit);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Button DELETE Clicked" + list.get(viewHolder.getAdapterPosition()).getIvIcon(),  Toast.LENGTH_SHORT).show();
                    }
                });
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Button EDIT Clicked",  Toast.LENGTH_SHORT).show();
                    }
                });

                tv_category.setText(list.get(viewHolder.getAdapterPosition()).getTvType());
                tv_date_time.setText(list.get(viewHolder.getAdapterPosition()).getTime() + " " +
                        list.get(viewHolder.getAdapterPosition()).getDate());
                tv_amount.setText(list.get(viewHolder.getAdapterPosition()).getTvAmount() + " MDL");
                tv_comment.setText(list.get(viewHolder.getAdapterPosition()).getComment());

                //Toast.makeText(context, "Test Click" + String.valueOf(viewHolder.getAdapterPosition()),  Toast.LENGTH_SHORT).show();
                itemStatisticsDialog.show();
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
        holder.tvPrice.setText(element.getTvAmount() + " MDL");
        holder.tvDateTime.setText(element.getTime()+" "+element.getDate());
        holder.ivFigure.setImageResource(element.getIvIcon());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}