package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.db.Fighter;
import com.mk.mkfighterresultdb.db.FighterDao;

import javax.inject.Inject;

public class FighterActivityPresenter extends BasePresenter<FighterActivityContract.View> implements FighterActivityContract.Presenter, FighterActivityContract.Model.onFinishedListener {

    private FighterActivityContract.Model mModel;

    @Inject
    FighterActivityPresenter(FighterActivityContract.Model model) {
        this.mModel = model;
    }

    @Override
    public void requestFighterList(FighterDao fighterDao) {
        mModel.getAllFighters(fighterDao, this);
    }

    @Override
    public void unsubscribeSubs() {
        mModel.unsubscribe();
    }

    @Override
    public void onFinishedResponseGetFighterList(Fighter[] fighters) {
        getView().onResponseSuccessRequestFighterList();
        getView().setListToRecyclerView(fighters);
    }

    @Override
    public void onFailureResponseGetFighterList() {
        getView().onResponseFailureRequestFighterList();
    }

}
