package com.patstudio.warehouse.ui.container.tabs.pick;

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
import com.patstudio.warehouse.databinding.FragmentPickBinding;
import com.patstudio.warehouse.ui.container.ContainerViewModel;
import com.patstudio.warehouse.util.MessageManager;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.patstudio.warehouse.data.models.Resource.Status.HAVE_NO_INTERNET_CONNECTION;
import static com.patstudio.warehouse.data.models.Resource.Status.SUCCESS;


public class PickFragment extends Fragment implements IPickItemListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentPickBinding binding;

    private PickViewModel pickViewModel;

    private ContainerViewModel containerViewModel;

    private PickAdapter pickAdapter;

    private MessageManager messageManager;

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    public PickFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViewModel() {
        pickViewModel = ViewModelProviders.of(this,viewModelFactory).get(PickViewModel.class);
        containerViewModel = ViewModelProviders.of(getActivity(),viewModelFactory).get(ContainerViewModel.class);
        getLifecycle().addObserver(pickViewModel);
        pickViewModel.setPickItemListener(this);

        pickViewModel.getPickedResult().observe(this, pickerOrderModelEvent-> {
            PickerOrderModel pickerOrderModel = pickerOrderModelEvent.getContentIfNotHandled();
            if (pickerOrderModel != null)
                containerViewModel.pickingResult(pickerOrderModel);
        });
    }

    private void initView() {
        binding.pickContainer.setLayoutManager(linearLayoutManager);
        messageManager = new MessageManager(getActivity());
        pickViewModel.getPickerOrders().observe(this, result -> {
            Resource<List<PickerOrderModel>> content = result.getContentIfNotHandled();
            if (content != null) {
                switch (content.status) {
                    case SUCCESS: {
                        pickAdapter = new PickAdapter(content.data, pickViewModel);
                        binding.pickContainer.setAdapter(pickAdapter);
                        break;
                    }
                    case HAVE_NO_INTERNET_CONNECTION: {
                        messageManager.showBottomMessage(getResources().getString(R.string.notification), getResources().getString(R.string.have_no_internet_connection));
                        break;
                    }
                }
            }
        });
        pickViewModel.loadingOrders().observe(this, loading-> {
            if (loading) {
                binding.pickContainer.setVisibility(View.GONE);
                binding.progress.setVisibility(View.VISIBLE);
            } else {
                binding.progress.setVisibility(View.GONE);
                binding.pickContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pick, container, false);
        initViewModel();
        initView();
        return binding.getRoot();
    }

    @Override
    public void selectItem(PickerOrderModel pickerOrderModel) {
        containerViewModel.selectPickerOrderDetail(pickerOrderModel);
    }

    @Override
    public void selectStart(PickerOrderModel pickerOrderModel) {

    }
}