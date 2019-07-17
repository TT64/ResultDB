package com.mk.mkfighterresultdb;

import android.app.Application;

import com.mk.mkfighterresultdb.di.AppComponent;
import com.mk.mkfighterresultdb.di.DaggerAppComponent;
import com.mk.mkfighterresultdb.di.RoomModule;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .roomModule(new RoomModule(this))
                .build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
