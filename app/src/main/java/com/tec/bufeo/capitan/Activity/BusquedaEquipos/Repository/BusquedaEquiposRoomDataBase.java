package com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;

@Database(entities = {BusquedaEquipos.class}, version = 1,exportSchema = false)
public abstract class BusquedaEquiposRoomDataBase extends RoomDatabase {
    public abstract BusquedaEquiposDao postInfoDao();

    private static BusquedaEquiposRoomDataBase INSTANCE;


    public static BusquedaEquiposRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BusquedaEquiposRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BusquedaEquiposRoomDataBase.class, "equipos_database")
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
                    new BusquedaEquiposDbAsync(INSTANCE).execute();
                }
            };
}
