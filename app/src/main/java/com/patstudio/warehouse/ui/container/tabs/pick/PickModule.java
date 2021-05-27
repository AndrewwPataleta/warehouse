package com.patstudio.warehouse.ui.container.tabs.pick;


import com.patstudio.warehouse.di.scopes.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {PickModule.PickAbstractModule.class})
public class PickModule {

    @Module
    public interface PickAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        PickFragment pickFragment();
    }

}
