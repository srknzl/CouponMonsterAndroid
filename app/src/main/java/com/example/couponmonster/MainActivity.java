package com.example.couponmonster;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        TextView connectionStatus = findViewById(R.id.connectionStatus);
        if(AppState.getInstance().connected)connectionStatus.setText("Connected");
        else connectionStatus.setText("Not connected");
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                TextView nameView = drawerView.findViewById(R.id.nav_name);
                TextView usernameView = drawerView.findViewById(R.id.nav_username);
                TextView scoreView = drawerView.findViewById(R.id.nav_score);
                nameView.setText("Name: " + AppState.getInstance().user.name);
                usernameView.setText("Username: " + AppState.getInstance().user.username);
                scoreView.setText("Score: " + AppState.getInstance().user.score);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_online,
                R.id.nav_options)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if(!AppState.getInstance().connected)connect();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void connect(){
        AppState appState = AppState.getInstance();

        if(appState.listener != null)appState.listener.close();
        if(appState.listenerThread != null)appState.listenerThread.interrupt();
        appState.listenerThread = null;
        appState.listener = null;
        ((TextView)this.findViewById(R.id.connectionStatus)).setText(R.string.appbar_notconnected);

        Listener listener = new Listener(this);
        appState.listener = listener;
        Log.e("Not null",listener.toString());
        Thread listenerThread = new Thread(listener);
        appState.listenerThread = listenerThread;
        listenerThread.start();
    }

}
