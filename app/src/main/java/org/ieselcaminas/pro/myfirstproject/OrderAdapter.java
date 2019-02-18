package org.ieselcaminas.pro.myfirstproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {

    Context context;
    ArrayList<OrderItem> list;

    public OrderAdapter(Context c, ArrayList<OrderItem> l) {
        context = c;
        list = l;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        OrderItem myList = list.get(position);
        holder.title.setText(myList.getTitle());
        holder.user.setText(myList.getUser());
        holder.descr.setText(myList.getDescr());
//        holder.img.setImageResource(myList.getImage());
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


        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ConsumerPlan);
            user = itemView.findViewById(R.id.textViewUser);
            descr = itemView.findViewById(R.id.textViewConsumerDescr);
            img = itemView.findViewById(R.id.imageViewProduct);

        }
    }

}