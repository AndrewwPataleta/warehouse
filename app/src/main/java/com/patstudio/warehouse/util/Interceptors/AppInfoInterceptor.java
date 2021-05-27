package com.patstudio.warehouse.util.Interceptors;


import com.patstudio.warehouse.BuildConfig;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AppInfoInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder();

            builder.addHeader("platform", "android");
            builder.addHeader("app", BuildConfig.APPLICATION_ID);
            builder.addHeader("version", String.valueOf(BuildConfig.VERSION_CODE));
            builder.addHeader("build_type", BuildConfig.BUILD_TYPE);

            request = builder.build();

            return chain.proceed(request);

        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw ioException;
        }
    }
}
