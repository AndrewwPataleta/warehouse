package com.patstudio.warehouse.data.user.remote;

import com.patstudio.warehouse.di.modules.NetworkModule;
import com.patstudio.warehouse.di.scopes.AppScoped;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module(includes = {NetworkModule.class})
public class UserRemoteDataModule {

    @AppScoped
    @Provides
    UserApiService provideQuakesService(Retrofit retrofit) {
        return retrofit.create(UserApiService.class);
    }


}
