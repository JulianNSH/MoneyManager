package github.julianNSH.moneymanager.overview.SortData;

import java.util.Comparator;

import github.julianNSH.moneymanager.overview.OverviewModelClass;

public class SortByTitleAsc implements Comparator<OverviewModelClass> {

    public int compare(OverviewModelClass obj1, OverviewModelClass obj2){
        return -obj1.tvDomain.compareTo(obj2.tvDomain);
    }
}
