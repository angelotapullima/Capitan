package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.PublicacionesTorneo;

import androidx.annotation.NonNull;





@Database(entities = {PublicacionesTorneo.class}, version = 1)
public abstract class PublicacionesTorneoRoomDataBase extends RoomDatabase {
    public abstract PublicacionesTorneoDao postInfoDao();

    private static PublicacionesTorneoRoomDataBase INSTANCE;


    static PublicacionesTorneoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PublicacionesTorneoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PublicacionesTorneoRoomDataBase.class, "PublicacionesTorneo_database")
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
                    new PublicacionesTorneoDbAsync(INSTANCE).execute();
                }
            };
}
