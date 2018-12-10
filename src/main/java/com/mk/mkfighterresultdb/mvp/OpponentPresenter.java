package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;

public class OpponentPresenter extends BasePresenter<OpponentActivityContract.View> implements OpponentActivityContract.Presenter, OpponentActivityContract.Model.onFinishedListener {

    private OpponentActivityContract.Model mModel;

    public OpponentPresenter(OpponentActivityContract.Model model) {
        this.mModel = model;
    }

    @Override
    public void requestOpponentList(long id, FighterDao fighterDao) {
        mModel.getOpponents(id, fighterDao, this);
    }

    @Override
    public void unsubscribeSubs() {
        mModel.unsubscribe();
    }

    @Override
    public void onFinishedGetOpponentListResponse(Fighter[] opponents) {
        getView().onSuccessRequestOpponentListResponse();
        getView().setListToRecyclerView(opponents);
    }

    @Override
    public void onFailureGetOpponentListResponse() {
        getView().onFailureRequestOpponentListResponse();
    }
}
