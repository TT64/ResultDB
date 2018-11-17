package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.Result;

import java.util.List;

public class ShowResultPresenter extends BasePresenter<ShowResultActivityContract.View> implements ShowResultActivityContract.Presenter, ShowResultActivityContract.Model.onFinishListener {

    private ShowResultActivityContract.Model model;

    public ShowResultPresenter(ShowResultActivityContract.Model model){
        this.model = model;
    }

    @Override
    public void requestViewData(FighterDao fighterDao, int firstId, int secondId) {
        model.getResult(fighterDao, firstId, secondId,this);
    }

    @Override
    public void onFinishedResponseGetResultList(List<Result> results) {
        getView().setListToRecyclerView(results);
        getView().onResponseSuccessRequestFighterList();
    }

    @Override
    public void onFailureResponseGetResultList() {
        getView().onResponseFailureRequestFighterList();
    }
}
