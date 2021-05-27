package com.patstudio.warehouse.ui.auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.databinding.ActivityLoginBinding;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.ui.container.ContainerActivity;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


@AppScoped
public class LoginActivity extends DaggerAppCompatActivity {

    private ActivityLoginBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private LoginViewModel loginViewModel;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this,viewModelFactory).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        loginViewModel.getUser().observe(this, loginUser -> {
            if (validateEmailUser(loginUser) && validatePasswordUser(loginUser))
                loginViewModel.login();
        });

        loginViewModel.needShowProgressLogin.observe(this, needLoading -> {
            if (needLoading) {
                showProgressLogin();
            } else {
                showLoginButton();
            }
        });

        loginViewModel.getScreen().observe(this, action -> {
            if(action != null){
                handleAction(action);
            }
        });
    }

    private void handleAction(@NonNull final ActionLoginScreenModel action) {

        switch (action.getValue()){
            case ActionLoginScreenModel.SHOW_MAIN:
                startActivity(new Intent(this, ContainerActivity.class));
                finish();
                break;
            case ActionLoginScreenModel.SHOW_FORGOT_PASSWORD:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void showProgressLogin() {
        this.binding.progressLogin.setVisibility(View.VISIBLE);
        this.binding.login.setVisibility(View.GONE);
    }

    private void showLoginButton() {
        this.binding.login.setVisibility(View.VISIBLE);
        this.binding.progressLogin.setVisibility(View.GONE);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private boolean validatePasswordUser(LoginUserModel loginUser) {
        boolean isValidate = true;

        if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
            binding.password.setBackground(getResources().getDrawable(R.drawable.et_error_background));
            binding.password.setHint(getString(R.string.enter_a_password));
            isValidate = false;
        }
        else if (!loginUser.isPasswordLengthGreaterThan5()) {
            binding.password.setBackground(getResources().getDrawable(R.drawable.et_error_background));
            binding.password.setHint(getString(R.string.enter_a_password));
            isValidate = false;
        } else {
            binding.password.setBackground(getResources().getDrawable(R.drawable.et_background));
            binding.password.setHint(getString(R.string.password));
        }

        return isValidate;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private boolean validateEmailUser(LoginUserModel loginUser) {
        boolean isValidate = true;

        if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress())) {
            binding.email.setBackground(getResources().getDrawable(R.drawable.et_error_background));
            binding.email.setHint(getString(R.string.enter_email_address));
            isValidate = false;
        }
        else if (!loginUser.isEmailValid()) {
            binding.email.setBackground(getResources().getDrawable(R.drawable.et_error_background));
            binding.email.setHint(getString(R.string.enter_valid_email_address));
            isValidate = false;
        } else {
            binding.email.setBackground(getResources().getDrawable(R.drawable.et_background));
            binding.email.setHint(getString(R.string.email));
        }

        return isValidate;
    }



}