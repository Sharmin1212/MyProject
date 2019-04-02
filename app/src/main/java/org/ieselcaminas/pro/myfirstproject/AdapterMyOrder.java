package org.ieselcaminas.pro.myfirstproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;

public class AdapterMyOrder extends RecyclerView.Adapter<AdapterMyOrder.MyHolder> {

    private Context context;
    private ArrayList<OrderItem> list;
    private DatabaseReference reference;

    public AdapterMyOrder(Context c, ArrayList<OrderItem> l) {
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
        holder.btnCancel.setVisibility(View.VISIBLE);
        holder.btnCancel.setFocusable(true);
        holder.btnCancel.setText(context.getString(R.string.delete));
        holder.btnAccept.setText("Completed");


//        holder.img.setImageResource(myList.getImage());

        reference = FirebaseDatabase.getInstance().getReference().child("orders");

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            OrderItem p = dataSnapshot1.getValue(OrderItem.class);
                            if (p.getOwner().equals(myList.getOwner()) && p.getTitle().equals(myList.getTitle())) {
                                dataSnapshot1.getRef().removeValue();
                                Toast.makeText(v.getContext(), "Order completed", Toast.LENGTH_SHORT).show();

                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();

                                // Set Custom Title
                                TextView title = new TextView(context);
                                // Title Properties
                                title.setText("Rate!");
                                title.setPadding(10, 20, 10, 10);   // Set Position
                                title.setGravity(Gravity.CENTER);
                                title.setTextColor(Color.BLACK);
                                title.setTextSize(20);
                                alertDialog.setCustomTitle(title);

                                // Set Message
                                TextView msg = new TextView(context);
                                msg.setText("Do you want to rate the user?");
                                msg.setGravity(Gravity.CENTER);
                                msg.setTextColor(Color.BLACK);
                                alertDialog.setView(msg);


                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });


                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });


                                new Dialog(context.getApplicationContext());
                                alertDialog.show();


                                final Button bOk = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) bOk.getLayoutParams();
                                neutralBtnLP.bottomMargin = 20;
                                neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
                                bOk.setTextColor(Color.WHITE);
                                bOk.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                                bOk.setLayoutParams(neutralBtnLP);

                                final Button bCancel = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                                LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) bOk.getLayoutParams();
                                negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
                                bCancel.setTextColor(Color.WHITE);
                                bCancel.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                                bCancel.setLayoutParams(negBtnLP);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(v.getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                removeAt(holder.getAdapterPosition());
            }
        });

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            OrderItem p = dataSnapshot1.getValue(OrderItem.class);
                            if (p.getOwner().equals(myList.getOwner()) && p.getTitle().equals(myList.getTitle())) {
                                Toast.makeText(v.getContext(), "Order removed", Toast.LENGTH_SHORT).show();
                                dataSnapshot1.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(v.getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                removeAt(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView title, user, descr;
        ImageView img;
        Button btnAccept, btnCancel;


        MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewOrderTitle);
            user = itemView.findViewById(R.id.textViewUser);
            descr = itemView.findViewById(R.id.textViewOrderDescr);
            img = itemView.findViewById(R.id.imageViewProduct);
            btnAccept = itemView.findViewById(R.id.buttonAcceptOrder);
            btnCancel = itemView.findViewById(R.id.buttonCancelOrder);
        }
    }

    private void removeAt(int position) {
        if (position == list.size() - 1) {
            list.remove(position);
            notifyItemRemoved(position);
        } else {
            int shift = 0;
            while (true) {
                try {
                    list.remove(position - shift);
                    notifyItemRemoved(position);
                    break;
                } catch (IndexOutOfBoundsException e) {
                    shift++;
                }
            }
        }
    }

}