package github.julianNSH.moneymanager.ui.invest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InvestViewModel extends ViewModel {

    private MutableLiveData<String> iText;

    public InvestViewModel(){
        iText = new MutableLiveData<>();
        iText.setValue("Urmarirea Investitiilor");
    }
    public LiveData<String> getText(){return iText;}
}
