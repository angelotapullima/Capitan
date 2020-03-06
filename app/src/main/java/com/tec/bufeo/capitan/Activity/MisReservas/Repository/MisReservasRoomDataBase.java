package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;


@Database(entities = {MisReservas.class}, version = 1,exportSchema = false)
public abstract class MisReservasRoomDataBase extends RoomDatabase {
    public abstract MisReservasDao postInfoDao();

    private static MisReservasRoomDataBase INSTANCE;


    public static MisReservasRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MisReservasRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MisReservasRoomDataBase.class, "mis_reservas_database")
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
                    new MisReservasDbAsync(INSTANCE).execute();
                }
            };
}
