package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.Result;

public class AddResultActivityContract {

    public interface Presenter extends MvpPresenter<View>{

        boolean checkNumField(String numValue, int orderNumEd);

        boolean checkStringField(String stringValue);

        boolean checkDate(String dateValue);

        void addData(Result result, FighterDao fighterDao);
    }

    public interface View extends MvpView{

        void onSuccessAddDataResponse();

        void onFailureAddDataResponse();

        void onEmptyFiled();

        void onCheckNumFieldFailure(int orderNumEd);

        void onCheckStringFieldFailure();

        void onCheckDateFailure();

    }

    public interface Model{
        interface onFinishedListener{
            void onFinishAddDataResponse();

            void onFailureAddDataResponse();
        }

        void addDataToDb(Result result, FighterDao fighterDao, onFinishedListener onFinishedListener);
    }
}
