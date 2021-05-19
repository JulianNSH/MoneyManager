package github.julianNSH.moneymanager.overview;

import java.util.Comparator;

public class SortByTitleDesc implements Comparator<OverviewModelClass> {

    public int compare(OverviewModelClass obj1, OverviewModelClass obj2){
        return obj1.tvDomain.compareTo(obj2.tvDomain);
    }
}