package com.example.food01.model;

public class Order {
    private int id;
    private String restaurantName;
    private String orderTime;
    private String status;
    private double totalPrice;
    private String imageUrl;

    public Order(int id, String restaurantName, String orderTime, String status, double totalPrice, String imageUrl) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.orderTime = orderTime;
        this.status = status;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }
} 