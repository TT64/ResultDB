package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.mvp.OperateResultModel;
import com.mk.mkfighterresultdb.mvp.ShowResultActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
class ShowResultActivityModule {

    ShowResultActivityModule() {
    }

    @Provides
    @PresenterScope
    ShowResultActivityContract.Model provideModel() {
        return new OperateResultModel();
    }
}
