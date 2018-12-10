package com.mk.mkfighterresultdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import rx.Completable;
import rx.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FighterDao {

    @Query("SELECT * FROM fighter")
    //Flowable<List<Fighter>> getAll();
    Flowable<Fighter[]> getAll();

    @Query("SELECT * FROM fighter WHERE id <> :id")
    Flowable<Fighter[]> getOpponent(long id);

    @Query("SELECT * FROM fighter WHERE id = :id")
    Flowable<List<Fighter>> getFighter(long id);

    @Query("SELECT * FROM result WHERE idFirstFighter = :idFirst AND idSecondFighter = :idSecond order by recordDate")
    Flowable<List<Result>> getResult(int idFirst, int idSecond);

    @Query("DELETE FROM result WHERE id = :id")
    void deleteResult(long id);

    @Query("UPDATE result SET firstFighterMatchWinner = :firstFighterMatchWinner, secondFighterMatchWinner = :secondFighterMatchWinner," +
            "firstRoundWinner = :firstRoundWinner, secondRoundWinner = :secondRoundWinner, fatality = :fatality," +
            "brutality =:brutality, withoutSpecialFinish = :withoutSpecialFinish, score = :score," +
            "matchCourse =:matchCourse, recordDate =:recordDate WHERE id = :id")
    void changeResult(long id, double firstFighterMatchWinner, double secondFighterMatchWinner, double firstRoundWinner,
                      double secondRoundWinner, double fatality, double brutality, double withoutSpecialFinish,
                      double score, String matchCourse, String recordDate);

    @Insert(onConflict = REPLACE)
    void insertResultData(Result result);

    @Insert(onConflict = REPLACE)
    void insertAll(Fighter... fighter);

}
