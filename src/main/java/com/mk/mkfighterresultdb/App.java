package com.mk.mkfighterresultdb;

import android.app.Application;

import com.mk.mkfighterresultdb.di.AppComponent;
import com.mk.mkfighterresultdb.di.DaggerAppComponent;
import com.mk.mkfighterresultdb.di.RoomModule;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        appComponent = DaggerAppComponent.builder()
                .roomModule(new RoomModule(this))
                .build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
