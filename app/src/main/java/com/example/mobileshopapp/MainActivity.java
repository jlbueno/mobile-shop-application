package com.example.mobileshopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<MortyListAdapter.MortyViewHolder> userCart = new ArrayList<>();

    public static void addItemToCart(MortyListAdapter.MortyViewHolder item) {
        System.out.println(String.format("Added: %s", item.getItemName()));
        userCart.add(item);
        viewCartItems();
    }

    public static void removeItemFromCart(MortyListAdapter.MortyViewHolder item) {
        System.out.println(String.format("Removed: %s", item.getItemName()));
        userCart.remove(item);
    }

    public static void viewCartItems() {
        for(MortyListAdapter.MortyViewHolder item: userCart) {
            System.out.println(String.format("%s: %d", item.getItemName(), item.getItemCount()));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create instance of the layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.home_label).setIcon(R.drawable.ic_baseline_home_24));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.shop_label).setIcon(R.drawable.ic_baseline_storefront_24));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cart_label).setIcon(R.drawable.ic_baseline_shopping_cart_24));

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by a fragment.
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // Set listener for clicks
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}