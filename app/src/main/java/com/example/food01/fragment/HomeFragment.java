package com.example.food01.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food01.R;
import com.example.food01.adapter.CategoryAdapter;
import com.example.food01.adapter.RestaurantAdapter;
import com.example.food01.databinding.FragmentHomeBinding;
import com.example.food01.model.Category;
import com.example.food01.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页Fragment
 */
public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener {

    private FragmentHomeBinding binding;
    private RecyclerView categoryRecyclerView;
    private RecyclerView restaurantRecyclerView;
    private SearchView searchView;
    private CategoryAdapter categoryAdapter;
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> allRestaurants; // 添加成员变量存储所有商家

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupRecyclerViews();
        setupSearchView();
        loadData();
    }

    private void initViews(View view) {
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        restaurantRecyclerView = view.findViewById(R.id.restaurantRecyclerView);
        searchView = view.findViewById(R.id.searchView);
        
        // 确保RecyclerView已初始化
        if (restaurantRecyclerView == null) {
            throw new IllegalStateException("restaurantRecyclerView not found");
        }
    }

    private void setupRecyclerViews() {
        // 设置分类列表
        categoryRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), this);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // 设置商家列表
        restaurantRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        restaurantAdapter = new RestaurantAdapter(restaurant -> {
            // 使用新的导航ID进行导航
            Bundle args = new Bundle();
            args.putInt("restaurantId", restaurant.getId());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_restaurantList_to_detail, args);
        });
        restaurantRecyclerView.setAdapter(restaurantAdapter);
    }

    private void setupSearchView() {
        if (searchView == null) {
            return;
        }

        // 设置搜索框样式
        searchView.setQueryHint("搜索商家");
        searchView.setIconified(false);
        searchView.clearFocus(); // 初始时不获取焦点

        // 设置搜索框监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRestaurants(query);
                searchView.clearFocus(); // 提交后清除焦点，收起键盘
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 实时搜索，用户输入时就开始搜索
                searchRestaurants(newText);
                return true;
            }
        });

        // 设置关闭按钮点击事件
        View closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> {
                searchView.setQuery("", false);
                searchView.clearFocus();
                // 显示所有商家
                restaurantAdapter.setRestaurants(new ArrayList<>(allRestaurants));
            });
        }
    }

    private void loadData() {
        // 加载分类数据
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "快餐", "drawable/img_fast_food"));
        categories.add(new Category(2, "火锅", "drawable/img_hotpot"));
        categories.add(new Category(3, "烧烤", "drawable/img_bbq"));
        categories.add(new Category(4, "饮品", "drawable/img_drinks"));
        categories.add(new Category(0, "全部", "drawable/img_all")); // 添加"全部"分类，使用ID 0
        categoryAdapter.updateData(categories);

        // 加载商家数据
        allRestaurants = new ArrayList<>(); // 初始化allRestaurants
        
        // 快餐类商家
        allRestaurants.add(new Restaurant(1, "老王炸鸡", "drawable/img_fried_chicken",
                4.8f, 328, "30-40分钟", "配送费¥5", "满38减15", 1)); // 添加分类ID
                
        // 火锅类商家
        allRestaurants.add(new Restaurant(2, "川味小炒", "drawable/img_hotpot",
                4.7f, 456, "40-50分钟", "配送费¥6", "满100减20", 2));
                
        // 烧烤类商家
        allRestaurants.add(new Restaurant(3, "老街烧烤", "drawable/img_bbq",
                4.5f, 234, "35-50分钟", "配送费¥5", "满50减12", 3));
                
        // 饮品类商家
        allRestaurants.add(new Restaurant(4, "甜蜜奶茶", "drawable/img_drinks",
                4.8f, 567, "15-25分钟", "配送费¥3", "满20减5", 4));
                
        restaurantAdapter.setRestaurants(new ArrayList<>(allRestaurants)); // 显示所有商家
    }

    private void searchRestaurants(String query) {
        if (query == null || query.trim().isEmpty()) {
            // 如果搜索框为空，显示所有商家
            restaurantAdapter.setRestaurants(new ArrayList<>(allRestaurants));
            return;
        }

        // 转换为小写，用于不区分大小写的搜索
        String lowerQuery = query.toLowerCase().trim();

        // 使用Stream API过滤商家
        List<Restaurant> searchResults = allRestaurants.stream()
                .filter(restaurant -> {
                    String name = restaurant.getName().toLowerCase();
                    // 模糊匹配：只要商家名称包含搜索词就显示
                    return name.contains(lowerQuery);
                })
                .collect(Collectors.toList());

        // 更新UI显示搜索结果
        restaurantAdapter.setRestaurants(searchResults);

        // 如果没有搜索结果，显示提示
        if (searchResults.isEmpty()) {
            Toast.makeText(getContext(), "没有找到相关商家", Toast.LENGTH_SHORT).show();
        }
    }

    private void filterRestaurantsByCategory(int categoryId) {
        if (categoryId == 0) {
            // 显示所有商家
            restaurantAdapter.setRestaurants(new ArrayList<>(allRestaurants));
        } else {
            // 筛选指定分类的商家
            List<Restaurant> filteredList = allRestaurants.stream()
                    .filter(restaurant -> restaurant.getCategoryId() == categoryId)
                    .collect(Collectors.toList());
            restaurantAdapter.setRestaurants(filteredList);
        }
    }

    @Override
    public void onCategoryClick(Category category) {
        filterRestaurantsByCategory(category.getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 