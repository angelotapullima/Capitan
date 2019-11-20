package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;

@Database(entities = {Torneo.class}, version = 1)
public abstract class OtrosTorneosRoomDataBase extends RoomDatabase {
    public abstract OtrosTorneosDao postInfoDao();

    private static OtrosTorneosRoomDataBase INSTANCE;


    static OtrosTorneosRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OtrosTorneosRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OtrosTorneosRoomDataBase.class, "torneos_database")
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
                    new OtrosTorneosDbAsync(INSTANCE).execute();
                }
            };
}
