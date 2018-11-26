package com.mk.mkfighterresultdb.mvp;

import android.annotation.SuppressLint;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.mvp.OpponentActivityContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ModelGetAllOpponents implements OpponentActivityContract.Model {
    @SuppressLint("CheckResult")
    @Override
    public void getOpponents(long id, FighterDao fighterDao, final onFinishedListener onFinishedListener) {
        fighterDao.getOpponent(id)
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
                });
    }
}
