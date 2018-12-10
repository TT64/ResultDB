package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.FighterDao;

public class ChangeResultContract {

    public interface Presenter extends MvpPresenter<View>{
        boolean checkNumField(String numValue, int orderNumEd);

        boolean checkStringField(String stringValue);

        boolean checkDate(String dateValue);

        void requestChangeResult(FighterDao fighterDao, long id, double firstFighterMatchWinner, double secondFighterMatchWinner, double firstRoundWinner,
                                 double secondRoundWinner, double fatality, double brutality, double withoutSpecialFinish,
                                 double score, String matchCourse, String recordDate);
    }

    public interface View extends MvpView{
        void onEmptyFiled();

        void onCheckNumFieldFailure(int orderNumEd);

        void onCheckStringFieldFailure();

        void onCheckDateFailure();

        void onSuccessChangeResult();

        void onErrorChangeResult();
    }

    public interface Model{
        interface onFinishedListener{
            void onFinishSuccessChangeResult();

            void onFinishFailureChangeResult();
        }

        void changeCurrentResult(FighterDao fighterDao, long id, double firstFighterMatchWinner, double secondFighterMatchWinner, double firstRoundWinner,
                                 double secondRoundWinner, double fatality, double brutality, double withoutSpecialFinish,
                                 double score, String matchCourse, String recordDate, onFinishedListener onFinishedListener);
    }
}
