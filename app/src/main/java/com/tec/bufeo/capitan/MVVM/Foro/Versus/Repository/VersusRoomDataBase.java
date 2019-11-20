package com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Foro.Versus.Models.Versus;

@Database(entities = {Versus.class}, version = 1)
public abstract class VersusRoomDataBase extends RoomDatabase {
    public abstract VersusDao postInfoDao();

    private static VersusRoomDataBase INSTANCE;


    static VersusRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VersusRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VersusRoomDataBase.class, "lista_versus_database")
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
                    new VersusDbAsync(INSTANCE).execute();
                }
            };
}
