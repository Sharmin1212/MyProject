package org.ieselcaminas.pro.myfirstproject;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class FragmentProfile extends Fragment {
    EditText editTextUsername;
    EditText editTextAge;
    EditText editTextCountry;
    EditText editTextCity;
    EditText editTextAddress;

    TextView textViewDeliveryRating;
    TextView textViewConsumerRating;

    ImageView imageView;

    Button buttonEditSave;
    boolean editing;

    DatabaseReference reference;


    public FragmentProfile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View thisView = inflater.inflate(R.layout.fragment_profile, container, false);

        editing = false;
        buttonEditSave = thisView.findViewById(R.id.buttonEditSave);


        editTextUsername = thisView.findViewById(R.id.editTextUsername);
        editTextAge = thisView.findViewById(R.id.editTextAge);
        editTextCountry = thisView.findViewById(R.id.editTextCountry);
        editTextCity = thisView.findViewById(R.id.editTextCity);
        editTextAddress = thisView.findViewById(R.id.editTextAddress);

        textViewDeliveryRating = thisView.findViewById(R.id.textViewDeliveryRating2);
        textViewConsumerRating = thisView.findViewById(R.id.textViewConsumerRating2);


        imageView = thisView.findViewById(R.id.imageView);


        reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = new User();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals(Singleton.sharedInstance().getmAuth().getCurrentUser().getUid())) {
                        u = dataSnapshot1.getValue(User.class);
                    }

                }

                editTextUsername.setText(u.name);
                editTextAge.setText(String.valueOf(u.age));
                editTextCountry.setText(u.country);
                editTextCity.setText(u.city);
                editTextAddress.setText(u.address);

                textViewDeliveryRating.setText(String.valueOf(u.deliveryRating));
                textViewConsumerRating.setText(String.valueOf(u.consumerRating));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(thisView.getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        final Drawable originalEditText = editTextUsername.getBackground();
        editTextUsername.setBackgroundColor(Color.TRANSPARENT);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");


        buttonEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editing) {
                    setEditable(originalEditText);
                } else {
                    if (editTextUsername.length() == 0 || editTextAge.length() == 0 || editTextCountry.length() == 0 || editTextCity.length() == 0 || editTextAddress.length() == 0) {
                        Toast.makeText(thisView.getContext(), getString(R.string.putInformation), Toast.LENGTH_SHORT).show();
                    } else {
                        setNotEditable();
                        //Write to my database
                        User user = new User(editTextUsername.getText().toString(),
                                Singleton.sharedInstance().getmAuth().getCurrentUser().getEmail(),
                                editTextCountry.getText().toString(),
                                editTextCity.getText().toString(),
                                editTextAddress.getText().toString(),
                                Integer.parseInt(editTextAge.getText().toString()),
                                1212,
                                1212,
                                imageView.getId());
                        myRef.child(Singleton.sharedInstance().getmAuth().getUid()).setValue(user);


                    }

                }

            }
        });


        return thisView;
    }

    private void setEditable(Drawable originalEditText) {
        editing = true;

        editTextUsername.setBackgroundDrawable(originalEditText);
        editTextUsername.setFocusable(true);
        editTextAge.setBackgroundDrawable(originalEditText);
        editTextAge.setFocusable(true);
        editTextCountry.setBackgroundDrawable(originalEditText);
        editTextCountry.setFocusable(true);
        editTextCity.setBackgroundDrawable(originalEditText);
        editTextCity.setFocusable(true);
        editTextAddress.setBackgroundDrawable(originalEditText);
        editTextAddress.setFocusable(true);

        buttonEditSave.setText(getString(R.string.save));
    }

    private void setNotEditable() {
        editing = false;
        editTextUsername.setBackgroundColor(Color.TRANSPARENT);
        editTextUsername.setFocusable(false);
        editTextAge.setBackgroundColor(Color.TRANSPARENT);
        editTextAge.setFocusable(false);
        editTextCountry.setBackgroundColor(Color.TRANSPARENT);
        editTextCountry.setFocusable(false);
        editTextCity.setBackgroundColor(Color.TRANSPARENT);
        editTextCity.setFocusable(false);
        editTextAddress.setBackgroundColor(Color.TRANSPARENT);
        editTextAddress.setFocusable(false);

        buttonEditSave.setText(getString(R.string.edit));
    }


}


