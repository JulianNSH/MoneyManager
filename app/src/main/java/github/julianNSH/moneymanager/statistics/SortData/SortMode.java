package github.julianNSH.moneymanager.statistics.SortData;

import java.util.Comparator;
import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class SortMode implements Comparator<StatisticsModelClass> {
    //value used to determine which method to use for sorting
    byte option = 0;

    public SortMode(byte option){
        this.option = option;
    }

    @Override
    public int compare(StatisticsModelClass obj1, StatisticsModelClass obj2) {
        //sorting by amount in descended mode
        if(option==0){
            float comp = ((StatisticsModelClass)obj2).getTvAmount();
            return (int) (comp-obj1.tvAmount);
        }

        //sorting by amount in ascended mode
        if(option==1){
            float comp = ((StatisticsModelClass)obj2).getTvAmount();
            return -(int) (comp-obj1.tvAmount);
        }

        //sorting by date and time in ascended mode
        if(option==2){
            int i = obj1.date.compareTo(obj2.date);
            if (i!=0) return i;
            return obj1.time.compareTo(obj2.time);
        }

        //sorting by date and time in descended mode
        if(option==3){
            int i = -obj1.date.compareTo(obj2.date);
            if (i!=0) return i;
            return -obj1.time.compareTo(obj2.time);
        }

        //sorting by date and time in ascended mode
        if(option==4) return -obj1.tvType.compareTo(obj2.tvType);

        //sorting by date and time in descended mode
        if(option==5) return obj1.tvType.compareTo(obj2.tvType);

        return 0;
    }
}
