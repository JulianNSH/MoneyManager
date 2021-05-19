package github.julianNSH.moneymanager.statistics.SortData;

import java.util.Comparator;

import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class SortByTitleDesc implements Comparator<StatisticsModelClass> {

    public int compare(StatisticsModelClass obj1, StatisticsModelClass obj2){
        return -obj1.tvType.compareTo(obj2.tvType);
    }
}