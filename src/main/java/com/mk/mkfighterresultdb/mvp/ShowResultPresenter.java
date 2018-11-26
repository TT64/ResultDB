package com.mk.mkfighterresultdb.mvp;

import android.util.Log;

import com.mk.mkfighterresultdb.Fighter;
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
    public void requestFirstFighter(FighterDao fighterDao, int firstId) {
        model.getFirstFighter(fighterDao, firstId, this);
    }

    @Override
    public void requestSecondFighter(FighterDao fighterDao, int firstId) {
        model.getSecondFighter(fighterDao, firstId, this);
    }

    @Override
    public void deleteResult(long id, int adapterPosition, FighterDao fighterDao) {
        model.deleteCurrentResult(fighterDao, id, adapterPosition, this);
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

    @Override
    public void onFinishedResponseFirstFighter(List<Fighter> fighterList) {
        getView().onResponseSuccessRequestFirstFighter(fighterList);
    }

    @Override
    public void onFinishedResponseSecondFighter(List<Fighter> fighterList) {
        getView().onResponseSuccessRequestSecondFighter(fighterList);
    }

    @Override
    public void onFailuredResponseFirstFighter() {
        getView().onResponseErrorRequestFirstFighter();
    }

    @Override
    public void onFailureResponseSecondFighter() {
        getView().onResponseErrorRequestSecondFighter();
    }

    @Override
    public void onFinishedResponseDeleteResult(int position) {
        getView().onResponseSuccessDeleteResult(position);
    }

    @Override
    public void onFailureResponseDeleteResult() {
        getView().onResponseFailureDeleteResult();
    }
}
