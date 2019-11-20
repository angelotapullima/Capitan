package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;

@Database(entities = {Retos.class}, version = 1)
public abstract class RetosRoomDataBase extends RoomDatabase {
    public abstract RetosDao postInfoDao();

    private static RetosRoomDataBase INSTANCE;


    static RetosRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RetosRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RetosRoomDataBase.class, "retos_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback =
            new Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new RetosDbAsync(INSTANCE).execute();
                }
            };
}
