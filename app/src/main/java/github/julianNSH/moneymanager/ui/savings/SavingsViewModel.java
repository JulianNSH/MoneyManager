package github.julianNSH.moneymanager.ui.savings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavingsViewModel extends ViewModel {

    private MutableLiveData<String> iText;

    public SavingsViewModel(){
        iText = new MutableLiveData<>();
        iText.setValue("Urmarirea Economiilor");
    }
    public LiveData<String> getText(){return iText;}
}
