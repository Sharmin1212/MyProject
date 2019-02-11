package org.ieselcaminas.pro.myfirstproject;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FragmentProfile extends Fragment {
    EditText editTextUsername;
    Button buttonEditSave;
    boolean editing;


    public FragmentProfile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View thisView = inflater.inflate(R.layout.fragment_profile, container, false);

        editing = false;
        buttonEditSave = thisView.findViewById(R.id.buttonEditSave);


        editTextUsername = thisView.findViewById(R.id.editTextUsername);
        editTextUsername.setText(Singleton.sharedInstance().getmAuth().getCurrentUser().getDisplayName());

        final Drawable originalEditText = editTextUsername.getBackground();
        editTextUsername.setBackgroundColor(Color.TRANSPARENT);


        buttonEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editing) {
                    editing = true;
                    editTextUsername.setBackgroundDrawable(originalEditText);
                    editTextUsername.setFocusable(true);


                    buttonEditSave.setText(getString(R.string.save));
                } else {
                    editing = false;
                    editTextUsername.setBackgroundColor(Color.TRANSPARENT);
                    editTextUsername.setFocusable(false);



                    buttonEditSave.setText(getString(R.string.edit));

                }

            }
        });


        return thisView;
    }


}


