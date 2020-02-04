package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;

@Database(entities = {Retos.class}, version = 2)
public abstract class RetosRoomDataBase extends RoomDatabase {
    public abstract RetosDao postInfoDao();

    private static RetosRoomDataBase INSTANCE;


    static RetosRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RetosRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RetosRoomDataBase.class, "retos_database")
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
                    new RetosDbAsync(INSTANCE).execute();
                }
            };
}
