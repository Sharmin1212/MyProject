package org.ieselcaminas.pro.myfirstproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentAcceptedOrders extends Fragment {


    AdapterAcceptedOrder adapter;

    RecyclerView recView;
    ArrayList<OrderItem> acceptedList;
    DatabaseReference reference;

    public FragmentAcceptedOrders() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View thisView = inflater.inflate(R.layout.fragment_accepted_orders, container, false);

        recView = thisView.findViewById(R.id.recViewAcceptedOrders);


        recView.setLayoutManager(new LinearLayoutManager(thisView.getContext()));


        reference = FirebaseDatabase.getInstance().getReference().child("orders");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                acceptedList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    OrderItem p = dataSnapshot1.getValue(OrderItem.class);
                    //assert p != null;
                    if (p.getAccepted().equals(Singleton.sharedInstance().getmAuth().getUid())) {
                        acceptedList.add(p);
                    }
                }
                adapter = new AdapterAcceptedOrder(thisView.getContext(), acceptedList);
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
