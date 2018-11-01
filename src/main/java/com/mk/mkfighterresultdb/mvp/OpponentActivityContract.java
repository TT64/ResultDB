package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;

public class OpponentActivityContract {

    public interface Presenter extends MvpPresenter<View> {

        void requestOpponentList(long id, FighterDao fighterDao);

    }

    public interface View extends MvpView {

        void onResponseSuccessRequestOpponentList();

        void onResponseFailureRequestOpponentList();

        void setListToRecyclerView(Fighter[] fighters);
    }

    public interface Model{

        interface onFinishedListener{
            void onFinishedResponseGetOpponentList(Fighter[] fighters);

            void onFailureResponseGetOpponentList();
        }
        void getOpponents(long id, FighterDao fighterDao, onFinishedListener onFinishedListener);
    }
}
