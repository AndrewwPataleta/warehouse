package com.patstudio.warehouse.data.order.remote;

import com.patstudio.warehouse.di.modules.NetworkModule;
import com.patstudio.warehouse.di.scopes.AppScoped;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module(includes = {NetworkModule.class})
public class OrderRemoteDataModule {

    @AppScoped
    @Provides
    OrderApiService provideQuakesService(Retrofit retrofit) {
        return retrofit.create(OrderApiService.class);
    }




}
