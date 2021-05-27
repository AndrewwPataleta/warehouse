package com.patstudio.warehouse.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.ui.auth.LoginActivity;
import com.patstudio.warehouse.ui.container.ContainerActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


@AppScoped
public class SplashActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashViewModel = ViewModelProviders.of(this,viewModelFactory).get(SplashViewModel.class);
        splashViewModel.getScreen().observe(this, action -> {
            if(action != null){
                handleAction(action);
            }
        });
    }

    private void handleAction(@NonNull final ActionSplashScreenModel action) {

        switch (action.getValue()){
            case ActionSplashScreenModel.SHOW_LOGIN:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case ActionSplashScreenModel.SHOW_MAIN:
                startActivity(new Intent(this, ContainerActivity.class));
                finish();
                break;
        }
    }

}