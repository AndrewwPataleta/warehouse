package com.patstudio.warehouse.ui.auth;


import com.patstudio.warehouse.di.scopes.ActivityScoped;
import com.patstudio.warehouse.util.providers.BaseResourceProvider;
import com.patstudio.warehouse.util.providers.ResourceProvider;

import dagger.Module;
import dagger.Provides;


@Module()
public class LoginModule {

    @ActivityScoped
    @Provides
    BaseResourceProvider provideResourceProvider(LoginActivity context) {
        return new ResourceProvider(context);
    }

}
