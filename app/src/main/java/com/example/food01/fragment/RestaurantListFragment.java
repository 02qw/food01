package com.example.food01.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food01.R;
import com.example.food01.adapter.RestaurantAdapter;
import com.example.food01.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * 餐厅列表Fragment
 */
public class RestaurantListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private SearchView searchView;
    private List<Restaurant> allRestaurants = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupRecyclerView();
        setupSearchView();
        loadData();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.restaurantRecyclerView);
        searchView = view.findViewById(R.id.searchView);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterRestaurants(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRestaurants(newText);
                return true;
            }
        });
    }

    private void filterRestaurants(String query) {
        if (query == null || query.isEmpty()) {
            adapter.setRestaurants(allRestaurants);
            return;
        }

        List<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant restaurant : allRestaurants) {
            if (restaurant.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(restaurant);
            }
        }
        adapter.setRestaurants(filteredList);
    }

    private void setupRecyclerView() {
        if (getContext() == null) return;
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantAdapter(restaurant -> {
            try {
                // 使用Bundle传递参数
                Bundle args = new Bundle();
                args.putInt("restaurantId", restaurant.getId());
                Navigation.findNavController(requireView())
                        .navigate(R.id.action_restaurantList_to_detail, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        // 加载餐厅数据
        allRestaurants.clear();
        allRestaurants.add(new Restaurant(1, "老王炸鸡", String.valueOf(R.drawable.img_fast_food),
                4.8f, 328, "30-40分钟", "配送费¥5", "满38减15", 1));
        allRestaurants.add(new Restaurant(2, "川味小馆", String.valueOf(R.drawable.img_hotpot),
                4.7f, 456, "40-50分钟", "配送费¥6", "满100减20", 2));
        allRestaurants.add(new Restaurant(3, "烧烤大排档", String.valueOf(R.drawable.img_bbq),
                4.5f, 234, "35-50分钟", "配送费¥5", "满50减12", 3));
        allRestaurants.add(new Restaurant(4, "甜品奶茶", String.valueOf(R.drawable.img_drinks),
                4.8f, 567, "15-25分钟", "配送费¥3", "满20减5", 4));
        adapter.setRestaurants(allRestaurants);
    }
} 