package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Models.Estadisticas;

@Database(entities = {Estadisticas.class}, version = 1)
public abstract class EstadisticasRoomDataBase extends RoomDatabase {
    public abstract EstadisticasDao postInfoDao();

    private static EstadisticasRoomDataBase INSTANCE;


    static EstadisticasRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EstadisticasRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EstadisticasRoomDataBase.class, "estadisticas_database")
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
                    new EstadisticasDbAsync(INSTANCE).execute();
                }
            };
}
