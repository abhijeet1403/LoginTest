package com.example.abhijeet.logintest.NavigationDrawer;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.abhijeet.logintest.Adapters.Customswipeadapter;
import com.example.abhijeet.logintest.ConstructorsandInterfaces.Communicator;
import com.example.abhijeet.logintest.MainStuff.MainFragment;
import com.example.abhijeet.logintest.MainStuff.Photos_Activity;
import com.example.abhijeet.logintest.Permissions;
import com.example.abhijeet.logintest.R;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Communicator {

    TextView mTextUsername;
    public ProfilePictureView profilePictureNVD;
    ViewPager viewPager;
    Customswipeadapter adapter;
    Button clk;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frament_container, new MainFragment()).commit();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);

        setupTextview();
        setuupProfilepic();
        clk = (Button) findViewById(R.id.changeview);

        return true;
    }

    private void setuupProfilepic() {
        profilePictureNVD = (ProfilePictureView) findViewById(R.id.profileimageView);
    }

    private void setupTextview() {
        mTextUsername = (TextView) findViewById(R.id.usernameview);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "abhijeet1abhijeet@gmail.com" });
            intent.putExtra(Intent.EXTRA_TEXT, "Sent From (App)LoginTest");
            startActivity(Intent.createChooser(intent, ""));

        }
        else if (id==R.id.permission){
            Intent mintent = new Intent(this, Permissions.class);
                startActivity(mintent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void respond(String profileid, String name) {
        profilePictureNVD.setProfileId(profileid);
        mTextUsername.setText(name);
    }




}
