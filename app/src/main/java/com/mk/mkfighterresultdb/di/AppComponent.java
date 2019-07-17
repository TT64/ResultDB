package com.mk.mkfighterresultdb.di;

import com.mk.mkfighterresultdb.db.FighterDao;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RoomModule.class})
public interface AppComponent {

    FighterDao fighterDao();

}
