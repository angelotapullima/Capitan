package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Models.Comments;

@Database(entities = {Comments.class}, version = 1)
public abstract class CommentsRoomDataBase extends RoomDatabase {
    public abstract CommentsDao postInfoDao();

    private static CommentsRoomDataBase INSTANCE;


    static CommentsRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CommentsRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CommentsRoomDataBase.class, "lista_comments_database")
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
                    new CommentsDbAsync(INSTANCE).execute();
                }
            };
}
