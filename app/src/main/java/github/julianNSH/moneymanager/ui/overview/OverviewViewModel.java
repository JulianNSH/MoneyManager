package github.julianNSH.moneymanager.ui.overview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OverviewViewModel extends ViewModel {

    private MutableLiveData<String> oText;

    public OverviewViewModel(){
        oText = new MutableLiveData<>();
        oText.setValue("Prezentarea generala a datelor");
    }
    public LiveData<String> getText(){
        return oText;
    }
}
