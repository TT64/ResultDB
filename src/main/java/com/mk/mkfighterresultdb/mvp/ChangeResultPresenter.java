package com.mk.mkfighterresultdb.mvp;

import android.text.TextUtils;

import com.mk.mkfighterresultdb.FighterDao;


public class ChangeResultPresenter extends BasePresenter<ChangeResultContract.View> implements ChangeResultContract.Presenter, ChangeResultContract.Model.onFinishedListener {

    private ChangeResultContract.Model mModel;

    public ChangeResultPresenter(ChangeResultContract.Model model){
        this.mModel = model;
    }

    @Override
    public boolean checkNumField(String numValue, int orderNumEd) {
        boolean result = false;
        if (!TextUtils.isEmpty(numValue.trim())){
            if (AddResultPresenter.isValidNumValue(numValue))
                result = true;
            else {
                result = false;
                getView().onCheckNumFieldFailure(orderNumEd);
            }
        }
        else {
            getView().onEmptyFiled();
        }
        return result;
    }

    @Override
    public boolean checkStringField(String stringValue) {
        boolean result = false;
        if (!TextUtils.isEmpty(stringValue.trim())){
            if (AddResultPresenter.isValidStringValue(stringValue))
                result = true;
            else {
                result = false;
                getView().onCheckStringFieldFailure();
            }
        }
        else {
            getView().onEmptyFiled();
        }
        return result;
    }

    @Override
    public boolean checkDate(String dateValue) {
        boolean result;
        if (!TextUtils.isEmpty(dateValue))
            result = true;
        else {
            result = false;
            getView().onCheckDateFailure();
        }
        return result;
    }

    @Override
    public void requestChangeResult(FighterDao fighterDao, long id, double firstFighterMatchWinner,
                                    double secondFighterMatchWinner, double firstRoundWinner, double secondRoundWinner,
                                    double fatality, double brutality, double withoutSpecialFinish, double score, String matchCourse, String recordDate) {
        mModel.changeCurrentResult(fighterDao, id, firstFighterMatchWinner, secondFighterMatchWinner, firstRoundWinner, secondRoundWinner,
        fatality, brutality, withoutSpecialFinish, score, matchCourse, recordDate,this);
    }

    @Override
    public void onFinishSuccessChangeResult() {
        getView().onSuccessChangeResult();
    }

    @Override
    public void onFinishFailureChangeResult() {
        getView().onErrorChangeResult();
    }
}
