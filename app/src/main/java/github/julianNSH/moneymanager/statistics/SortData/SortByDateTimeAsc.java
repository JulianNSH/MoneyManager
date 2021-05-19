package github.julianNSH.moneymanager.statistics.SortData;

import java.util.Comparator;

import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class SortByDateTimeAsc implements Comparator<StatisticsModelClass> {

    public int compare(StatisticsModelClass obj1, StatisticsModelClass obj2){

            int i = obj1.date.compareTo(obj2.date);
            if (i!=0) return i;
            return obj1.time.compareTo(obj2.time);

    }
}
