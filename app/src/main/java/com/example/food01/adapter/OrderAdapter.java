package com.example.food01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food01.R;
import com.example.food01.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public OrderAdapter(List<Order> orders, OnOrderClickListener listener) {
        this.orders = orders;
        this.listener = listener;
    }

    public void updateData(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView restaurantImage;
        private TextView restaurantNameText;
        private TextView orderTimeText;
        private TextView totalPriceText;
        private TextView statusText;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
            restaurantNameText = itemView.findViewById(R.id.restaurantNameText);
            orderTimeText = itemView.findViewById(R.id.orderTimeText);
            totalPriceText = itemView.findViewById(R.id.totalPriceText);
            statusText = itemView.findViewById(R.id.statusText);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onOrderClick(orders.get(position));
                }
            });
        }

        public void bind(Order order) {
            restaurantNameText.setText(order.getRestaurantName());
            orderTimeText.setText(order.getOrderTime());
            totalPriceText.setText(String.format("¥%.2f", order.getTotalPrice()));
            statusText.setText(order.getStatus());

            String imageUrl = order.getImageUrl();
            if (imageUrl.startsWith("drawable://")) {
                int resourceId = Integer.parseInt(imageUrl.substring("drawable://".length()));
                Glide.with(itemView.getContext())
                        .load(resourceId)
                        .into(restaurantImage);
            } else {
                Glide.with(itemView.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                        .into(restaurantImage);
            }
        }
    }
} 