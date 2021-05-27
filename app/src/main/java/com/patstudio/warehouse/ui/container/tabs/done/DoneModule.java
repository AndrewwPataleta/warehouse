package com.patstudio.warehouse.ui.container.tabs.done;


import com.patstudio.warehouse.di.scopes.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {DoneModule.PickAbstractModule.class})
public class DoneModule {

    @Module
    public interface PickAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        DoneFragment packFragment();
    }

}
