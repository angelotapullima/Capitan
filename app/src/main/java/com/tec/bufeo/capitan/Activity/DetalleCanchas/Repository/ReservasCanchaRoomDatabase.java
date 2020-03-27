package com.tec.bufeo.capitan.Activity.DetalleCanchas.Repository;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.DetalleCanchas.Models.ReservasCancha;


@Database(entities = {ReservasCancha.class}, version = 1,exportSchema = false)
public abstract class ReservasCanchaRoomDatabase extends RoomDatabase {

    public abstract ReservasCanchaDao postInfoDao();

    private static ReservasCanchaRoomDatabase INSTANCE;


    static ReservasCanchaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ReservasCanchaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ReservasCanchaRoomDatabase.class, "reservas_database")
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
                    new ReservasCanchaDbAsync(INSTANCE).execute();
                }
            };
}
