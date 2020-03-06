package com.tec.bufeo.capitan.Activity.MisMovimientos.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;


@Database(entities = {Movimientos.class}, version = 1,exportSchema = false)
public abstract class MovimientosRoomDataBase extends RoomDatabase {
    public abstract MovimientosDao postInfoDao();

    private static MovimientosRoomDataBase INSTANCE;


    public static MovimientosRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovimientosRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovimientosRoomDataBase.class, "movimientos_database")
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
                    new MovimientosDbAsync(INSTANCE).execute();
                }
            };
}
