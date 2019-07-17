package com.mk.mkfighterresultdb.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Fighter.class, Result.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FighterDao fighterDao();
}
