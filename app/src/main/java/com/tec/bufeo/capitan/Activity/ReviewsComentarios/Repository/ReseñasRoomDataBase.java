package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Models.Reseñas;


@Database(entities = {Reseñas.class}, version = 1,exportSchema = false)
public abstract class ReseñasRoomDataBase extends RoomDatabase {
    public abstract ReseñasDao postInfoDao();

    private static ReseñasRoomDataBase INSTANCE;


    public static ReseñasRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ReseñasRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ReseñasRoomDataBase.class, "reseñas_database")
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
                    new ReseñasDbAsync(INSTANCE).execute();
                }
            };
}
