package com.patstudio.warehouse.ui.container.tabs.pack;


import com.patstudio.warehouse.di.scopes.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {PackModule.PickAbstractModule.class})
public class PackModule {

    @Module
    public interface PickAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        PackFragment packFragment();
    }

}
