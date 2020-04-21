package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Models.Estadisticas;

@Database(entities = {Estadisticas.class}, version = 1,exportSchema = false)
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
                            .fallbackToDestructiveMigration ()
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
