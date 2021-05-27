package com.patstudio.warehouse.di.modules;


import com.patstudio.warehouse.di.scopes.ActivityScoped;
import com.patstudio.warehouse.ui.auth.LoginActivity;
import com.patstudio.warehouse.ui.auth.LoginModule;
import com.patstudio.warehouse.ui.container.ContainerActivity;
import com.patstudio.warehouse.ui.container.ContainerModule;
import com.patstudio.warehouse.ui.splash.SplashActivity;
import com.patstudio.warehouse.ui.splash.SplashModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = {SplashModule.class})
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {LoginModule.class})
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {ContainerModule.class})
    abstract ContainerActivity containerActivity();
    
}
