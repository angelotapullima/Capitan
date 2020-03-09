package com.tec.bufeo.capitan.Activity.Negocios.Repository.Canchas;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.Activity.Negocios.Model.Canchas;


@Database(entities = {Canchas.class}, version = 1,exportSchema = false)
public abstract class CanchasRoomDataBase extends RoomDatabase {
    public abstract CanchasDao postInfoDao();

    private static CanchasRoomDataBase INSTANCE;


    public static CanchasRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CanchasRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CanchasRoomDataBase.class, "canchas_database")
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
                    new CanchasDbAsync(INSTANCE).execute();
                }
            };
}
