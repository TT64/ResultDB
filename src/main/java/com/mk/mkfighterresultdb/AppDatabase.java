package com.mk.mkfighterresultdb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.Callable;

import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Database(entities = {Fighter.class, Result.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    static String TAG = "TAG";

    private static AppDatabase instance;

    public synchronized static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DB_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        Completable.fromCallable(new Callable<Object>() {
                            @Override
                            public Object call() {
                                getDatabase(context).fighterDao().insertAll(Fighter.populateData());
                                Log.d(TAG, "call: insert1");
                                return null;
                            }
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnCompleted(new Action0() {
                                    @Override
                                    public void call() {
                                        Log.d(TAG, "call: insert");
                                    }
                                })
                                .doOnError(new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.d(TAG, "call: " + throwable.getMessage());
                                    }
                                })
                                .subscribe();
                    }
                })
                .build();
    }

    public abstract FighterDao fighterDao();
}