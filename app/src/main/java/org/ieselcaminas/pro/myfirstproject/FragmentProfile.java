package org.ieselcaminas.pro.myfirstproject;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

        editTextUsername.setEnabled(false);
        editTextAge.setEnabled(false);
        editTextCountry.setEnabled(false);
        editTextCity.setEnabled(false);
        editTextAddress.setEnabled(false);


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
        editTextUsername.setEnabled(true);
        editTextAge.setBackgroundDrawable(originalEditText);
        editTextAge.setEnabled(true);
        editTextCountry.setBackgroundDrawable(originalEditText);
        editTextCountry.setEnabled(true);
        editTextCity.setBackgroundDrawable(originalEditText);
        editTextCity.setEnabled(true);
        editTextAddress.setBackgroundDrawable(originalEditText);
        editTextAddress.setEnabled(true);

        buttonEditSave.setText(getString(R.string.save));
    }

    private void setNotEditable() {
        editing = false;
        editTextUsername.setBackgroundColor(Color.TRANSPARENT);
        editTextUsername.setEnabled(false);
        editTextAge.setBackgroundColor(Color.TRANSPARENT);
        editTextAge.setEnabled(false);
        editTextAge.setTextColor(Color.GRAY);
        editTextCountry.setBackgroundColor(Color.TRANSPARENT);
        editTextCountry.setEnabled(false);
        editTextCountry.setTextColor(Color.GRAY);
        editTextCity.setBackgroundColor(Color.TRANSPARENT);
        editTextCity.setEnabled(false);
        editTextCity.setTextColor(Color.GRAY);
        editTextAddress.setBackgroundColor(Color.TRANSPARENT);
        editTextAddress.setEnabled(false);
        editTextAddress.setTextColor(Color.GRAY);

        buttonEditSave.setText(getString(R.string.edit));
    }


}


