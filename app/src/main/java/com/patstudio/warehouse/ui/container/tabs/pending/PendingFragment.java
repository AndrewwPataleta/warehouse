package com.patstudio.warehouse.ui.container.tabs.pending;

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
import com.patstudio.warehouse.data.models.Resource;
import com.patstudio.warehouse.databinding.FragmentPendingBinding;
import com.patstudio.warehouse.ui.container.ContainerViewModel;
import com.patstudio.warehouse.util.MessageManager;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class PendingFragment extends Fragment implements IPendingItemListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentPendingBinding binding;

    private PendingViewModel pendingViewModel;

    private ContainerViewModel containerViewModel;

    private PendingAdapter pendingAdapter;

    private MessageManager messageManager;

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    public PendingFragment() {
        // Required empty public constructor
    }

    private void initViewModel() {
        pendingViewModel = ViewModelProviders.of(this,viewModelFactory).get(PendingViewModel.class);
        containerViewModel = ViewModelProviders.of(this,viewModelFactory).get(ContainerViewModel.class);
        pendingViewModel.setPendingItemListener(this);
        getLifecycle().addObserver(pendingViewModel);
    }

    private void initView() {
        binding.pendingContainer.setLayoutManager(linearLayoutManager);
        messageManager = new MessageManager(getActivity());
        pendingViewModel.getPickerOrders().observe(this, result -> {
            Resource<List<PickerOrderModel>> content = result.getContentIfNotHandled();
            if (content != null) {
                switch (content.status) {
                    case SUCCESS: {
                        pendingAdapter = new PendingAdapter(content.data, pendingViewModel);
                        binding.pendingContainer.setAdapter(pendingAdapter);
                        break;
                    }
                    case HAVE_NO_INTERNET_CONNECTION: {
                        messageManager.showBottomMessage(getResources().getString(R.string.notification), getResources().getString(R.string.have_no_internet_connection));
                        break;
                    }
                }
            }
        });
        pendingViewModel.loadingOrders().observe(this, loading-> {
            if (loading) {
                binding.pendingContainer.setVisibility(View.GONE);
                binding.progress.setVisibility(View.VISIBLE);
            } else {
                binding.progress.setVisibility(View.GONE);
                binding.pendingContainer.setVisibility(View.VISIBLE);
            }
        });

        pendingViewModel.getCancelPickerOrder().observe(this, eventCancelDialog -> {
            PickerOrderModel content = eventCancelDialog.getContentIfNotHandled();
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
                    pendingViewModel.cancelPickerOrder(pickerOrderModel);
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
                inflater, R.layout.fragment_pending, container, false);
        initViewModel();
        initView();
        return binding.getRoot();
    }

    @Override
    public void selectItem(PickerOrderModel pickerOrderModel) {
        containerViewModel.selectPickerOrderDetail(pickerOrderModel);
    }

    @Override
    public void selectContinue(PickerOrderModel pickerOrderModel) {

    }

    @Override
    public void selectCancel(PickerOrderModel pickerOrderModel) {

    }
}