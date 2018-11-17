package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;

public class OpponentActivityContract {

    public interface Presenter extends MvpPresenter<View> {

        void requestOpponentList(long id, FighterDao fighterDao);

    }

    public interface View extends MvpView {

        void onSuccessRequestOpponentListResponse();

        void onFailureRequestOpponentListResponse();

        void setListToRecyclerView(Fighter[] fighters);

        }

    public interface Model{

        interface onFinishedListener{
            void onFinishedGetOpponentListResponse(Fighter[] fighters);

            void onFailureGetOpponentListResponse();
        }
        void getOpponents(long id, FighterDao fighterDao, onFinishedListener onFinishedListener);
    }
}
