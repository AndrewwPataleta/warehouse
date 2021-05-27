package com.patstudio.warehouse.ui.container.tabs.pack;

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
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class PackFragment extends Fragment implements IPackItemListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentPackBinding binding;

    private PackViewModel packViewModel;

    private ContainerViewModel containerViewModel;

    private PackAdapter packAdapter;

    private MessageManager messageManager;

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    public PackFragment() {

    }

    private void initViewModel() {
        packViewModel = ViewModelProviders.of(this,viewModelFactory).get(PackViewModel.class);
        containerViewModel = ViewModelProviders.of(this,viewModelFactory).get(ContainerViewModel.class);
        packViewModel.setPendingItemListener(this);
        getLifecycle().addObserver(packViewModel);
    }

    private void initView() {
        binding.packContainer.setLayoutManager(linearLayoutManager);
        messageManager = new MessageManager(getActivity());
        packViewModel.getPickerOrders().observe(this, result -> {
            switch (result.status) {
                case SUCCESS: {
                    packAdapter = new PackAdapter(result.data, packViewModel);
                    binding.packContainer.setAdapter(packAdapter);
                    break;
                }
                case HAVE_NO_INTERNET_CONNECTION: {
                    messageManager.showBottomMessage(getResources().getString(R.string.notification), getResources().getString(R.string.have_no_internet_connection));
                    break;
                }
            }
        });

        packViewModel.loadingOrders().observe(this, loading-> {
            if (loading) {
                binding.packContainer.setVisibility(View.GONE);
                binding.progress.setVisibility(View.VISIBLE);
            } else {
                binding.progress.setVisibility(View.GONE);
                binding.packContainer.setVisibility(View.VISIBLE);
            }
        });

        packViewModel.getCancelPickerOrder().observe(this, pickerOrderModelEvent -> {
            PickerOrderModel content = pickerOrderModelEvent.getContentIfNotHandled();
            if (content != null) {
                showCancelDialog(content);
            }
        });
    }

    private void showCancelDialog(PickerOrderModel pickerOrderModel) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.notification))
                .setMessage(getString(R.string.status_change_question))
                .setCancelable(false)
                .setPositiveButton(getActivity().getString(R.string.ok), (dialogInterface, which) -> {
                    packViewModel.cancelPickerOrder(pickerOrderModel);
                    dialogInterface.dismiss();
                })
                .setNegativeButton(getActivity().getString(R.string.cancel), (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                })
                .build();
        mBottomSheetDialog.show();
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
    public void selectPack(PickerOrderModel pickerOrderModel) {

    }

    @Override
    public void selectCancel(PickerOrderModel pickerOrderModel) {

    }
}