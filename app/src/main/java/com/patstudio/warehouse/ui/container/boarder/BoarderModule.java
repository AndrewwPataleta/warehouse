package com.patstudio.warehouse.ui.container.boarder;


import com.patstudio.warehouse.di.scopes.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {BoarderModule.PickAbstractModule.class})
public class BoarderModule {

    @Module
    public interface PickAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        BoarderFragment pickFragment();
    }

}
