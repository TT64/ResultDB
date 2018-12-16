package com.mk.mkfighterresultdb.di;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.mk.mkfighterresultdb.AppDatabase;
import com.mk.mkfighterresultdb.Constants;
import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;

import java.util.concurrent.Callable;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@Module
public class RoomModule {

    private AppDatabase appDatabase;

    public RoomModule(final Application mApplication) {
        appDatabase = Room.databaseBuilder(mApplication, AppDatabase.class, Constants.DB_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Completable.fromCallable(new Callable<Object>() {
                            @Override
                            public Object call() {
                                appDatabase.fighterDao().insertAll(Fighter.populateData());
                                return null;
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnCompleted(new Action0() {
                                    @Override
                                    public void call() {
                                    }
                                })
                                .doOnError(new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                    }
                                })
                                .subscribe();
                    }
                })
                .build();
    }

    @Singleton
    @Provides
    AppDatabase providesRoomDatabase() {
        return appDatabase;
    }

    @Singleton
    @Provides
    FighterDao providesFighterDao(AppDatabase appDatabase) {
        return appDatabase.fighterDao();
    }
}
