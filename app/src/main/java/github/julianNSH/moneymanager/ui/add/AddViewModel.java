package github.julianNSH.moneymanager.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {

    private MutableLiveData<String> aText;

    public AddViewModel(){
        aText = new MutableLiveData<>();
        aText.setValue("Adaugare de continut");
    }
    public LiveData<String> getText(){return aText;}
}
