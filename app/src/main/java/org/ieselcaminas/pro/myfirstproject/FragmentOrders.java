package org.ieselcaminas.pro.myfirstproject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentOrders extends Fragment {

    FloatingActionButton fab;
    Button buttonDiaSubmit;
    Button buttonDiaCancel;
    EditText editTextTitle;
    EditText editTextDescription;
    ImageView imageViewAddProduct;
    OrderAdapter adapter;

    RecyclerView recView;
    ArrayList<OrderItem> list;
    DatabaseReference reference;

    public FragmentOrders() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View thisView = inflater.inflate(R.layout.fragment_orders, container, false);

        fab = thisView.findViewById(R.id.fab);
        recView = thisView.findViewById(R.id.recView);


        recView.setLayoutManager(new LinearLayoutManager(thisView.getContext()));


        reference = FirebaseDatabase.getInstance().getReference().child("orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    OrderItem p = dataSnapshot1.getValue(OrderItem.class);
                    list.add(p);
                }
                adapter = new OrderAdapter(thisView.getContext(), list);
                recView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(thisView.getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


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
                imageViewAddProduct = dialogView.findViewById(R.id.imageViewAddProduct);


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("orders");

                /*database.getReference("orders").orderByChild("owner_id").equalTo(Singleton.sharedInstance().getmAuth().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        OrderItem p = dataSnapshot.getValue(OrderItem.class);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

                buttonDiaSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editTextTitle.length() == 0 || editTextDescription.length() == 0) {
                            Toast.makeText(thisView.getContext(), getString(R.string.putInformation), Toast.LENGTH_SHORT).show();
                        } else if (!Singleton.sharedInstance().isAuthenticated()) {
                            Toast.makeText(thisView.getContext(), getString(R.string.logInForOrder), Toast.LENGTH_SHORT).show();
                        } else {

                            //Write to my database
                            OrderItem o = new OrderItem(imageViewAddProduct.getId(), editTextTitle.getText().toString(), Objects.requireNonNull(Singleton.sharedInstance().getmAuth().getCurrentUser()).getEmail(), editTextDescription.getText().toString());
                            String clau = myRef.push().getKey();
                            myRef.child("item" + clau).setValue(o);


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
