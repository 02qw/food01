package com.example.food01.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food01.R;
import com.example.food01.adapter.CartAdapter;
import com.example.food01.manager.CartManager;
import com.example.food01.model.CartItem;

import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemChangeListener {

    private RecyclerView rvCartItems;
    private TextView tvEmptyCart;
    private TextView tvTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;
    private CartManager cartManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupRecyclerView();
        loadCartItems();
    }

    private void initViews(View view) {
        rvCartItems = view.findViewById(R.id.rv_cart_items);
        tvEmptyCart = view.findViewById(R.id.tv_empty_cart);
        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        btnCheckout = view.findViewById(R.id.btn_checkout);

        btnCheckout.setOnClickListener(v -> onCheckoutClicked());
    }

    private void setupRecyclerView() {
        cartManager = CartManager.getInstance();
        cartAdapter = new CartAdapter(this);
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCartItems.setAdapter(cartAdapter);
    }

    private void loadCartItems() {
        List<CartItem> cartItems = cartManager.getCartItems();
        cartAdapter.setCartItems(cartItems);
        updateUI();
    }

    private void updateUI() {
        List<CartItem> cartItems = cartManager.getCartItems();
        boolean isEmpty = cartItems.isEmpty();
        
        tvEmptyCart.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        rvCartItems.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        btnCheckout.setEnabled(!isEmpty);
        
        double totalPrice = cartManager.getTotalPrice();
        tvTotalPrice.setText(String.format(Locale.CHINA, "合计: ¥%.2f", totalPrice));
    }

    @Override
    public void onQuantityChanged(CartItem item, int newQuantity) {
        if (newQuantity <= 0) {
            cartManager.removeFromCart(item.getDish());
        } else {
            cartManager.updateQuantity(item.getDish(), newQuantity);
        }
        updateUI();
    }

    private void onCheckoutClicked() {
        // 创建订单
        // TODO: 实现订单创建逻辑
        Toast.makeText(getContext(), "订单创建成功", Toast.LENGTH_SHORT).show();
        
        // 清空购物车
        cartManager.clearCart();
        updateUI();
        
        // 跳转到订单列表页面
        Navigation.findNavController(requireView())
                .navigate(R.id.action_cart_to_orders);
    }
} 