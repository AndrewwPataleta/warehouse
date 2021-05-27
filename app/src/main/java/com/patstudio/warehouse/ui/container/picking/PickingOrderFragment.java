package com.patstudio.warehouse.ui.container.picking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.data.models.Resource;
import com.patstudio.warehouse.databinding.FragmentBoarderBinding;
import com.patstudio.warehouse.databinding.FragmentPickingOrderBinding;
import com.patstudio.warehouse.ui.container.ContainerViewModel;
import com.patstudio.warehouse.util.MessageManager;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class PickingOrderFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentPickingOrderBinding binding;

    private PickingOrderViewModel pickingOrderViewModel;

    private ContainerViewModel containerViewModel;

    private MessageManager messageManager;

    private Bundle bundle;

    public PickingOrderFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViewModel() {
        pickingOrderViewModel = ViewModelProviders.of(this,viewModelFactory).get(PickingOrderViewModel.class);
        containerViewModel = ViewModelProviders.of(this,viewModelFactory).get(ContainerViewModel.class);
        getLifecycle().addObserver(pickingOrderViewModel);

    }

    private void initView() {
        messageManager = new MessageManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_picking_order, container, false);
        initViewModel();
        initView();
        return binding.getRoot();
    }
}