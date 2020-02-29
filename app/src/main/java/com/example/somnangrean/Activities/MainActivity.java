package com.example.somnangrean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.somnangrean.Controllers.UserStateController;
import com.example.somnangrean.Fragments.Categories;
import com.example.somnangrean.Fragments.Challenges;
import com.example.somnangrean.Fragments.Home;
import com.example.somnangrean.Fragments.RecentQuestions;
import com.example.somnangrean.Models.User.ActivityUser;
import com.example.somnangrean.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    // fragment id variable
    private int fragmentId;
    private String tag="";

    //setting up the slide menu
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    //state manager
    private UserStateController userStateController;


    //close app if not in home fragment
    private boolean atHome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        initializeDrawerLayout();
        navigationClick();
        loadHomeFragment();
        loadHeaderInfo();

    }

    private void initializeUI(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * InitializeDrawerToggle
     * To initialize all the elements of the slide menu
     * takes no parameters
     */
    private void initializeDrawerLayout(){
        navigationView = findViewById(R.id.nav_view);
        mDrawer = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        inflateMenu();

    }

    /**
     * Setting up the navigation item click listener
     */
    private void navigationClick(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){ // getting the id of the menu selected
                    case R.id.home:
                        fragmentId = 0;
                        loadFragment(fragmentId);
                        item.setChecked(true);
                        break;
                    case R.id.recent:
                        fragmentId =1;
                        loadFragment(fragmentId);
                        item.setChecked(true);
                        break;
                    case R.id.categories:
                        fragmentId = 2;
                        loadFragment(fragmentId);
                        item.setChecked(true);
                        break;
                    case R.id.challenges:
                        fragmentId = 3;
                        loadFragment(fragmentId);
                        item.setChecked(true);
                        break;
                    case R.id.logout:
                        alertLogout();
                        // load settings fragment
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Selecting the right fragment, and loading it in the fram layout
     */
    private void loadFragment(int fragmentID){
        String tags[] = getResources().getStringArray(R.array.fragmentTags);
        Fragment fragment = null;
        
        switch (fragmentID){
            case 0:
                fragment = new Home();
                atHome = true;
                tag = tags[0];
                break;
            case 1:
                fragment = new RecentQuestions();
                atHome = false;
                tag = tags[1];
                break;
            case 2:
                fragment = new Categories();
                atHome = false;
                tag = tags[2];
                break;
            case 3:
                fragment = new Challenges();
                atHome = false;
                tag = tags[3];
                break;
        }
        // loading the fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.main_fragment, fragment, tag);
        transaction.commitAllowingStateLoss();
        getSupportActionBar().setTitle(tag);
        mDrawer.closeDrawer(GravityCompat.START);
    }

    private void loadHomeFragment(){
        atHome = true;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.main_fragment, new Home(), "Home");
        getSupportActionBar().setTitle("Home");
        transaction.commit();
        navigationView.getMenu().getItem(0).setChecked(true);

    }
    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        }else{
            if (atHome){
                Log.e("Home", "Application should close");
                super.onBackPressed();
            }else{
                loadHomeFragment();
            }
        }

    }

    private void inflateMenu(){
        new UserStateController();
        userStateController = UserStateController.userStateController;

        if (userStateController.getState()== UserStateController.userState.loggedin){
            navigationView.inflateMenu(R.menu.nav_menu);
        }else{
            navigationView.inflateMenu(R.menu.logged_out_menu);
        }
    }
    private void alertLogout(){
        mDrawer.closeDrawer(GravityCompat.START);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout?");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Logout", (dialog, which) -> {
            logOut();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void logOut(){
        UserStateController.userStateController.setState(UserStateController.userState.loggedout);
        UserStateController.activeUser = new ActivityUser("", "",0,0,"Anonymous","");
        finish();
        startActivity(getIntent());
    }
    private void loadHeaderInfo(){
        // user details
        if (userStateController.getState()==UserStateController.userState.loggedin){
            View v = navigationView.getHeaderView(0);
            TextView profileEmail = v.findViewById(R.id.userName);
            TextView profileRating = v.findViewById(R.id.rating);
            ImageView profilePic = v.findViewById(R.id.profilePic);

            profilePic.setOnClickListener(v1->{
                // open profile activity
                startActivity(new Intent().setClass(this, UserProfile.class));
            });
            profileEmail.setText(UserStateController.activeUser.getUserName());
            profileRating.setText(String.valueOf(UserStateController.activeUser.getRating()));

        }
        else{
            View v = navigationView.getHeaderView(0);
            TextView login = v.findViewById(R.id.userName);
            login.setOnClickListener(v1 -> {
                startActivity(new Intent().setClass(this, Login.class));
            });
        }

    }

}
