package com.patstudio.warehouse.ui.container.tabs.done;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.databinding.FragmentPackBinding;
import com.patstudio.warehouse.ui.container.ContainerViewModel;
import com.patstudio.warehouse.util.MessageManager;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class DoneFragment extends Fragment implements IDoneItemListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentPackBinding binding;

    private DoneViewModel doneViewModel;

    private ContainerViewModel containerViewModel;

    private DoneAdapter doneAdapter;

    private MessageManager messageManager;

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    public DoneFragment() {

    }

    private void initViewModel() {
        doneViewModel = ViewModelProviders.of(this,viewModelFactory).get(DoneViewModel.class);
        containerViewModel = ViewModelProviders.of(this,viewModelFactory).get(ContainerViewModel.class);
        doneViewModel.setDoneItem(this);
        getLifecycle().addObserver(doneViewModel);
    }

    private void initView() {
        binding.packContainer.setLayoutManager(linearLayoutManager);
        messageManager = new MessageManager(getActivity());
        doneViewModel.getPickerOrders().observe(this, result -> {
            switch (result.status) {
                case SUCCESS: {
                    doneAdapter = new DoneAdapter(result.data, doneViewModel);
                    binding.packContainer.setAdapter(doneAdapter);
                    break;
                }
                case HAVE_NO_INTERNET_CONNECTION: {
                    messageManager.showBottomMessage(getResources().getString(R.string.notification), getResources().getString(R.string.have_no_internet_connection));
                    break;
                }
            }
        });
        doneViewModel.loadingOrders().observe(this, loading-> {
            if (loading) {
                binding.packContainer.setVisibility(View.GONE);
                binding.progress.setVisibility(View.VISIBLE);
            } else {
                binding.progress.setVisibility(View.GONE);
                binding.packContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_pack, container, false);
        initViewModel();
        initView();
        return binding.getRoot();
    }

    @Override
    public void selectItem(PickerOrderModel pickerOrderModel) {
        containerViewModel.selectPickerOrderDetail(pickerOrderModel);
    }

    @Override
    public void selectRevise(PickerOrderModel pickerOrderModel) {

    }

}