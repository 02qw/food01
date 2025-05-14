package com.example.food01.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food01.R;
import com.example.food01.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    public void updateData(List<Category> newCategories) {
        this.categories = newCategories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImage);
            textView = itemView.findViewById(R.id.categoryName);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onCategoryClick(categories.get(position));
                }
            });
        }

        public void bind(Category category) {
            textView.setText(category.getName());
            String imageUrl = category.getImageUrl();
            if (imageUrl != null && imageUrl.startsWith("drawable/")) {
                try {
                    String resourceName = imageUrl.substring("drawable/".length());
                    int resourceId = itemView.getContext().getResources().getIdentifier(
                        resourceName, "drawable", itemView.getContext().getPackageName());
                    if (resourceId != 0) {
                        imageView.setImageResource(resourceId);
                    } else {
                        imageView.setImageResource(R.drawable.ic_placeholder);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    imageView.setImageResource(R.drawable.ic_placeholder);
                }
            } else {
                imageView.setImageResource(R.drawable.ic_placeholder);
            }
        }
    }
} 