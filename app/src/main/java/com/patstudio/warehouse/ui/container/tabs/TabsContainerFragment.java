package com.patstudio.warehouse.ui.container.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.patstudio.warehouse.R;
import com.patstudio.warehouse.databinding.FragmentTabsContainerBinding;


public class TabsContainerFragment extends Fragment {

    private TabsAdapter tabsAdapter;
    private ViewPager2 viewPager;
    FragmentTabsContainerBinding binding;

    public TabsContainerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initAdapter() {
        viewPager = binding.pager;
        tabsAdapter = new TabsAdapter(this);
        viewPager.setAdapter(tabsAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        new TabLayoutMediator(binding.tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0 :{
                            tab.setText(getResources().getString(R.string.pick));
                            break;
                        }
                        case 1 :{
                            tab.setText(getResources().getString(R.string.pending));
                            break;
                        }
                        case 2 :{
                            tab.setText(getResources().getString(R.string.pack));
                            break;
                        }
                        case 3 :{
                            tab.setText(getResources().getString(R.string.done));
                            break;
                        }
                    }
                }
        ).attach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_tabs_container, container, false);
        initAdapter();
        return binding.getRoot();
    }
}