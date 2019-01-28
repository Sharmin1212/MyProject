package org.ieselcaminas.pro.myfirstproject;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentOrders extends Fragment {

    FloatingActionButton fab;
    Button buttonDiaSubmit;
    Button buttonDiaCancel;
    EditText editTextTitle;
    EditText editTextDescription;

    public FragmentOrders() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View thisView = inflater.inflate(R.layout.fragment_orders, container, false);

        fab = thisView.findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog dialogBuilder = new AlertDialog.Builder(thisView.getContext()).create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_add_order, null);

                editTextTitle = dialogView.findViewById(R.id.editTextTitle);
                editTextDescription = dialogView.findViewById(R.id.editTextDescr);
                buttonDiaSubmit = dialogView.findViewById(R.id.buttonSubmit);
                buttonDiaCancel = dialogView.findViewById(R.id.buttonCancel);

                buttonDiaSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editTextTitle.length() == 0 || editTextDescription.length() == 0) {
                            Toast.makeText(thisView.getContext(), "Please put some information", Toast.LENGTH_SHORT).show();
                        } else {

                            //Write to my database
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("message");

                            myRef.setValue("Hello, World!");


                            dialogBuilder.dismiss();
                        }

                    }
                });
                buttonDiaCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();


            }
        });
        return thisView;
    }
}
