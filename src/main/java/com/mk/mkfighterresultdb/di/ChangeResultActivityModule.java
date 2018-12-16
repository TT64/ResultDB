package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.mvp.ChangeResultContract;
import com.mk.mkfighterresultdb.mvp.ChangeResultModel;

import dagger.Module;
import dagger.Provides;

@Module
class ChangeResultActivityModule {

    ChangeResultActivityModule() {
    }

    @Provides
    @PresenterScope
    ChangeResultContract.Model provideModel() {
        return new ChangeResultModel();
    }
}
