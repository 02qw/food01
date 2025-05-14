package com.example.food01.manager;

import com.example.food01.model.CartItem;
import com.example.food01.model.Dish;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车管理类（单例模式）
 */
public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;
    private int currentRestaurantId = -1;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    /**
     * 添加商品到购物车
     * @param dish 要添加的菜品
     * @return 是否添加成功
     */
    public boolean addToCart(Dish dish) {
        // 检查是否是同一家餐厅的商品
        if (currentRestaurantId != -1 && currentRestaurantId != dish.getRestaurantId()) {
            return false;
        }

        // 更新当前餐厅ID
        if (currentRestaurantId == -1) {
            currentRestaurantId = dish.getRestaurantId();
        }

        // 查找购物车中是否已存在该商品
        for (CartItem item : cartItems) {
            if (item.getDish().equals(dish)) {
                item.setQuantity(item.getQuantity() + 1);
                return true;
            }
        }

        // 不存在则添加新的购物车项
        cartItems.add(new CartItem(dish, 1));
        return true;
    }

    /**
     * 从购物车移除商品
     * @param dish 要移除的菜品
     */
    public void removeFromCart(Dish dish) {
        cartItems.removeIf(item -> item.getDish().equals(dish));
        if (cartItems.isEmpty()) {
            currentRestaurantId = -1;
        }
    }

    /**
     * 更新商品数量
     * @param dish 要更新的菜品
     * @param quantity 新的数量
     */
    public void updateQuantity(Dish dish, int quantity) {
        if (quantity <= 0) {
            removeFromCart(dish);
            return;
        }

        for (CartItem item : cartItems) {
            if (item.getDish().equals(dish)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    /**
     * 清空购物车
     */
    public void clearCart() {
        cartItems.clear();
        currentRestaurantId = -1;
    }

    /**
     * 获取购物车中的所有商品
     */
    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    /**
     * 获取购物车商品总数
     */
    public int getItemCount() {
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }

    /**
     * 获取购物车总价
     */
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * 获取当前购物车餐厅ID
     */
    public int getCurrentRestaurantId() {
        return currentRestaurantId;
    }

    /**
     * 检查购物车是否为空
     */
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
} 