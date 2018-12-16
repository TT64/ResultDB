package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.FighterDao;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChangeResultModel implements ChangeResultContract.Model {
    @Override
    public void changeCurrentResult(final FighterDao fighterDao, final long id, final double firstFighterMatchWinner, final double secondFighterMatchWinner, final double firstRoundWinner, final double secondRoundWinner,
                                    final double fatality, final double brutality, final double withoutSpecialFinish, final double score, final String matchCourse, final String recordDate,
                                    final onFinishedListener onFinishedListener) {
        Completable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                fighterDao.changeResult(id, firstFighterMatchWinner, secondFighterMatchWinner, firstRoundWinner, secondRoundWinner, fatality, brutality, withoutSpecialFinish, score, matchCourse, recordDate);
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onFinishedListener.onFinishFailureChangeResult();
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        onFinishedListener.onFinishSuccessChangeResult();
                    }
                })
                .subscribe();
    }
}
