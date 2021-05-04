package github.julianNSH.moneymanager.ui.scope;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.infideap.stylishwidget.view.AProgressBar;

import java.util.List;

import github.julianNSH.moneymanager.R;

public class ScopeAdapter extends RecyclerView.Adapter<ScopeAdapter.MyViewHolder> {
    ScopeFragment context;

    private List<ScopeModelClass> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView scopeTitle, value_of_progress;
        AProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            scopeTitle = (TextView) itemView.findViewById(R.id.scopeTitle);
            value_of_progress = (TextView) itemView.findViewById(R.id.value_of_progress);
            progressBar = (AProgressBar) itemView.findViewById(R.id.pb_scope);
        }
    }

    public ScopeAdapter(ScopeFragment context, List<ScopeModelClass> list ){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scope_element, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScopeModelClass element = list.get(position);
        holder.scopeTitle.setText(element.getTvTitle());
        holder.value_of_progress.setText(element.getTvValues());
        holder.progressBar.setProgressValue(element.getPbProgressValue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}