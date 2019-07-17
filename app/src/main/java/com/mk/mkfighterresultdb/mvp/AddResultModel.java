package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.db.FighterDao;
import com.mk.mkfighterresultdb.db.Result;

import java.util.concurrent.Callable;

import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AddResultModel implements AddResultActivityContract.Model {
    @Override
    public void addDataToDb(final Result result, final FighterDao fighterDao, final onFinishedListener onFinishedListener) {
        Completable.fromCallable(new Callable<Object>() {
            @Override
            public Result call() throws Exception {
                fighterDao.insertResultData(result);
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        onFinishedListener.onFinishAddDataResponse();
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        onFinishedListener.onFailureAddDataResponse();
                    }
                })
                .subscribe();
    }
}
