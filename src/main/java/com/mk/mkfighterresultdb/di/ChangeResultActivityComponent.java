package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.ui.ChangeResultActivity;

import dagger.Component;

@PresenterScope
@Component(dependencies = AppComponent.class, modules = ChangeResultActivityModule.class)
public interface ChangeResultActivityComponent {
    void inject(ChangeResultActivity changeResultActivity);
}
