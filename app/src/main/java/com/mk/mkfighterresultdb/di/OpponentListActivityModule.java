package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.mvp.GetAllOpponentsModel;
import com.mk.mkfighterresultdb.mvp.OpponentActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
public class OpponentListActivityModule {

    OpponentListActivityModule() {
    }

    @Provides
    @PresenterScope
    public OpponentActivityContract.Model provideModel() {
        return new GetAllOpponentsModel();
    }

}
