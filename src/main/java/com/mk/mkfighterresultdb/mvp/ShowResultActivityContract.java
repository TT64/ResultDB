package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.Result;

import java.util.List;

public class ShowResultActivityContract {

    public interface Presenter extends MvpPresenter<View>{
        void requestViewData(FighterDao fighterDao, int firstId, int secondId);
    }

    public interface View extends MvpView{

        void setListToRecyclerView(List<Result> resultList);

        void onResponseSuccessRequestFighterList();

        void onResponseFailureRequestFighterList();

    }

    public interface Model{

        public interface onFinishListener{

            void onFinishedResponseGetResultList(List<Result> results);

            void onFailureResponseGetResultList();

        }

        void getResult(FighterDao fighterDao, int firstId, int secondId, onFinishListener onFinishListener);

    }
}
