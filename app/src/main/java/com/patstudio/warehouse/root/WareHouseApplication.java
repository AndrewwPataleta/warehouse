package com.patstudio.warehouse.root;

import android.app.Application;

import com.patstudio.warehouse.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class WareHouseApplication extends DaggerApplication {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
