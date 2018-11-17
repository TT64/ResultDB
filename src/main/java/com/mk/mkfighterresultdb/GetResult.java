package com.mk.mkfighterresultdb;

import android.annotation.SuppressLint;

import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.mvp.ShowResultActivityContract;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class GetResult implements ShowResultActivityContract.Model {

    @SuppressLint("CheckResult")
    @Override
    public void getResult(FighterDao fighterDao, int firstId, int secondId, final onFinishListener onFinishListener) {
        fighterDao.getResult(firstId, secondId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Result>>() {
                    @Override
                    public void accept(List<Result> results) throws Exception {
                        if (results.size() >= 1)
                            onFinishListener.onFinishedResponseGetResultList(results);
                        else
                            onFinishListener.onFailureResponseGetResultList();
                    }
                });
    }
}
