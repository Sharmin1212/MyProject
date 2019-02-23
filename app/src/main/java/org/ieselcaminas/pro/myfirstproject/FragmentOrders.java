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


        recView = thisView.findViewById(R.id.recView);


        recView.setLayoutManager(new LinearLayoutManager(thisView.getContext()));


        reference = FirebaseDatabase.getInstance().getReference().child("orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    OrderItem p = dataSnapshot1.getValue(OrderItem.class);
                    if (!Singleton.sharedInstance().isAuthenticated()) {
                        list.add(p);
                    } else {
                        if (!p.getOwner().equals(Singleton.sharedInstance().getmAuth().getUid()) && p.getAccepted().equals("no")) {
                            list.add(p);
                        }
                    }

                }
                adapter = new OrderAdapter(thisView.getContext(), list);
                recView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(thisView.getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        return thisView;
    }
}
