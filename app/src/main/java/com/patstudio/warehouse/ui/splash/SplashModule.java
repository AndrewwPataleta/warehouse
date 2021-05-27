package com.patstudio.warehouse.ui.splash;


import com.patstudio.warehouse.di.scopes.ActivityScoped;
import com.patstudio.warehouse.util.providers.BaseResourceProvider;
import com.patstudio.warehouse.util.providers.ResourceProvider;

import dagger.Module;
import dagger.Provides;


@Module()
public class SplashModule {

    @ActivityScoped
    @Provides
    BaseResourceProvider provideResourceProvider(SplashActivity context) {
        return new ResourceProvider(context);
    }

}
