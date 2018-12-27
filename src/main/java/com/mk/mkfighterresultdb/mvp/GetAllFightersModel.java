package com.mk.mkfighterresultdb.mvp;

import android.annotation.SuppressLint;

import com.mk.mkfighterresultdb.db.Fighter;
import com.mk.mkfighterresultdb.db.FighterDao;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class GetAllFightersModel implements FighterActivityContract.Model {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("CheckResult")
    @Override
    public void getAllFighters(FighterDao fighterDao, final onFinishedListener onFinishedListener) {
        compositeDisposable.add(fighterDao.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onFinishedListener.onFailureResponseGetFighterList();
                    }
                })
                .subscribe(new Consumer<Fighter[]>() {
                    @Override
                    public void accept(final Fighter[] fighters) throws Exception {
                        if (fighters != null) {
                            onFinishedListener.onFinishedResponseGetFighterList(fighters);
                        }
                    }
                }));
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
    }
}
