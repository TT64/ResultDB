package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.Result;

public class AddResultActivityContract {

    public interface Presenter extends MvpPresenter<View>{

        boolean checkNumField(String numValue, int orderNumEd);

        boolean checkStringField(String stringValue);

        void addData(Result result, FighterDao fighterDao);
    }

    public interface View extends MvpView{

        void onSuccessAddDataResponse();

        void onFaiilureAddDataResponse();

        void onEmptyFiled();

        void onCheckNumFieldFailure(int orderNumEd);

        void onCheckStringFieldFailure();

        void onFirstFighterMatchWinnerEdEmptyField();

        void onSecondFighterMatchWinnerEdEmptyField();

        void onFirstRoundWinnerEdEmptyField();

        void onSecondRoundWinnerEdEmptyField();

        void onFatalityEdEmptyField();

        void onBrutalityEdEmptyField();

        void onWithoutSpecialFinishEdEmptyField();

        void onScoreEdEmptyField();

        void onMatchCourseEdEmptyField();
    }

    public interface Model{
        public interface onFinishedListener{
            void onFinishAddDataResponse();

            void onFailureAddDataResponse();
        }

        void addDataToDb(
                Result result,
                //int idFirstFighter, int idSecondFighter, int firstFighterMatchWinner, int secondFighterMatchWinner, int firstRoundWinner,
                  //       int secondRoundWinner, int fatality, int brutality, int withoutSpecialFinish,
                //         int score, String matchCourse,
                FighterDao fighterDao, onFinishedListener onFinishedListener);
    }
}
