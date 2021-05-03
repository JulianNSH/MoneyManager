package github.julianNSH.moneymanager.ui.overview;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import github.julianNSH.moneymanager.R;

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.MyViewHolder> {
    OverviewFragment context;
    private List<OverviewModelClass> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvType, tvPrice;
        ImageView ivMain;

        public MyViewHolder(View view) {
            super(view);

            tvType = (TextView) view.findViewById(R.id.tvType);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            ivMain = (ImageView) view.findViewById(R.id.ivMain);

        }

    }

    public OverviewAdapter(OverviewFragment context, List<OverviewModelClass> elementsList) {
        this.list = elementsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);

        return new MyViewHolder(itemView);

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
