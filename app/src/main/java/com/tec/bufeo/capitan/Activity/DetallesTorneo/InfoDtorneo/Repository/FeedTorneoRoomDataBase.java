package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.FeedTorneo;

import androidx.annotation.NonNull;





@Database(entities = {FeedTorneo.class}, version = 1)
public abstract class FeedTorneoRoomDataBase extends RoomDatabase {
    public abstract FeedTorneoDao postInfoDao();

    private static FeedTorneoRoomDataBase INSTANCE;


    static FeedTorneoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FeedTorneoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FeedTorneoRoomDataBase.class, "feedTorneo_database")
                            .addCallback(sRoomDatabaseCallback)
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
                    new FeedTorneoDbAsync(INSTANCE).execute();
                }
            };
}
