package com.mk.mkfighterresultdb.mvp;

import android.annotation.SuppressLint;

import com.mk.mkfighterresultdb.db.Fighter;
import com.mk.mkfighterresultdb.db.FighterDao;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class GetAllOpponentsModel implements OpponentActivityContract.Model {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("CheckResult")
    @Override
    public void getOpponents(final long id, final FighterDao fighterDao, final onFinishedListener onFinishedListener) {
        compositeDisposable.add(fighterDao.getOpponent(id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onFinishedListener.onFailureGetOpponentListResponse();
                    }
                })
                .subscribe(new Consumer<Fighter[]>() {
                    @Override
                    public void accept(Fighter[] opponents) throws Exception {
                        if (opponents != null) {
                            onFinishedListener.onFinishedGetOpponentListResponse(opponents);
                        }
                    }
                }));
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }
}
