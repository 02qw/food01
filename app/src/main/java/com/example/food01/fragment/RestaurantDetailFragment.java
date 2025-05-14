package com.example.food01.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.food01.R;
import com.example.food01.adapter.MenuAdapter;
import com.example.food01.databinding.FragmentRestaurantDetailBinding;
import com.example.food01.model.MenuItem;
import com.example.food01.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * 商家详情页面
 */
public class RestaurantDetailFragment extends Fragment {
    private static final String TAG = "RestaurantDetail";
    private FragmentRestaurantDetailBinding binding;
    private int restaurantId = -1;
    private MenuAdapter menuAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                restaurantId = getArguments().getInt("restaurantId", -1);
                Log.d(TAG, "Received restaurantId: " + restaurantId);
                if (restaurantId == -1) {
                    Log.e(TAG, "Invalid restaurantId");
                    navigateBack();
                }
            } else {
                Log.e(TAG, "No arguments received");
                navigateBack();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            navigateBack();
        }
    }

    private void navigateBack() {
        try {
            if (getView() != null) {
                Navigation.findNavController(getView()).navigateUp();
            } else if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error navigating back", e);
            if (getActivity() != null) {
                getActivity().finish();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false);
            return binding.getRoot();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreateView", e);
            return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            setupMenuRecyclerView();
            loadRestaurantDetails();
        } catch (Exception e) {
            Log.e(TAG, "Error in onViewCreated", e);
            navigateBack();
        }
    }

    private void setupMenuRecyclerView() {
        try {
            if (binding != null && getContext() != null) {
                menuAdapter = new MenuAdapter();
                binding.rvMenu.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvMenu.setAdapter(menuAdapter);
                
                menuAdapter.setOnItemClickListener(item -> {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), 
                            "查看" + item.getName() + "详情", 
                            Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in setupMenuRecyclerView", e);
        }
    }

    private void loadRestaurantDetails() {
        try {
            if (getContext() == null) {
                Log.e(TAG, "Context is null");
                return;
            }
            
            if (restaurantId == -1) {
                Log.e(TAG, "Invalid restaurantId");
                navigateBack();
                return;
            }
            
            Restaurant restaurant = null;
            switch (restaurantId) {
                case 1: // 老王炸鸡
                    restaurant = new Restaurant(
                        1,                          // id
                        "老王炸鸡",                  // name
                        "drawable/img_fast_food",   // imageUrl
                        4.8f,                      // rating
                        328,                       // monthSales
                        "30-40分钟",                // deliveryTime
                        "配送费¥5",                 // deliveryFee
                        "满38减15",                 // promotion
                        1                          // categoryId
                    );
                    break;
                
                case 2: // 川味小炒
                    restaurant = new Restaurant(
                        2,                          // id
                        "川味小炒",                  // name
                        "drawable/img_hotpot",      // imageUrl
                        4.7f,                      // rating
                        456,                       // monthSales
                        "40-50分钟",                // deliveryTime
                        "配送费¥6",                 // deliveryFee
                        "满100减20",                // promotion
                        2                          // categoryId
                    );
                    break;
                
                case 3: // 老街烧烤
                    restaurant = new Restaurant(
                        3,                          // id
                        "老街烧烤",                  // name
                        "drawable/img_bbq",         // imageUrl
                        4.5f,                      // rating
                        234,                       // monthSales
                        "35-50分钟",                // deliveryTime
                        "配送费¥5",                 // deliveryFee
                        "满50减12",                 // promotion
                        3                          // categoryId
                    );
                    break;
                
                case 4: // 甜蜜奶茶
                    restaurant = new Restaurant(
                        4,                          // id
                        "甜蜜奶茶",                  // name
                        "drawable/img_drinks",      // imageUrl
                        4.8f,                      // rating
                        567,                       // monthSales
                        "15-25分钟",                // deliveryTime
                        "配送费¥3",                 // deliveryFee
                        "满20减5",                  // promotion
                        4                          // categoryId
                    );
                    break;
                
                default:
                    Log.e(TAG, "Unknown restaurantId: " + restaurantId);
                    navigateBack();
                    return;
            }

            if (restaurant != null) {
                updateUI(restaurant);
                loadMenuItems(restaurant.getId());
            } else {
                Log.e(TAG, "Failed to create restaurant object");
                navigateBack();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in loadRestaurantDetails", e);
            navigateBack();
        }
    }

    private void loadMenuItems(int restaurantId) {
        try {
            if (menuAdapter == null) {
                Log.e(TAG, "MenuAdapter is null");
                return;
            }
            
            // 模拟菜单数据
            List<MenuItem> menuItems = new ArrayList<>();
            
            switch (restaurantId) {
                case 1: // 老王炸鸡的菜单
                    menuItems.add(new MenuItem(1, "香辣炸鸡", "外酥里嫩，香辣可口，特制秘方腌制", 28.0, "drawable/img_fried_chicken", restaurantId));
                    menuItems.add(new MenuItem(2, "蜜汁炸鸡", "甜中带辣，口感独特", 30.0, "drawable/img_fried_chicken", restaurantId));
                    menuItems.add(new MenuItem(3, "原味炸鸡", "原汁原味，健康美味", 25.0, "drawable/img_fried_chicken", restaurantId));
                    break;
                    
                case 2: // 川味小炒的菜单
                    menuItems.add(new MenuItem(4, "回锅肉", "传统川菜，肥而不腻", 38.0, "drawable/img_hotpot", restaurantId));
                    menuItems.add(new MenuItem(5, "宫保鸡丁", "经典川菜，口感独特", 32.0, "drawable/img_hotpot", restaurantId));
                    menuItems.add(new MenuItem(6, "麻婆豆腐", "麻辣鲜香，入口即化", 28.0, "drawable/img_hotpot", restaurantId));
                    break;
                    
                case 3: // 老街烧烤的菜单
                    menuItems.add(new MenuItem(7, "羊肉串", "新疆羊肉，鲜嫩多汁", 6.0, "drawable/img_bbq", restaurantId));
                    menuItems.add(new MenuItem(8, "烤鸡翅", "外焦里嫩，香气四溢", 8.0, "drawable/img_bbq", restaurantId));
                    menuItems.add(new MenuItem(9, "烤生蚝", "海鲜烧烤，新鲜美味", 12.0, "drawable/img_bbq", restaurantId));
                    break;
                    
                case 4: // 甜品奶茶的菜单
                    menuItems.add(new MenuItem(10, "珍珠奶茶", "经典饮品，浓香醇厚", 15.0, "drawable/img_drinks", restaurantId));
                    menuItems.add(new MenuItem(11, "水果茶", "新鲜水果，清爽怡人", 18.0, "drawable/img_drinks", restaurantId));
                    menuItems.add(new MenuItem(12, "芒果冰沙", "夏日特饮，清凉解暑", 20.0, "drawable/img_drinks", restaurantId));
                    break;
                    
                default:
                    Log.e(TAG, "Unknown restaurantId for menu items: " + restaurantId);
                    return;
            }
            
            menuAdapter.setMenuItems(menuItems);
        } catch (Exception e) {
            Log.e(TAG, "Error in loadMenuItems", e);
        }
    }

    private void updateUI(Restaurant restaurant) {
        try {
            if (binding == null) {
                Log.e(TAG, "Binding is null");
                return;
            }
            
            if (getContext() == null) {
                Log.e(TAG, "Context is null");
                return;
            }
            
            binding.tvRestaurantName.setText(restaurant.getName());
            binding.tvRating.setText(String.format("评分：%.1f", restaurant.getRating()));
            binding.tvMonthSales.setText(String.format("月售：%d", restaurant.getMonthSales()));
            binding.tvDeliveryTime.setText(restaurant.getDeliveryTime());
            binding.tvDeliveryFee.setText(restaurant.getDeliveryFee());
            binding.tvPromotion.setText(restaurant.getPromotion());

            // 加载商家图片
            String imageUrl = restaurant.getImageUrl();
            if (imageUrl != null && imageUrl.startsWith("drawable/")) {
                String resourceName = imageUrl.substring("drawable/".length());
                int resourceId = getContext().getResources().getIdentifier(
                        resourceName, "drawable", getContext().getPackageName());
                if (resourceId != 0) {
                    binding.ivRestaurantCover.setImageResource(resourceId);
                } else {
                    Log.e(TAG, "Resource not found: " + resourceName);
                }
            } else {
                Log.e(TAG, "Invalid image URL: " + imageUrl);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in updateUI", e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        menuAdapter = null;
    }
} 