package github.julianNSH.moneymanager.overview;

import java.util.Comparator;

public class SortByDateTimeAsc implements Comparator<OverviewModelClass> {

    public int compare(OverviewModelClass obj1, OverviewModelClass obj2){

            int i = obj1.date.compareTo(obj2.date);
            if (i!=0) return i;
            return obj1.time.compareTo(obj2.time);

    }
}
