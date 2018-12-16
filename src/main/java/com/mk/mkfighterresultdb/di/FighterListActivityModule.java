package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.mvp.FighterActivityContract;
import com.mk.mkfighterresultdb.mvp.GetAllFightersModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FighterListActivityModule {

    FighterListActivityModule() {
    }

    @Provides
    @PresenterScope
    public FighterActivityContract.Model provideModel() {
        return new GetAllFightersModel();
    }
}
