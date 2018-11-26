package com.mk.mkfighterresultdb.mvp;

import android.annotation.SuppressLint;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.Result;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ModelOperateResult implements ShowResultActivityContract.Model {

    @SuppressLint("CheckResult")
    @Override
    public void getResult(FighterDao fighterDao, int firstId, int secondId, final onFinishListener onFinishListener) {
        fighterDao.getResult(firstId, secondId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onFinishListener.onFailureResponseGetResultList();
                    }
                })
                .subscribe(new Consumer<List<Result>>() {
                    @Override
                    public void accept(List<Result> results) throws Exception {
                        onFinishListener.onFinishedResponseGetResultList(results);
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getFirstFighter(FighterDao fighterDao, int id, final onFinishListener onFinishListener) {
        fighterDao.getFighter((long)id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onFinishListener.onFailuredResponseFirstFighter();
                    }
                })
                .subscribe(new Consumer<List<Fighter>>() {
                    @Override
                    public void accept(List<Fighter> fighterList) throws Exception {
                        onFinishListener.onFinishedResponseFirstFighter(fighterList);
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSecondFighter(FighterDao fighterDao, int id, final onFinishListener onFinishListener) {
        fighterDao.getFighter((long)id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                           onFinishListener.onFailureResponseSecondFighter();
                    }
                })
                .subscribe(new Consumer<List<Fighter>>() {
                    @Override
                    public void accept(List<Fighter> fighterList) throws Exception {
                        onFinishListener.onFinishedResponseSecondFighter(fighterList);
                    }
                });
    }

    @Override
    public void deleteCurrentResult(final FighterDao fighterDao, final long id, final int position, final onFinishListener onFinishListener) {
        Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                fighterDao.deleteResult(id);
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        onFinishListener.onFinishedResponseDeleteResult(position);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onFinishListener.onFailureResponseDeleteResult();
                    }
                })
                .subscribe();
    }
}
