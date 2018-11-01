package com.mk.mkfighterresultdb;

import android.annotation.SuppressLint;

import com.mk.mkfighterresultdb.mvp.OpponentActivityContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class GetAllOpponents implements OpponentActivityContract.Model {
    @SuppressLint("CheckResult")
    @Override
    public void getOpponents(long id, FighterDao fighterDao, final onFinishedListener onFinishedListener) {
        fighterDao.getOpponent(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Fighter[]>() {
                    @Override
                    public void accept(Fighter[] opponents) throws Exception {
                        if (opponents != null){
                            onFinishedListener.onFinishedResponseGetOpponentList(opponents);
                        }
                        else
                            onFinishedListener.onFailureResponseGetOpponentList();
                    }
                });
    }
}
