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

    @Insert(onConflict = REPLACE)
    void insertAll(Fighter... fighter);
}
