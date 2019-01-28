package org.ieselcaminas.pro.myfirstproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ItemViewHolder> {
    private List<OrderItem> orderList;
    private Context mContext;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ItemViewHolder(view);
    }

    public OrderAdapter(Context mContext, List<OrderItem> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        OrderItem orderItem = orderList.get(position);
        holder.mTvTitle.setText(orderItem.getTitle());
        holder.mTvUser.setText(orderItem.getTitle());
        holder.mTvDescr.setText(orderItem.getDescr());
        holder.mTvImage.setImageResource(orderItem.getImage());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle, mTvUser, mTvDescr;
        ImageView mTvImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvUser = itemView.findViewById(R.id.textViewUser);
            mTvTitle = itemView.findViewById(R.id.textViewTitle);
            mTvDescr = itemView.findViewById(R.id.textViewDescr);
            mTvImage = itemView.findViewById(R.id.imageViewProduct);

        }
    }
}