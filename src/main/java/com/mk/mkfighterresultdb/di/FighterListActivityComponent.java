package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.ui.FighterListActivity;

import dagger.Component;

@PresenterScope
@Component(dependencies = AppComponent.class, modules = FighterListActivityModule.class)
public interface FighterListActivityComponent {
    void inject(FighterListActivity fighterListActivity);
}
