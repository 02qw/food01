package com.example.food01.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food01.R;

/**
 * 个人资料页面Fragment
 */
public class ProfileFragment extends Fragment {

    private TextView tvUsername;
    private TextView tvPhone;
    private TextView tvAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadUserData();
    }

    private void initViews(View view) {
        tvUsername = view.findViewById(R.id.tv_username);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvAddress = view.findViewById(R.id.tv_address);
    }

    private void loadUserData() {
        // TODO: 从SharedPreferences或其他数据源加载用户数据
        tvUsername.setText("测试用户");
        tvPhone.setText("电话：138****8888");
        tvAddress.setText("地址：北京市朝阳区xxx街道");
    }
} 