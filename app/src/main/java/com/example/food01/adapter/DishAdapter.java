package com.example.food01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food01.R;
import com.example.food01.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 菜品列表适配器
 */
public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {
    private List<Dish> dishes = new ArrayList<>();
    private OnDishClickListener listener;

    public interface OnDishClickListener {
        void onAddToCart(Dish dish);
    }

    public DishAdapter(OnDishClickListener listener) {
        this.listener = listener;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishes.get(position);
        holder.bind(dish);
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    class DishViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDish;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvPrice;
        private ImageButton btnAddToCart;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDish = itemView.findViewById(R.id.iv_dish);
            tvName = itemView.findViewById(R.id.tv_dish_name);
            tvDescription = itemView.findViewById(R.id.tv_dish_description);
            tvPrice = itemView.findViewById(R.id.tv_dish_price);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        }

        public void bind(Dish dish) {
            tvName.setText(dish.getName());
            tvDescription.setText(dish.getDescription());
            tvPrice.setText(String.format(Locale.CHINA, "¥%.2f", dish.getPrice()));

            // 加载图片
            try {
                int resourceId = Integer.parseInt(dish.getImageUrl());
                ivDish.setImageResource(resourceId);
            } catch (Exception e) {
                e.printStackTrace();
                ivDish.setImageResource(R.drawable.ic_restaurant_default);
            }

            // 添加到购物车按钮点击事件
            btnAddToCart.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAddToCart(dish);
                }
            });
        }
    }
} 