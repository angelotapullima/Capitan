package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository;

import android.content.Context;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Models.EquiposTorneo;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Models.RegistroEquiposTorneo;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {RegistroEquiposTorneo.class}, version = 1,exportSchema = false)
public abstract class RegistroEquiposTorneoRoomDataBase extends RoomDatabase {
    public abstract RegistroEquiposTorneoDao postInfoDao();

    private static RegistroEquiposTorneoRoomDataBase INSTANCE;


    static RegistroEquiposTorneoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RegistroEquiposTorneoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RegistroEquiposTorneoRoomDataBase.class, "registro_equipos_torneo_database")
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
                    new RegistroEquiposTorneoDbAsync(INSTANCE).execute();
                }
            };
}
