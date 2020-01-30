package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

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
                            .fallbackToDestructiveMigration ()
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
