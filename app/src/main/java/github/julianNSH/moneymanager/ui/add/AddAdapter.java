package github.julianNSH.moneymanager.ui.add;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AddAdapter extends FragmentPagerAdapter {
    private View context;
    int tabs;
    public AddAdapter(View context, FragmentManager fm, int tabs){
        super(fm);
        this.context = context;
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AddIncomeFragment incomeFragment = new AddIncomeFragment();
                return incomeFragment;
            case 1:
                AddOutgoingFragment outgoingFragment = new AddOutgoingFragment();
                return outgoingFragment;
            case 2:
                AddOtherFragment otherFragment = new AddOtherFragment();
                return otherFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
