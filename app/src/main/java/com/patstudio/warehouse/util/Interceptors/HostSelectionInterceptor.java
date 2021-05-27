package com.patstudio.warehouse.util.Interceptors;

import android.content.Context;

import com.patstudio.warehouse.BuildConfig;
import com.patstudio.warehouse.data.interfaces.SettingsDataSource;
import com.patstudio.warehouse.data.interfaces.UserDataSource;

import java.io.IOException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

public final class HostSelectionInterceptor implements Interceptor {

    private Context context;
    private SettingsDataSource settingsDataSource;


    public HostSelectionInterceptor(Context context, SettingsDataSource settingsDataSource) {
        this.context = context;
        this.settingsDataSource = settingsDataSource;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String host = settingsDataSource.getBaseUrl();
        if (host != null) {

            URL url = new URL(host);

            HttpUrl newUrl = request.url().newBuilder()
                    .host(url.getHost())
                    .port(url.getPort())
                    .build();
            request = request.newBuilder()

                    .url(newUrl)
                    .build();
        }

        try {
            return chain.proceed(request);
        } catch (IOException ioException) {
            throw ioException;
        }
    }
}