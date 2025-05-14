package com.example.food01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food01.R;
import com.example.food01.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单列表适配器
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<MenuItem> menuItems = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setMenuItems(List<MenuItem> items) {
        this.menuItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFood;
        private TextView tvName;
        private TextView tvDescription;
        private TextView tvPrice;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.iv_food);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPrice = itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(menuItems.get(position));
                }
            });
        }

        public void bind(MenuItem item) {
            tvName.setText(item.getName());
            tvDescription.setText(item.getDescription());
            tvPrice.setText(String.format("¥%.2f", item.getPrice()));

            // 加载图片
            try {
                String imageUrl = item.getImageUrl();
                if (imageUrl != null && imageUrl.startsWith("drawable/")) {
                    String resourceName = imageUrl.substring("drawable/".length());
                    int resourceId = itemView.getContext().getResources().getIdentifier(
                            resourceName, "drawable", itemView.getContext().getPackageName());
                    if (resourceId != 0) {
                        ivFood.setImageResource(resourceId);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
} 