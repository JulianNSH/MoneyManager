package github.julianNSH.moneymanager.ui.scope;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScopeViewModel extends ViewModel {

    private MutableLiveData<String> sText;

    public ScopeViewModel(){
        sText = new MutableLiveData<>();
        sText.setValue("Scopurile puse");
    }
    public LiveData<String> getText(){return sText;}
}
