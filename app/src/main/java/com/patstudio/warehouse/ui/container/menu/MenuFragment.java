package com.patstudio.warehouse.ui.container.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.databinding.FragmentMenuBinding;
import com.patstudio.warehouse.ui.container.ContainerViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class MenuFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentMenuBinding binding;

    private ContainerViewModel containerViewModel;

    public MenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViewModel() {
        containerViewModel = ViewModelProviders.of(getActivity(),viewModelFactory).get(ContainerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_menu, container, false);
        initViewModel();
        binding.setContainerViewModel(containerViewModel);
        return binding.getRoot();
    }
}