package ie.dcu.computing.student.lenehan4.mystoremanager;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ie.dcu.computing.student.lenehan4.mystoremanager.EOTM.EmployeeEotm;
import ie.dcu.computing.student.lenehan4.mystoremanager.Rota.EmployeeRota;


public class EmployeeProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployProfile()).commit();
            navigationView.setCheckedItem(R.id.nav_employeeprofile);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_employeeprofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployProfile()).commit();
                break;

            case R.id.nav_employeerota:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployeeRota()).commit();
                break;


            case R.id.nav_employeestock:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployeeStock()).commit();
                break;

            case R.id.nav_employeeeotm:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployeeEotm()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
