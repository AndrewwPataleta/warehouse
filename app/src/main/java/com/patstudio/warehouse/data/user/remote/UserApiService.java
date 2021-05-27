package com.patstudio.warehouse.data.user.remote;


import com.google.gson.JsonObject;
import com.patstudio.warehouse.data.models.TokenModel;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface UserApiService {



    @POST("/api/token")
    Single<TokenModel> getToken(@Body JsonObject jsonObject);
}
