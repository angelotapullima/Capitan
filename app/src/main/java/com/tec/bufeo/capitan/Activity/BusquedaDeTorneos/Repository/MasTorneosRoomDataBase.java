package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Models.MasTorneos;

@Database(entities = {MasTorneos.class}, version = 1,exportSchema = false)
public abstract class MasTorneosRoomDataBase extends RoomDatabase {
    public abstract MasTorneosDao postInfoDao();

    private static MasTorneosRoomDataBase INSTANCE;


    public static MasTorneosRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MasTorneosRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MasTorneosRoomDataBase.class, "mas_torneos_database")
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
                    new MasTorneosDbAsync(INSTANCE).execute();
                }
            };
}
