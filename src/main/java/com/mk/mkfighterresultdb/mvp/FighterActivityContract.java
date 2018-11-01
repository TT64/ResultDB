package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;

public class FighterActivityContract {

    public interface Presenter extends MvpPresenter<View> {

        void requestFighterList(FighterDao fighterDao);

    }

    public interface View extends MvpView {

        void onResponseSuccessRequestFighterList();

        void onResponseFailureRequestFighterList();

        void setListToRecyclerView(Fighter[] fighters);
    }

    public interface Model{

        interface onFinishedListener{
            void onFinishedResponseGetFighterList(Fighter[] fighters);

            void onFailureResponseGetFighterList();
        }
        void getAllFighters(FighterDao fighterDao, onFinishedListener onFinishedListener);
    }
}
