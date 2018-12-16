package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.ShowResultActivity;

import dagger.Component;

@PresenterScope
@Component(dependencies = AppComponent.class, modules = ShowResultActivityModule.class)
public interface ShowResultActivityComponent {
    void inject(ShowResultActivity showResultActivity);
}
