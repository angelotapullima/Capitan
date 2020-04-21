package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models.Chanchas;


@Database(entities = {Chanchas.class}, version = 1,exportSchema = false)
public abstract class ChanchasRoomDataBase extends RoomDatabase {
    public abstract ChanchasDao postInfoDao();

    private static ChanchasRoomDataBase INSTANCE;


    public static ChanchasRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ChanchasRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ChanchasRoomDataBase.class, "mis_chanchas_database")
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
                    new ChanchasDbAsync(INSTANCE).execute();
                }
            };
}
