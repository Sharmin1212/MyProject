package org.ieselcaminas.pro.myfirstproject;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;

    ConstraintLayout constraintLayoutMainMenu;
    ConstraintLayout constraintLayoutProfileMenu;

    Button buttonMenuOrders;
    Button buttonMenuPlans;
    Button buttonProfile;
    Button buttonProfileMyOrders;
    Button buttonProfileAcceptedOrders;

    TextView buttonOrders;
    TextView buttonPlans;
    TextView buttonAuthenticate;

    ImageButton buttonLogOut;
    ImageView imageViewLogo;
    boolean mainMenuDown;
    boolean profileMenuDown;
    android.support.v7.widget.Toolbar toolbar;
    TextView textViewSlogan;

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
        buttonProfileMyOrders = findViewById(R.id.buttonProfileMyOrders);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonProfileAcceptedOrders = findViewById(R.id.buttonProfileAcceptedOrders);

        imageViewLogo = findViewById(R.id.imageViewLogo);
        constraintLayoutMainMenu = findViewById(R.id.ConstraintLayoutMenu);
        constraintLayoutProfileMenu = findViewById(R.id.ConstraintLayoutProfileMenu);
        textViewSlogan = findViewById(R.id.textViewSlogan);
        buttonOrders = findViewById(R.id.buttonOrders);
        buttonPlans = findViewById(R.id.buttonPlans);
        buttonAuthenticate = findViewById(R.id.buttonAuthenticate);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Wellsight.ttf");

        textViewSlogan.setTypeface(custom_font);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Singleton.sharedInstance().setAuthenticated(false);
        mainMenuDown = false;
        profileMenuDown = false;

        imageViewLogo.setAlpha(0f);
        imageViewLogo.setFocusable(false);


        //opcion de busqueda
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        imageViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).addToBackStack(null).commit();
                }

                hideLogo();
            }
        });

        buttonMenuOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOrders();
            }
        });

        buttonOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOrders();
            }
        });

        buttonMenuPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPlans();
            }
        });

        buttonPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPlans();
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });

        buttonProfileMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentMyOrders();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                upAllMenus();
                showLogo();
            }
        });

        buttonProfileAcceptedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentAcceptedOrders();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                upAllMenus();
                showLogo();
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

        buttonAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Singleton.sharedInstance().isAuthenticated()) {
                    goToProfile();
                } else {
                    goToAuthentication();
                }
            }
        });
    }

    private void goToProfile() {
        fragment = new FragmentProfile();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        upAllMenus();
        showLogo();
    }

    private void goToPlans() {
        fragment = new FragmentPlans();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        upAllMenus();
        showLogo();
    }

    private void goToOrders() {
        fragment = new FragmentOrders();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        upAllMenus();
        showLogo();
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = Singleton.sharedInstance().getmAuth().getCurrentUser();

        if (currentUser != null) {
            Singleton.sharedInstance().setAuthenticated(true);
            buttonAuthenticate.setText(getString(R.string.profile));
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
                    goToAuthentication();
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

    private void goToAuthentication() {
        Intent i = new Intent(getApplicationContext(), AuthenticationActivity.class);
        startActivity(i);
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

    public void hideLogo() {
        imageViewLogo.animate().alpha(1.0f).setDuration(500).start();
        imageViewLogo.animate().alpha(0).start();
        imageViewLogo.setFocusable(false);
    }

    public void showLogo() {
        imageViewLogo.animate().alpha(0).setDuration(500).start();
        imageViewLogo.animate().alpha(1.0f).start();
        imageViewLogo.setFocusable(true);

    }

}
