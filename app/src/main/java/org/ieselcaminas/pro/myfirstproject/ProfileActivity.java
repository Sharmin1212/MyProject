package org.ieselcaminas.pro.myfirstproject;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Boolean authenticated = false;
    boolean mainMenuDown;
    boolean profileMenuDown;
    Button buttonMainMenu1;
    Button buttonMainMenu2;
    Button buttonMainMenu3;
    ConstraintLayout constraintLayoutMainMenu;
    ConstraintLayout constraintLayoutProfileMenu;
    Button buttonProfile;
    Button buttonProfileMenu2;
    ImageButton buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_search);

        buttonMainMenu1 = findViewById(R.id.buttonMenu1);
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        constraintLayoutMainMenu = findViewById(R.id.ConstraintLayoutMenu);
        constraintLayoutProfileMenu = findViewById(R.id.ConstraintLayoutProfileMenu);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mainMenuDown = false;
        profileMenuDown = false;

        //opcion de busqueda
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        buttonMainMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i);
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
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

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            authenticated = true;
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

                    upAnimation(constraintLayoutMainMenu, 145, 0, 1.0f, 0.0f);


                    mainMenuDown = false;
                } else {
                    downAnimation(constraintLayoutMainMenu, 0, 145, 0.0f, 1.0f);
                    mainMenuDown = true;
                }

                return true;
            case R.id.action_user:
                if (!authenticated) {
                    Intent i = new Intent(getApplicationContext(), AuthenticationActivity.class);
                    startActivity(i);
                } else {
                    if (profileMenuDown) {

                        upAnimation(constraintLayoutProfileMenu, 145, 0, 1.0f, 0.0f);


                        profileMenuDown = false;
                    } else {
                        downAnimation(constraintLayoutProfileMenu, 0, 145, 0.0f, 1.0f);
                        profileMenuDown = true;
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


}


