package github.julianNSH.moneymanager.ui.overview;

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


public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.MyViewHolder> {
    private Context context;
    private List<OverviewModelClass> list;
    Dialog itemOverviewDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout rvItem;
        private TextView tvType, tvPrice;
        private ImageView ivMain;

        public MyViewHolder(View view) {
            super(view);
            rvItem = (LinearLayout) view.findViewById(R.id.list_element);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            ivMain = (ImageView) view.findViewById(R.id.ivMain);

        }
    }


    public OverviewAdapter(Context context, List<OverviewModelClass> elementsList) {
        this.list = elementsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);

        //Popup dialog window

        itemOverviewDialog = new Dialog(context);
        itemOverviewDialog.setContentView(R.layout.overview_dialog);
        itemOverviewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.rvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tv_domain = (TextView) itemOverviewDialog.findViewById(R.id.tv_domain);
                TextView tv_category = (TextView) itemOverviewDialog.findViewById(R.id.tv_category);
                TextView tv_date_time = (TextView) itemOverviewDialog.findViewById(R.id.tv_date_time);
                TextView tv_amount = (TextView) itemOverviewDialog.findViewById(R.id.tv_amount);
                Button btn_delete = (Button) itemOverviewDialog.findViewById(R.id.btn_delete);
                Button btn_edit = (Button) itemOverviewDialog.findViewById(R.id.btn_edit);

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

                tv_domain.setText(list.get(viewHolder.getAdapterPosition()).getTvType());
                tv_category.setText(list.get(viewHolder.getAdapterPosition()).getTvType());
                tv_date_time.setText(list.get(viewHolder.getAdapterPosition()).getTvType());
                tv_amount.setText(list.get(viewHolder.getAdapterPosition()).getTvPrice());

                //Toast.makeText(context, "Test Click" + String.valueOf(viewHolder.getAdapterPosition()),  Toast.LENGTH_SHORT).show();
                itemOverviewDialog.show();
            }
        });

        return viewHolder;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        OverviewModelClass element = list.get(position);
        holder.tvType.setText(element.getTvType());
        holder.tvPrice.setText(element.getTvPrice());
        holder.ivMain.setImageResource(element.getIvMain());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
