
package github.julianNSH.moneymanager;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import github.julianNSH.moneymanager.add.AddFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MoneyManager);//change theme after splash screen
        setContentView(R.layout.activity_main);

        //Configure add button from bottom app bar
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_add);
        fab.setOnClickListener(view -> {
            DialogFragment addDialog = new AddFragment();
            addDialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);
            addDialog.show(getSupportFragmentManager(), "dialog");
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_overview, R.id.navigation_statistics, R.id.navigation_scope,
                R.id.navigation_savings, R.id.floating_add)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

}