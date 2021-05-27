package com.patstudio.warehouse.ui.container.tabs.pending;


import com.patstudio.warehouse.di.scopes.FragmentScoped;
import com.patstudio.warehouse.ui.container.tabs.pick.PickFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {PendingModule.PickAbstractModule.class})
public class PendingModule {

    @Module
    public interface PickAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        PickFragment pickFragment();
    }

}
