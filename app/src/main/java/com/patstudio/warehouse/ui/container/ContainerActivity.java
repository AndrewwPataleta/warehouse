package com.patstudio.warehouse.ui.container;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.databinding.ActivityContainerBinding;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.ui.auth.LoginActivity;
import com.patstudio.warehouse.ui.container.boarder.BoarderFragment;
import com.patstudio.warehouse.ui.container.boarder.BoarderViewModel;
import com.patstudio.warehouse.ui.container.menu.MenuFragment;
import com.patstudio.warehouse.ui.container.picking.PickingOrderFragment;
import com.patstudio.warehouse.ui.container.settings.SettingsFragment;
import com.patstudio.warehouse.ui.container.tabs.TabsContainerFragment;
import com.patstudio.warehouse.util.MessageManager;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


@AppScoped
public class ContainerActivity extends DaggerAppCompatActivity {

    private ActivityContainerBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ContainerViewModel containerViewModel;
    private BoarderViewModel boarderViewModel;

    private MessageManager messageManager;

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu_24px);
        binding.toolbar.setNavigationIconTint(getResources().getColor(R.color.orange));
        binding.toolbar.setNavigationOnClickListener(view -> containerViewModel.selectMenu());
    }

    private void initNavigation() {
        containerViewModel.getScreen().observe(this, action -> {
            if(action != null){
                handleAction(action);
            }
        });

    }

    private void initView() {
        messageManager = new MessageManager(this);
        containerViewModel.getNeedShowSync().observe(this, needShow -> {
            if (needShow) {
                this.binding.syncContainer.setVisibility(View.VISIBLE);
            } else {
                this.binding.syncContainer.setVisibility(View.GONE);
            }
        });
        containerViewModel.getPickResult().observe(this, pickerOrderModel -> {
            BoarderFragment fragment = new BoarderFragment();
            Bundle bundle = new Bundle();

            bundle.putSerializable("pickerOrderModel", pickerOrderModel);
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.fragmentContainer.getId(), fragment, "BoarderFragment")
                    .commit();
        });
        containerViewModel.getMockBarcode().observe(this, mockbarcode -> {
            String mockBarcode = mockbarcode.getContentIfNotHandled();
            if (mockBarcode != null) {
                if (mockBarcode.length() == 13 ) {
                    boarderViewModel.setCurrentEAN13Barcode(mockBarcode);
                } else {
                    boarderViewModel.setCurrentCode128Barcode(mockBarcode);
                }
            }

        });
    }

    private void initScanBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.rfid.SCAN");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerViewModel = ViewModelProviders.of(this,viewModelFactory).get(ContainerViewModel.class);
        boarderViewModel = ViewModelProviders.of(this,viewModelFactory).get(BoarderViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_container);
        binding.setLifecycleOwner(this);
        binding.setContainerViewModel(containerViewModel);
        initToolbar();
        initView();
        initNavigation();
        initScanBroadcast();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void handleAction(@NonNull final ActionContainerScreenModel action) {

        switch (action.getValue()){
            case ActionContainerScreenModel.SHOW_MENU:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), new MenuFragment(), "MenuFragment")
                        .commit();
                break;
            case ActionContainerScreenModel.SHOW_TABS:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), new TabsContainerFragment(), "TabsContainerFragment")
                        .commit();
                break;
            case ActionContainerScreenModel.SHOW_SETTINGS:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), new SettingsFragment(), "SettingsFragment")
                        .commit();
                break;
            case ActionContainerScreenModel.SHOW_DETAIL: {
                BoarderFragment fragment = new BoarderFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", action.getItemId());
                fragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), fragment, "BoarderFragment")
                        .commit();
                break;
            }
            case ActionContainerScreenModel.SHOW_PICKING_ORDER: {
                PickingOrderFragment fragment = new PickingOrderFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(binding.fragmentContainer.getId(), fragment, "PickingOrderFragment")
                        .commit();
                break;
            }
            case ActionContainerScreenModel.SHOW_LOGIN: {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            }
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            byte[] data = intent.getByteArrayExtra("data");
            if (data != null) {
                try {
                    String barcode = new String(data);
                    if (barcode.length() == 13 ) {
                        boarderViewModel.setCurrentEAN13Barcode(barcode);
                    } else {
                        boarderViewModel.setCurrentCode128Barcode(barcode);
                    }
                    messageManager.showBottomMessage(getResources().getString(R.string.notification), barcode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };
}