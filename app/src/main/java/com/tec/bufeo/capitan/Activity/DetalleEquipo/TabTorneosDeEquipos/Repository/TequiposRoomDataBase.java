package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository;

import android.content.Context;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Models.TorneosDeEquipos;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TorneosDeEquipos.class}, version = 2,exportSchema = false)
public abstract class TequiposRoomDataBase extends RoomDatabase {
    public abstract TequiposDao postInfoDao();

    private static TequiposRoomDataBase INSTANCE;


    public static TequiposRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TequiposRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TequiposRoomDataBase.class, "torneos_equipos_database")
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
                    new TequiposDbAsync(INSTANCE).execute();
                }
            };
}
