package com.patstudio.warehouse.ui.container;


import com.patstudio.warehouse.di.scopes.ActivityScoped;
import com.patstudio.warehouse.di.scopes.FragmentScoped;
import com.patstudio.warehouse.ui.container.boarder.BoarderFragment;
import com.patstudio.warehouse.ui.container.menu.MenuFragment;
import com.patstudio.warehouse.ui.container.picking.PickingOrderFragment;
import com.patstudio.warehouse.ui.container.settings.SettingsFragment;
import com.patstudio.warehouse.ui.container.tabs.done.DoneFragment;
import com.patstudio.warehouse.ui.container.tabs.pack.PackFragment;
import com.patstudio.warehouse.ui.container.tabs.pending.PendingFragment;
import com.patstudio.warehouse.ui.container.tabs.pick.PickFragment;
import com.patstudio.warehouse.util.providers.BaseResourceProvider;
import com.patstudio.warehouse.util.providers.ResourceProvider;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;


@Module(includes = {ContainerModule.MenuAbstractModule.class, ContainerModule.PickAbstractModule.class,
        ContainerModule.PendingAbstractModule.class, ContainerModule.BoarderAbstractModule.class,
        ContainerModule.PackAbstractModule.class, ContainerModule.DoneAbstractModule.class,
        ContainerModule.SettingsAbstractModule.class, ContainerModule.PickingOrderModule.class})

public class ContainerModule {

    @ActivityScoped
    @Provides
    BaseResourceProvider provideResourceProvider(ContainerActivity context) {
        return new ResourceProvider(context);
    }

    @Module
    public interface MenuAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        MenuFragment menuFragment();
    }

    @Module
    public interface PickAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        PickFragment pickFragment();
    }

    @Module
    public interface PendingAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        PendingFragment pendingFragment();
    }

    @Module
    public interface PackAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        PackFragment packFragment();
    }

    @Module
    public interface DoneAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        DoneFragment doneFragment();
    }


    @Module
    public interface BoarderAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        BoarderFragment boarderFragment();
    }


    @Module
    public interface SettingsAbstractModule {
        @FragmentScoped
        @ContributesAndroidInjector
        SettingsFragment settingsFragment();
    }

    @Module
    public interface PickingOrderModule {
        @FragmentScoped
        @ContributesAndroidInjector
        PickingOrderFragment pickingOrderFragment();
    }

}
