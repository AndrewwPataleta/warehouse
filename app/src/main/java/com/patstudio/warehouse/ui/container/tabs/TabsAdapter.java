package com.patstudio.warehouse.ui.container.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.patstudio.warehouse.ui.container.tabs.done.DoneFragment;
import com.patstudio.warehouse.ui.container.tabs.pack.PackFragment;
import com.patstudio.warehouse.ui.container.tabs.pending.PendingFragment;
import com.patstudio.warehouse.ui.container.tabs.pick.PickFragment;

public class TabsAdapter extends FragmentStateAdapter {

    final int PAGE_COUNT = 4;

    public TabsAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0 : {
                fragment = new PickFragment();
                return fragment;
            }
            case 1 : {
                fragment = new PendingFragment();
                return fragment;
            }
            case 2 : {
                fragment = new PackFragment();
                return fragment;
            }
            case 3 : {
                fragment = new DoneFragment();
                return fragment;
            }
        }
        return fragment;
    }


    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}