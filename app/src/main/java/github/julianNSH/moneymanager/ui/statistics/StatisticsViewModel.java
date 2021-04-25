package github.julianNSH.moneymanager.ui.statistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticsViewModel extends ViewModel {

    private MutableLiveData<String> sText;

    public StatisticsViewModel(){
        sText = new MutableLiveData<>();
        sText.setValue("Statistica cheltuielilor");
    }
    public LiveData<String> getText(){return sText;}
}
