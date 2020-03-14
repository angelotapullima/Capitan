package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Models.Torneo;

@Database(entities = {Torneo.class}, version = 1,exportSchema = false)
public abstract class TorneosRoomDataBase extends RoomDatabase {
    public abstract TorneosDao postInfoDao();

    private static TorneosRoomDataBase INSTANCE;


    static TorneosRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TorneosRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TorneosRoomDataBase.class, "torneos_database")
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
                    new TorneosDbAsync(INSTANCE).execute();
                }
            };
}
