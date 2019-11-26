package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;

@Database(entities = {ModelFeed.class}, version = 1)
public abstract class FeedRoomDataBase extends RoomDatabase {
    public abstract FeedDao postInfoDao();

    private static FeedRoomDataBase INSTANCE;


    static FeedRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FeedRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FeedRoomDataBase.class, "feed_database")
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
                    new FeedDbAsync(INSTANCE).execute();
                }
            };
}
