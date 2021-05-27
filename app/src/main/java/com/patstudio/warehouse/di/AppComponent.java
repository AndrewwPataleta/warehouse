package com.patstudio.warehouse.di;

import android.app.Application;

import com.patstudio.warehouse.di.modules.ActivityBindingModule;
import com.patstudio.warehouse.di.modules.OrderRepositoryModule;
import com.patstudio.warehouse.di.modules.SettingsRepositoryModule;
import com.patstudio.warehouse.di.modules.UserRepositoryModule;
import com.patstudio.warehouse.di.modules.UtilsModule;
import com.patstudio.warehouse.di.modules.ViewModelModule;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.root.WareHouseApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * This is the root Dagger component.
 * {@link AndroidSupportInjectionModule}
 * is the module from Dagger.Android that helps with the generation
 * and location of subcomponents, which will be in our case, activities
 */
@AppScoped
@Component(modules = {
        UserRepositoryModule.class, SettingsRepositoryModule.class, OrderRepositoryModule.class, ViewModelModule.class,
        UtilsModule.class, AppModule.class,
        ActivityBindingModule.class, AndroidSupportInjectionModule.class})

public interface AppComponent extends AndroidInjector<WareHouseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
