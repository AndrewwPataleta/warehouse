package com.patstudio.warehouse.di.modules;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.ConnectivityUtils.DefaultOnlineChecker;
import com.patstudio.warehouse.util.ConnectivityUtils.OnlineChecker;
import com.patstudio.warehouse.util.schedulers.BaseSchedulerProvider;
import com.patstudio.warehouse.util.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

    @Provides
    @AppScoped
    ConnectivityManager provideConnectivityManager(Application context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @AppScoped
    OnlineChecker onlineChecker(ConnectivityManager cm) {
        return new DefaultOnlineChecker(cm);
    }


    @AppScoped
    @Provides
    BaseSchedulerProvider provideSchedulerProvider(){
        return SchedulerProvider.getInstance();
    }



}
