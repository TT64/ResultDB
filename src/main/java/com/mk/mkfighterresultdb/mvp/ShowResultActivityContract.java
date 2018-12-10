package com.mk.mkfighterresultdb.mvp;

import com.mk.mkfighterresultdb.Fighter;
import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.Result;

import java.util.List;

public class ShowResultActivityContract {

    public interface Presenter extends MvpPresenter<View>{
        void requestViewData(FighterDao fighterDao, int firstId, int secondId);

        void requestFirstFighter(FighterDao fighterDao, int firstId);

        void requestSecondFighter(FighterDao fighterDao, int firstId);

        void deleteResult(long id, int adapterPosition, FighterDao fighterDao);

        void unsubscribeSubs();
    }

    public interface View extends MvpView{

        void setListToRecyclerView(List<Result> resultList);

        void onResponseSuccessRequestFighterList();

        void onResponseSuccessRequestFirstFighter(List<Fighter> fighterList);

        void onResponseSuccessRequestSecondFighter(List<Fighter> fighterList);

        void onResponseErrorRequestFirstFighter();

        void onResponseErrorRequestSecondFighter();

        void onResponseFailureRequestFighterList();

        void onResponseSuccessDeleteResult(int position);

        void onResponseFailureDeleteResult();

    }

    public interface Model{

        interface onFinishListener{

            void onFinishedResponseGetResultList(List<Result> results);

            void onFailureResponseGetResultList();

            void onFinishedResponseFirstFighter(List<Fighter> fighterList);

            void onFinishedResponseSecondFighter(List<Fighter> fighterList);

            void onFailuredResponseFirstFighter();

            void onFailureResponseSecondFighter();

            void onFinishedResponseDeleteResult(int position);

            void onFailureResponseDeleteResult();

        }

        void getResult(FighterDao fighterDao, int firstId, int secondId, onFinishListener onFinishListener);

        void getFirstFighter(FighterDao fighterDao, int id, onFinishListener onFinishListener);

        void getSecondFighter(FighterDao fighterDao, int id, onFinishListener onFinishListener);

        void deleteCurrentResult(FighterDao fighterDao, long id, int position, onFinishListener onFinishListener);

        void unsubscribe();

    }
}
