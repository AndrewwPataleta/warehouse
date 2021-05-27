package com.patstudio.warehouse.ui.container.settings;


import com.patstudio.warehouse.di.scopes.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {SettingsModule.PickAbstractModule.class})
public class SettingsModule {

    @Module
    public interface PickAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        SettingsFragment pickFragment();
    }

}
