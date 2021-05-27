package com.patstudio.warehouse.di.modules;

import com.patstudio.warehouse.data.interfaces.OrderDataSource;
import com.patstudio.warehouse.data.order.remote.OrderApiService;
import com.patstudio.warehouse.data.order.remote.OrderRemoteDataModule;
import com.patstudio.warehouse.data.order.remote.OrderRemoteDataSource;
import com.patstudio.warehouse.di.scopes.AppScoped;

import dagger.Module;
import dagger.Provides;

@Module(includes = { OrderRemoteDataModule.class})
public class OrderRepositoryModule {

    @Provides
    @AppScoped
    OrderDataSource provideUserRemoteDataSource(OrderApiService apiService) {
        return new OrderRemoteDataSource(apiService);
    }
}
