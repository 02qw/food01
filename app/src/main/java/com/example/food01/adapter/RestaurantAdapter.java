package com.example.food01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food01.R;
import com.example.food01.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * 餐厅列表适配器
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private List<Restaurant> restaurants = new ArrayList<>();
    private OnRestaurantClickListener listener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public RestaurantAdapter(OnRestaurantClickListener listener) {
        this.listener = listener;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.bind(restaurant);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivRestaurant;
        private TextView tvName;
        private TextView tvRating;
        private TextView tvMonthSales;
        private TextView tvDeliveryTime;
        private TextView tvDeliveryFee;
        private TextView tvPromotion;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRestaurant = itemView.findViewById(R.id.iv_restaurant);
            tvName = itemView.findViewById(R.id.tv_restaurant_name);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvMonthSales = itemView.findViewById(R.id.tv_month_sales);
            tvDeliveryTime = itemView.findViewById(R.id.tv_delivery_time);
            tvDeliveryFee = itemView.findViewById(R.id.tv_delivery_fee);
            tvPromotion = itemView.findViewById(R.id.tv_promotion);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRestaurantClick(restaurants.get(position));
                }
            });
        }

        public void bind(Restaurant restaurant) {
            tvName.setText(restaurant.getName());
            tvRating.setText(String.format("评分：%.1f", restaurant.getRating()));
            tvMonthSales.setText(String.format("月售：%d", restaurant.getMonthSales()));
            tvDeliveryTime.setText(restaurant.getDeliveryTime());
            tvDeliveryFee.setText(restaurant.getDeliveryFee());
            tvPromotion.setText(restaurant.getPromotion());

            // 加载图片
            try {
                String imageUrl = restaurant.getImageUrl();
                if (imageUrl != null) {
                    int resourceId = Integer.parseInt(imageUrl);
                    ivRestaurant.setImageResource(resourceId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 设置默认图片
                ivRestaurant.setImageResource(R.drawable.ic_restaurant_default);
            }
        }
    }
} 