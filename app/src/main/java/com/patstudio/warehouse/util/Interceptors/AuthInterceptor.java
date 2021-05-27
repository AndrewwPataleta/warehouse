package com.patstudio.warehouse.util.Interceptors;

import android.content.Context;

import com.patstudio.warehouse.data.interfaces.UserDataSource;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private Context context;
    private UserDataSource userDataSource;

    public AuthInterceptor(Context context, UserDataSource userDataSource) {

        this.context = context;
        this.userDataSource = userDataSource;

    }

    @Override
    public Response intercept(Chain chain) throws IOException, ConnectException {
        try {
            Request request = chain.request();
            String token = userDataSource.getAccessToken();
            //if (token != null)
                request = request.newBuilder().addHeader("Authorization", "Bearer devteam").build();
            Response response = chain.proceed(request);
            return response;
        } catch (ConnectException connectException) {
            throw connectException;
        }
    }


}
