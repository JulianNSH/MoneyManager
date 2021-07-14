package github.julianNSH.moneymanager.add;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AddAdapter extends FragmentPagerAdapter {
    private final View context;
    int tabs;
    public AddAdapter(View context, FragmentManager fm, int tabs){
        super(fm, tabs);
        this.context = context;
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddIncomeFragment();
            case 1:
                return new AddOutgoingFragment();
            case 2:
                return new AddOtherFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
