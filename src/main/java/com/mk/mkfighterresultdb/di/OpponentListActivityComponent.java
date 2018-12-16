package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.OpponentActivity;

import dagger.Component;

@PresenterScope
@Component(dependencies = AppComponent.class, modules = OpponentListActivityModule.class)
public interface OpponentListActivityComponent {
    void inject(OpponentActivity opponentActivity);
}
