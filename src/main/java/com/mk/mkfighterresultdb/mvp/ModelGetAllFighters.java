package com.mk.mkfighterresultdb.mvp;

import android.annotation.SuppressLint;
import android.util.Log;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.mvp.FighterActivityContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ModelGetAllFighters implements FighterActivityContract.Model {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("CheckResult")
    @Override
    public void getAllFighters(FighterDao fighterDao, final onFinishedListener onFinishedListener) {
        Log.d("da","dadada");
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
