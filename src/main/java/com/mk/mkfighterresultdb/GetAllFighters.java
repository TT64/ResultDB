package com.mk.mkfighterresultdb;

import android.annotation.SuppressLint;

import com.mk.mkfighterresultdb.mvp.FighterActivityContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class GetAllFighters implements FighterActivityContract.Model {

    @SuppressLint("CheckResult")
    @Override
    public void getAllFighters(FighterDao fighterDao, final onFinishedListener onFinishedListener) {
        fighterDao.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Fighter[]>() {
                    @Override
                    public void accept(final Fighter[] fighters) throws Exception {
                        if (fighters != null) {
                            onFinishedListener.onFinishedResponseGetFighterList(fighters);
                        } else
                            onFinishedListener.onFailureResponseGetFighterList();
                    }
                });
    }
}
