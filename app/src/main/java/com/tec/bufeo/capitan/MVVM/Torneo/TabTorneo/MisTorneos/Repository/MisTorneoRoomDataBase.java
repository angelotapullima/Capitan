package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;

@Database(entities = {Torneo.class}, version = 1)
public abstract class MisTorneoRoomDataBase extends RoomDatabase {
    public abstract MisTorneoDao postInfoDao();

    private static MisTorneoRoomDataBase INSTANCE;


    static MisTorneoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MisTorneoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MisTorneoRoomDataBase.class, "torneos_database")
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
                    new MisTorneoDbAsync(INSTANCE).execute();
                }
            };
}
