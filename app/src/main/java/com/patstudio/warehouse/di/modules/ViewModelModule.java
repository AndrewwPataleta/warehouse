package com.patstudio.warehouse.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.patstudio.warehouse.di.ViewModelKey;
import com.patstudio.warehouse.ui.ViewModelFactory;
import com.patstudio.warehouse.ui.auth.LoginViewModel;
import com.patstudio.warehouse.ui.container.ContainerViewModel;
import com.patstudio.warehouse.ui.container.boarder.BoarderViewModel;
import com.patstudio.warehouse.ui.container.picking.PickingOrderViewModel;
import com.patstudio.warehouse.ui.container.settings.SettingsViewModel;
import com.patstudio.warehouse.ui.container.tabs.done.DoneViewModel;
import com.patstudio.warehouse.ui.container.tabs.pack.PackViewModel;
import com.patstudio.warehouse.ui.container.tabs.pending.PendingViewModel;
import com.patstudio.warehouse.ui.container.tabs.pick.PickViewModel;
import com.patstudio.warehouse.ui.splash.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory modelProvider);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel ratesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContainerViewModel.class)
    abstract ViewModel bindContainerViewModel(ContainerViewModel containerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PickViewModel.class)
    abstract ViewModel bindPickViewModel(PickViewModel pickViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PendingViewModel.class)
    abstract ViewModel bindPendingViewModel(PendingViewModel pickViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PackViewModel.class)
    abstract ViewModel bindPackViewModel(PackViewModel packViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DoneViewModel.class)
    abstract ViewModel bindDoneViewModel(DoneViewModel doneViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BoarderViewModel.class)
    abstract ViewModel bindBoarderViewModel(BoarderViewModel boarderViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel settingsViewModel(SettingsViewModel settingsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PickingOrderViewModel.class)
    abstract ViewModel pickingOrderViewModel(PickingOrderViewModel pickingOrderViewModel);

}
