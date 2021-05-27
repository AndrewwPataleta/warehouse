package com.patstudio.warehouse.di.modules;

import androidx.lifecycle.ViewModelProvider;

import com.patstudio.warehouse.di.auth.SplashViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(SplashViewModelFactory modelProvider);


}
