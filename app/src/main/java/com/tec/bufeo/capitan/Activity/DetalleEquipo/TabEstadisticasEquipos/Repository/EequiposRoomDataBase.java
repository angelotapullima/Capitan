package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository;

import android.content.Context;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Models.EstadisticasDeEquipos;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {EstadisticasDeEquipos.class}, version = 1,exportSchema = false)
public abstract class EequiposRoomDataBase extends RoomDatabase {
    public abstract EequiposDao postInfoDao();

    private static EequiposRoomDataBase INSTANCE;


    static EequiposRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EequiposRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EequiposRoomDataBase.class, "estadisticas_equipos_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
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
                    new EequiposDbAsync(INSTANCE).execute();
                }
            };
}
