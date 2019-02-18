package org.ieselcaminas.pro.myfirstproject;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    Button buttonMenuOrders;
    Button buttonMainMenu2;
    Button buttonMenuPlans;
    ConstraintLayout constraintLayoutMainMenu;
    ConstraintLayout constraintLayoutProfileMenu;
    Button buttonProfile;
    Button buttonProfileMenu2;
    ImageButton buttonLogOut;
    boolean mainMenuDown;
    boolean profileMenuDown;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_search);

        buttonMenuOrders = findViewById(R.id.buttonMenuOrders);
        buttonMenuPlans = findViewById(R.id.buttonMenuPlans);
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        constraintLayoutMainMenu = findViewById(R.id.ConstraintLayoutMenu);
        constraintLayoutProfileMenu = findViewById(R.id.ConstraintLayoutProfileMenu);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Singleton.sharedInstance().setAuthenticated(false);
        mainMenuDown = false;
        profileMenuDown = false;


        //opcion de busqueda
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        buttonMenuOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentOrders();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                upAllMenus();
            }
        });

        buttonMenuPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentPlans();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                upAllMenus();
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentProfile();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                upAllMenus();
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.sharedInstance().getmAuth().signOut();
                Intent intent = getIntent();
                finish();
                startActivity(intent);

                onStart();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = Singleton.sharedInstance().getmAuth().getCurrentUser();

        if (currentUser != null) {
            Singleton.sharedInstance().setAuthenticated(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:

                if (mainMenuDown) {
                    upAnimation(constraintLayoutMainMenu, toolbar.getHeight(), 0, 1.0f, 0.0f);
                    mainMenuDown = false;
                } else {
                    downAnimation(constraintLayoutMainMenu, 0, toolbar.getHeight(), 0.0f, 1.0f);
                    mainMenuDown = true;
                }

                if (profileMenuDown) {
                    upAnimation(constraintLayoutProfileMenu, toolbar.getHeight(), 0, 1.0f, 0.0f);
                    profileMenuDown = false;
                }

                return true;
            case R.id.action_user:
                if (!Singleton.sharedInstance().isAuthenticated()) {
                    Intent i = new Intent(getApplicationContext(), AuthenticationActivity.class);
                    startActivity(i);
                } else {
                    if (profileMenuDown) {
                        upAnimation(constraintLayoutProfileMenu, toolbar.getHeight(), 0, 1.0f, 0.0f);
                        profileMenuDown = false;
                    } else {
                        downAnimation(constraintLayoutProfileMenu, 0, toolbar.getHeight(), 0.0f, 1.0f);
                        profileMenuDown = true;
                    }

                    if (mainMenuDown) {
                        upAnimation(constraintLayoutMainMenu, toolbar.getHeight(), 0, 1.0f, 0.0f);
                        mainMenuDown = false;
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void downAnimation(ConstraintLayout constraint, int i2, int i3, float v, float v2) {
        constraint.animate().translationY(i2).setDuration(500).start();
        constraint.animate().translationY(i3).start();
        constraint.animate().alpha(v).setDuration(500).start();
        constraint.animate().alpha(v2).start();
    }

    private void upAnimation(ConstraintLayout constraint, int i2, int i3, float v, float v2) {
        downAnimation(constraint, i2, i3, v, v2);
    }


    private void upAllMenus() {
        if (mainMenuDown) {
            upAnimation(constraintLayoutMainMenu, toolbar.getHeight(), 0, 1.0f, 0.0f);
            mainMenuDown = false;
        } else if (profileMenuDown) {
            upAnimation(constraintLayoutProfileMenu, toolbar.getHeight(), 0, 1.0f, 0.0f);
            profileMenuDown = false;
        }
    }

}
