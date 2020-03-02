package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Detalle;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.DetalleTorneo;

@Database(entities = {DetalleTorneo.class}, version = 2,exportSchema = false)
public abstract class DetalleTorneoRoomDataBase extends RoomDatabase {
    public abstract DetalleTorneoDao postInfoDao();

    private static DetalleTorneoRoomDataBase INSTANCE;


    static DetalleTorneoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DetalleTorneoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DetalleTorneoRoomDataBase.class, "PublicacionesTorneo_database")
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
                    new DetalleTorneoDbAsync(INSTANCE).execute();
                }
            };
}
