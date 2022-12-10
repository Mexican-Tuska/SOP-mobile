package mobile.system;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        // Сейчас сделано так: https://www.youtube.com/watch?v=wzu380RouwI
        // Но можно сделать и так: https://www.youtube.com/watch?v=OV25x3a55pk&t=348s

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_sop,
                R.id.navigation_schedule,
                R.id.navigation_home,
                R.id.navigation_attendance,
                R.id.navigation_profile)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}