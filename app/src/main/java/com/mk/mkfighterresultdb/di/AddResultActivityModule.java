package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.mvp.AddResultActivityContract;
import com.mk.mkfighterresultdb.mvp.AddResultModel;

import dagger.Module;
import dagger.Provides;

@Module
class AddResultActivityModule {

    AddResultActivityModule() {
    }

    @Provides
    @PresenterScope
    AddResultActivityContract.Model provideModel() {
        return new AddResultModel();
    }
}
