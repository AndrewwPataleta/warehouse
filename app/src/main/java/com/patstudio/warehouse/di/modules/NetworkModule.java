package com.patstudio.warehouse.di.modules;


import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.patstudio.warehouse.BuildConfig;
import com.patstudio.warehouse.data.settings.local.SettingsLocalDataModule;
import com.patstudio.warehouse.data.settings.local.SettingsLocalDataSource;
import com.patstudio.warehouse.data.user.local.UserLocalDataModule;
import com.patstudio.warehouse.data.user.local.UserLocalDataSource;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.Interceptors.AppInfoInterceptor;
import com.patstudio.warehouse.util.Interceptors.AuthInterceptor;
import com.patstudio.warehouse.util.Interceptors.HostSelectionInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = { UserLocalDataModule.class, SettingsLocalDataModule.class})
public class NetworkModule {


    @Provides
    @AppScoped
    static Retrofit provideRetrofit(Gson gson, Application application, UserLocalDataSource userLocalDataSource, SettingsLocalDataSource settingsLocalDataSource) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(new ChuckInterceptor(application))
                .addInterceptor(new AuthInterceptor(application,userLocalDataSource))
                .addInterceptor(new AppInfoInterceptor())
                .addInterceptor(new HostSelectionInterceptor(application,settingsLocalDataSource))
                .build();



        return new Retrofit.Builder()
                .baseUrl(settingsLocalDataSource.getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @AppScoped

    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

}
