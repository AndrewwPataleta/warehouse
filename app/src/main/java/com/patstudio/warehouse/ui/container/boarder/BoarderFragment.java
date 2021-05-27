package com.patstudio.warehouse.ui.container.boarder;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.ItemModel;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.data.models.Resource;
import com.patstudio.warehouse.databinding.FragmentBoarderBinding;
import com.patstudio.warehouse.ui.container.ContainerViewModel;
import com.patstudio.warehouse.util.InputFilterMinMax;
import com.patstudio.warehouse.util.MessageManager;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class BoarderFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentBoarderBinding binding;

    private BoarderViewModel boarderViewModel;
    private ContainerViewModel containerViewModel;

    private BoarderAdapter boarderAdapter;

    private MessageManager messageManager;

    private Bundle bundle;

    private AlertDialog materialDialog;



    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

    public BoarderFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViewModel() {
        boarderViewModel = ViewModelProviders.of(this,viewModelFactory).get(BoarderViewModel.class);
        containerViewModel = ViewModelProviders.of(this,viewModelFactory).get(ContainerViewModel.class);
        getLifecycle().addObserver(boarderViewModel);
        boarderViewModel.getPickerOrders().observe(this, result -> {
            Resource<PickerOrderModel> content = result.getContentIfNotHandled();
            if (content != null) {
                switch (content.status) {
                    case SUCCESS: {
                        binding.contentContainer.setVisibility(View.VISIBLE);
                        boarderAdapter = new BoarderAdapter(content.data.getOrders().get(0).getItems(), boarderViewModel);
                        binding.itemsContainer.setLayoutManager(linearLayoutManager);
                        binding.itemsContainer.setAdapter(boarderAdapter);
                        break;
                    }
                    case HAVE_NO_INTERNET_CONNECTION: {
                        messageManager.showBottomMessage(getResources().getString(R.string.notification), getResources().getString(R.string.have_no_internet_connection));
                        break;
                    }
                }
            }
        });

        binding.itemsContainer.addOnScrollListener ( new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {

                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    boarderViewModel.setCurrentItemPosition(position);
                }

            }
        });
        boarderViewModel.loadingOrders().observe(this, loading-> {
            if (loading) {

                binding.contentContainer.setVisibility(View.GONE);
                binding.progress.setVisibility(View.VISIBLE);
            } else {
                binding.progress.setVisibility(View.GONE);
                binding.contentContainer.setVisibility(View.VISIBLE);
            }
        });
        boarderViewModel.needUpdate().observe(this, booleanEvent-> {
            Boolean needUpdate = booleanEvent.getContentIfNotHandled();
            if (needUpdate != null && boarderAdapter != null) {
                boarderAdapter.notifyDataSetChanged();
            }
        });
        boarderViewModel.getNeedUpdateItemQuantityPicked().observe(this, needUpdate-> {
            ItemModel itemModel = needUpdate.getContentIfNotHandled();
            if (itemModel != null) {
                View view = getLayoutInflater().inflate(R.layout.view_item_count, null);
                ((EditText) view.findViewById(R.id.itemEdit)).setText(String.valueOf(itemModel.getQuantity_picked()));
                ((EditText) view.findViewById(R.id.itemEdit)).setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(itemModel.getQuantity()))});
                materialDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                        .setView(view)
                        .setCancelable(false)
                        .create();
                materialDialog.setOnShowListener(dialogInterface -> {
                    view.findViewById(R.id.cancel).setOnClickListener(view12 -> dialogInterface.dismiss());
                    view.findViewById(R.id.done).setOnClickListener(view1 -> {
                        dialogInterface.dismiss();
                        boarderViewModel.updateItemModelCount(itemModel,((EditText) view.findViewById(R.id.itemEdit)).getText().toString());
                    });
                });
                materialDialog.show();
            }
        });

        boarderViewModel.getNeedUpdateItemBinCount().observe(this, needUpdate-> {
            ItemModel itemModel = needUpdate.getContentIfNotHandled();
            if (itemModel != null) {
                View view = getLayoutInflater().inflate(R.layout.view_item_count_bin, null);
                ((EditText) view.findViewById(R.id.itemEdit)).setText(String.valueOf(itemModel.getBin_count()));
                ((EditText) view.findViewById(R.id.itemEdit)).setFilters(new InputFilter[]{ new InputFilterMinMax("0", String.valueOf(itemModel.getQuantity()))});
                materialDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()))
                        .setView(view)
                        .setCancelable(false)
                        .create();
                materialDialog.setOnShowListener(dialogInterface -> {
                    view.findViewById(R.id.cancel).setOnClickListener(view12 -> dialogInterface.dismiss());
                    view.findViewById(R.id.done).setOnClickListener(view1 -> {
                        dialogInterface.dismiss();
                        boarderViewModel.updateItemModelCountBin(itemModel,((EditText) view.findViewById(R.id.itemEdit)).getText().toString());
                    });
                });
                materialDialog.show();
            }
        });
        boarderViewModel.setPickResult((PickerOrderModel) bundle.getSerializable("pickerOrderModel"));

        boarderViewModel.getScreen().observe(this, action -> {
            ActionBoarderScreenModel actionBoarderScreenModel = action.getContentIfNotHandled();
            if(actionBoarderScreenModel != null){
                handleAction(actionBoarderScreenModel);
            }
        });
        binding.setViewModel(boarderViewModel);
    }

    private void handleAction(@NonNull final ActionBoarderScreenModel action) {

        switch (action.getValue()){
            case ActionBoarderScreenModel.SHOW_TABS:
                containerViewModel.selectMenuPickingPacking(getView());
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        messageManager = new MessageManager(getActivity());
        containerViewModel.boarderReadyToScan();
//        binding.doneButton.setOnClickListener(view -> containerViewModel.needCode128());
//        binding.cancelButton.setOnClickListener(view -> containerViewModel.needEan13());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        containerViewModel.boarderStopToScan();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_boarder, container, false);
        bundle = getArguments();
        initViewModel();
        initView();
        return binding.getRoot();
    }
}