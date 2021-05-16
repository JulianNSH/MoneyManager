package github.julianNSH.moneymanager.savings;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.julianNSH.moneymanager.R;

public class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.MyViewHolder> {
    SavingsFragment context;
    private final List<SavingsModelClass> list;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView savingsTitlePeriod, savingsIncome, savingsOutcome, savingsResult;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            savingsTitlePeriod = itemView.findViewById(R.id.savingsTitlePeriod);
            savingsIncome = itemView.findViewById(R.id.savingIncome);
            savingsOutcome = itemView.findViewById(R.id.savingOutcome);
            savingsResult = itemView.findViewById(R.id.savingsResult);
        }
    }

    public SavingsAdapter(SavingsFragment context, List<SavingsModelClass> list){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.savings_element, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SavingsModelClass element = list.get(position);
        int color;
        if(element.getTvIncome()>=element.getTvOutgoings()){
            color = context.getResources().getColor(R.color.green2);
        } else {color = context.getResources().getColor(R.color.red2);}
        
        holder.savingsTitlePeriod.setText(element.getTvTitlePeriod());
        holder.savingsIncome.setText(element.getTvIncome()+" "+context.getResources().getString(R.string.currency));
        holder.savingsOutcome.setText(element.getTvOutgoings()+" "+context.getResources().getString(R.string.currency));
        holder.savingsResult.setText(element.getTvResult()+" "+context.getResources().getString(R.string.currency));
        holder.savingsResult.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
