package ie.dcu.computing.student.lenehan4.mystoremanager;

import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ie.dcu.computing.student.lenehan4.mystoremanager.Rota.ManagerRota;

import static ie.dcu.computing.student.lenehan4.mystoremanager.R.string.navigation_drawer_close;
import static ie.dcu.computing.student.lenehan4.mystoremanager.R.string.navigation_drawer_open;

public class ManagerDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_drawer);

        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar1,
                navigation_drawer_open, navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManagerProfile()).commit();
            navigationView.setCheckedItem(R.id.nav_managerprofile);
        }


        sendFCMToDatabase();

    }

    private void sendFCMToDatabase() {


        SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getSharedPreferenceHelper(getApplicationContext());



        Map<String, Object> map = new HashMap<>();
        map.put("fcm", sharedPreferenceHelper.getFCM());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("admin")
                .document("admin_fcm")
                .set(map);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_managerprofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManagerProfile()).commit();
                break;
            case R.id.nav_managerstock:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManagerStock()).commit();
                break;
            case R.id.nav_managerrota:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManagerRota()).commit();
                break;
            case R.id.nav_managereotm:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ManagerEotm()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }



}
