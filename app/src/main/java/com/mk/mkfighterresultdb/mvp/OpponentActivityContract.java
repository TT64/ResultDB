package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.db.Fighter;
import com.mk.mkfighterresultdb.db.FighterDao;

public class OpponentActivityContract {

    public interface Presenter extends MvpPresenter<View> {

        void requestOpponentList(long id, FighterDao fighterDao);

        void unsubscribeSubs();

    }

    public interface View extends MvpView {

        void onSuccessRequestOpponentListResponse();

        void onFailureRequestOpponentListResponse();

        void setListToRecyclerView(Fighter[] fighters);

    }

    public interface Model {

        interface onFinishedListener {
            void onFinishedGetOpponentListResponse(Fighter[] fighters);

            void onFailureGetOpponentListResponse();
        }

        void getOpponents(long id, FighterDao fighterDao, onFinishedListener onFinishedListener);

        void unsubscribe();
    }
}
