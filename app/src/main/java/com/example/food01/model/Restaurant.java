package com.example.food01.model;

import android.content.Context;
import com.example.food01.R;

/**
 * 餐厅实体类
 */
public class Restaurant {
    private int id;
    private String name;
    private String imageUrl;
    private float rating;
    private int monthSales;
    private String deliveryTime;
    private String deliveryFee;
    private String promotion;
    private int categoryId;

    public Restaurant(int id, String name, String imageUrl, float rating, int monthSales,
                     String deliveryTime, String deliveryFee, String promotion, int categoryId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.monthSales = monthSales;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
        this.promotion = promotion;
        this.categoryId = categoryId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public float getRating() { return rating; }
    public int getMonthSales() { return monthSales; }
    public String getDeliveryTime() { return deliveryTime; }
    public String getDeliveryFee() { return deliveryFee; }
    public String getPromotion() { return promotion; }
    public int getCategoryId() { return categoryId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setRating(float rating) { this.rating = rating; }
    public void setMonthSales(int monthSales) { this.monthSales = monthSales; }
    public void setDeliveryTime(String deliveryTime) { this.deliveryTime = deliveryTime; }
    public void setDeliveryFee(String deliveryFee) { this.deliveryFee = deliveryFee; }
    public void setPromotion(String promotion) { this.promotion = promotion; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    /**
     * 获取图片资源ID
     * @param context 上下文
     * @return 资源ID，如果获取失败则返回默认图片资源ID
     */
    public int getImageResourceId(Context context) {
        try {
            if (imageUrl != null && imageUrl.startsWith("drawable/")) {
                String resourceName = imageUrl.substring("drawable/".length());
                int resourceId = context.getResources().getIdentifier(
                    resourceName, "drawable", context.getPackageName());
                if (resourceId != 0) {
                    return resourceId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.drawable.ic_restaurant_default;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id == that.id;
    }
} 