package github.julianNSH.moneymanager.savings;

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
    private List<SavingsModelClass> list;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView savingsTitlePeriod, savingsIncome, savingsOutcome, savingsResult;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            savingsTitlePeriod = (TextView) itemView.findViewById(R.id.savingsTitlePeriod);
            savingsIncome = (TextView) itemView.findViewById(R.id.savingIncome);
            savingsOutcome = (TextView) itemView.findViewById(R.id.savingOutcome);
            savingsResult = (TextView) itemView.findViewById(R.id.savingsResult);
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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SavingsModelClass element = list.get(position);
        holder.savingsTitlePeriod.setText(element.getTvTitlePeriod());
        holder.savingsIncome.setText(element.getTvIncome());
        holder.savingsOutcome.setText(element.getTvOutcome());
        holder.savingsResult.setText(element.getTvResult());
        holder.savingsResult.setTextColor(Color.parseColor(element.getTvResultColor()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
