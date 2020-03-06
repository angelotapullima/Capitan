package com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Repository;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Models.Goleadores;

@Database(entities = {Goleadores.class}, version = 1,exportSchema = false)
public abstract class GoleadoresRoomDatabase extends RoomDatabase {

    public abstract GoleadoresDao postInfoDao();

    private static GoleadoresRoomDatabase INSTANCE;


    static GoleadoresRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GoleadoresRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GoleadoresRoomDatabase.class, "goleadores_database")
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
                    new GoleadoresDbAsync(INSTANCE).execute();
                }
            };
}
