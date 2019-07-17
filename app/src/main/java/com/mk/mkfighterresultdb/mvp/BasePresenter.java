package com.mk.mkfighterresultdb.mvp;

public abstract class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    private T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void destroy() {
        view = null;
    }

    public T getView() {
        return view;
    }
}
