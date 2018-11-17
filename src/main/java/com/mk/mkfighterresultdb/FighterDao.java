package com.mk.mkfighterresultdb;

import android.arch.persistence.room.Dao;
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

    @Query("SELECT * FROM result WHERE idFirstFighter = :idFirst AND idSecondFighter = :idSecond")
    Flowable<List<Result>> getResult(int idFirst, int idSecond);

    @Insert(onConflict = REPLACE)
    void insertResultData(Result result);

    @Insert(onConflict = REPLACE)
    void insertAll(Fighter... fighter);
}
