package com.patstudio.warehouse.ui.container.picking;


import com.patstudio.warehouse.di.scopes.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {PickingOrderModule.PickAbstractModule.class})
public class PickingOrderModule {

    @Module
    public interface PickAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        PickingOrderFragment pickFragment();
    }

}
