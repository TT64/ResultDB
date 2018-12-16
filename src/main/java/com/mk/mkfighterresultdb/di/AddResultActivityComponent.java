package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.AddResultActivity;

import dagger.Component;

@PresenterScope
@Component(dependencies = AppComponent.class, modules = AddResultActivityModule.class)
public interface AddResultActivityComponent {
    void inject(AddResultActivity addResultActivity);
}
