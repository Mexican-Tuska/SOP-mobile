package mobile.system;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import mobile.system.databinding.FragmentBottomNavigationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomNavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomNavigationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BottomNavigationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomNavigationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomNavigationFragment newInstance(String param1, String param2) {
        BottomNavigationFragment fragment = new BottomNavigationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    FragmentBottomNavigationBinding binding;

    private class Listener implements NavigationBarView.OnItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.navigation_sop:
                    replaceFragment(new SopFragment());
                    break;
                case R.id.navigation_attendance:
                    replaceFragment(new AttendanceFragment());
                    break;
                case R.id.navigation_schedule:
                    replaceFragment(new ScheduleFragment());
                    break;
                case R.id.navigation_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return false;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        replaceFragment(new HomeFragment()); // Works

        // Not Works
        binding = FragmentBottomNavigationBinding.inflate(getLayoutInflater());

        binding.bottomNavView.setOnItemSelectedListener(new Listener());


        // Valitsya parasha:
        /*BottomNavigationView nav = getView().findViewById(R.id.bottom_nav_view);
        nav.setOnItemSelectedListener(new Listener());*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_navigation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navView = view.findViewById(R.id.bottom_nav_view);
        Log.d("NAV_VIEW", navView == null ? "NULL" : navView.toString());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_sop,
                R.id.navigation_schedule,
                R.id.navigation_attendance,
                R.id.navigation_home,
                R.id.navigation_profile)
                .build();

//        NavController navController = findNavController(root);

        AppCompatActivity context = (AppCompatActivity) getActivity();
        Log.d("NAV_VIEW", context == null ? "NULL" : context.toString());

//        assert context != null;
        NavController navController = NavHostFragment.findNavController(this);


//        Log.d("NAV_VIEW", navController == null ? "NULL" : navController.toString());


        NavigationUI.setupActionBarWithNavController(context, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}