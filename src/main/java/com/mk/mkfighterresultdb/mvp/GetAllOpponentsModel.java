package com.mk.mkfighterresultdb.mvp;

import android.annotation.SuppressLint;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;

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
                .subscribe(new Consumer<Fighter[]>() {
                    @Override
                    public void accept(Fighter[] opponents) throws Exception {
                        if (opponents != null){
                            onFinishedListener.onFinishedGetOpponentListResponse(opponents);
                        }
                        else
                            onFinishedListener.onFailureGetOpponentListResponse();
                    }
                }));
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }
}
