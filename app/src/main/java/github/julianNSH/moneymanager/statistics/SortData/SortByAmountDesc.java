package github.julianNSH.moneymanager.statistics.SortData;

import java.util.Comparator;

import github.julianNSH.moneymanager.overview.OverviewModelClass;
import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class SortByAmountDesc  implements Comparator<StatisticsModelClass> {

public int compare(StatisticsModelClass obj1, StatisticsModelClass obj2){
        float comp = ((StatisticsModelClass)obj2).getTvAmount();
        return -(int) (comp-obj1.tvAmount);

        }
}
