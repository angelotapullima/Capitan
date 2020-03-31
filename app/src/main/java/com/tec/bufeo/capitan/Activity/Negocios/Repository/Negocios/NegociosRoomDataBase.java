package com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;


@Database(entities = {Negocios.class}, version = 3,exportSchema = false)
public abstract class NegociosRoomDataBase extends RoomDatabase {
    public abstract NegociosDao postInfoDao();

    private static NegociosRoomDataBase INSTANCE;


    public static NegociosRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NegociosRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NegociosRoomDataBase.class, "negocios_database")
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
                    new NegociosDbAsync(INSTANCE).execute();
                }
            };
}
