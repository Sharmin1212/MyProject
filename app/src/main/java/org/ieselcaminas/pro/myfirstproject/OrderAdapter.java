package org.ieselcaminas.pro.myfirstproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {

    Context context;
    ArrayList<OrderItem> list;
    DatabaseReference reference;

    public OrderAdapter(Context c, ArrayList<OrderItem> l) {
        context = c;
        list = l;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final OrderItem myList = list.get(position);
        holder.title.setText(myList.getTitle());
        holder.user.setText(myList.getUser());
        holder.descr.setText(myList.getDescr());
//        holder.img.setImageResource(myList.getImage());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                reference = FirebaseDatabase.getInstance().getReference().child("orders");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            OrderItem p = dataSnapshot1.getValue(OrderItem.class);
                            if (p.getOwner().equals(myList.getOwner()) && p.getTitle().equals(myList.getTitle())) {
                                dataSnapshot1.child("accepted").getRef().setValue(Singleton.sharedInstance().getmAuth().getUid());
                                Toast.makeText(v.getContext(), "Order accepted", Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(v.getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        /*int arr = 0;
        try {
            if (list.size() == 0) {
                arr = 0;
            } else {
                arr = list.size();
            }
        } catch (Exception e) {
        }*/
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView title, user, descr;
        ImageView img;
        Button btn;


        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewOrderTitle);
            user = itemView.findViewById(R.id.textViewUser);
            descr = itemView.findViewById(R.id.textViewOrderDescr);
            img = itemView.findViewById(R.id.imageViewProduct);
            btn = itemView.findViewById(R.id.buttonAcceptOrder);
        }
    }

}