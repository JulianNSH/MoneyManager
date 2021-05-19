package github.julianNSH.moneymanager.overview.SortData;

import java.util.Comparator;

import github.julianNSH.moneymanager.overview.OverviewModelClass;


public class SortByAmountAsc implements Comparator<OverviewModelClass> {

    public int compare(OverviewModelClass obj1, OverviewModelClass obj2){
        float comp = ((OverviewModelClass)obj2).getTvAmount();
        return -(int) (comp-obj1.tvAmount);

    }
}

