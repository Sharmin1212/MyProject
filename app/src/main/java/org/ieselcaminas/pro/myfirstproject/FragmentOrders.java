package org.ieselcaminas.pro.myfirstproject;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentOrders extends Fragment {

    FloatingActionButton fab;
    Button buttonDiaSubmit;
    Button buttonDiaCancel;
    EditText editTextTitle;
    EditText editTextDescription;
    ImageView imageViewAddProduct;
    private List<OrderItem> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    RecyclerView recView;

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
                imageViewAddProduct = dialogView.findViewById(R.id.imageViewAddProduct);

                recView = thisView.findViewById(R.id.recView);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("orders");

                buttonDiaSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editTextTitle.length() == 0 || editTextDescription.length() == 0) {
                            Toast.makeText(thisView.getContext(), "Please put some information", Toast.LENGTH_SHORT).show();
                        } else {

                            //Write to my database
                            OrderItem o = new OrderItem(imageViewAddProduct.getId(), editTextTitle.getText().toString(), Singleton.sharedInstance().getmAuth().getCurrentUser().getEmail().toString(), editTextDescription.getText().toString());
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

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderList.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            OrderItem orderItem = dataSnapshot1.getValue(OrderItem.class);
                            orderList.add(orderItem);
                        }

                        orderAdapter = new OrderAdapter(thisView.getContext(), orderList);
                        recView.setAdapter(orderAdapter);
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        return thisView;
    }
}
