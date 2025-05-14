package com.example.food01.model;

/**
 * 购物车项实体类
 */
public class CartItem {
    private Dish dish;
    private int quantity;

    public CartItem(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    // Getters
    public Dish getDish() { return dish; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return dish.getPrice() * quantity; }

    // Setters
    public void setDish(Dish dish) { this.dish = dish; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return dish.equals(cartItem.dish);
    }
} 