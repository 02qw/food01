package com.example.food01.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food01.R;
import com.example.food01.adapter.OrderAdapter;
import com.example.food01.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单页面Fragment
 */
public class OrderFragment extends Fragment implements OrderAdapter.OnOrderClickListener {

    private RecyclerView orderListRecyclerView;
    private OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupRecyclerView();
        loadData();
    }

    private void initViews(View view) {
        orderListRecyclerView = view.findViewById(R.id.rv_orders);
    }

    private void setupRecyclerView() {
        orderListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new OrderAdapter(new ArrayList<>(), this);
        orderListRecyclerView.setAdapter(orderAdapter);
    }

    private void loadData() {
        // 加载订单数据
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "老王炸鸡", "2024-01-20 12:30",
                "已完成", 45.5, "drawable://" + R.drawable.img_fast_food));
        orders.add(new Order(2, "川味小馆", "2024-01-19 18:45",
                "已完成", 88.0, "drawable://" + R.drawable.img_hotpot));
        orders.add(new Order(3, "烧烤大排档", "2024-01-18 20:15",
                "已完成", 156.0, "drawable://" + R.drawable.img_bbq));
        orders.add(new Order(4, "甜品奶茶", "2024-01-17 15:20",
                "已完成", 25.0, "drawable://" + R.drawable.img_drinks));
        orderAdapter.updateData(orders);
    }

    @Override
    public void onOrderClick(Order order) {
        // 处理订单点击
        Toast.makeText(getContext(),
                String.format("查看订单: %s - ¥%.2f", order.getRestaurantName(), order.getTotalPrice()),
                Toast.LENGTH_SHORT).show();
        // TODO: 跳转到订单详情页
    }
} 