package com.patstudio.warehouse.ui.container.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.databinding.FragmentBoarderBinding;
import com.patstudio.warehouse.databinding.FragmentSettingsBinding;
import com.patstudio.warehouse.ui.container.tabs.pick.PickAdapter;
import com.patstudio.warehouse.util.MessageManager;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class SettingsFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentSettingsBinding binding;

    private SettingsViewModel settingsViewModel;

    private MessageManager messageManager;

    public SettingsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initViewModel() {
        settingsViewModel = ViewModelProviders.of(this,viewModelFactory).get(SettingsViewModel.class);
        this.binding.setSettingsViewModel(settingsViewModel);
        getLifecycle().addObserver(settingsViewModel);

        settingsViewModel.getBearer().observe(this, bearer -> {
            this.binding.setBearer(bearer);
        });
        settingsViewModel.getUrlBase().observe(this, urlBase -> {
            this.binding.setUrlBase(urlBase);
        });
        settingsViewModel.getMessage().observe(this, message -> {
            String liveDataMessage = message.getContentIfNotHandled();
            if (liveDataMessage != null) {
                messageManager.showBottomMessage(getResources().getString(R.string.notification),liveDataMessage );
            }

        });
        settingsViewModel.getUrlBaseError().observe(this, error -> {

            if (error) {
                this.binding.login.setVisibility(View.GONE);
                binding.urlServer.setBackground(getResources().getDrawable(R.drawable.et_error_background));
            } else {
                this.binding.login.setVisibility(View.VISIBLE);
                binding.urlServer.setBackground(getResources().getDrawable(R.drawable.et_background));
            }
        });

    }

    private void initView() {
        messageManager = new MessageManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_settings, container, false);
        initViewModel();
        initView();
        return binding.getRoot();
    }
}