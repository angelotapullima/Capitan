package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Models.EquiposTorneo;

@Database(entities = {EquiposTorneo.class}, version = 1,exportSchema = false)
public abstract class EquiposTorneoRoomDataBase extends RoomDatabase {
    public abstract EquiposTorneoDao postInfoDao();

    private static EquiposTorneoRoomDataBase INSTANCE;


    static EquiposTorneoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EquiposTorneoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EquiposTorneoRoomDataBase.class, "equipos_torneo_database")
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
                    new EquiposTorneoDbAsync(INSTANCE).execute();
                }
            };
}
