package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models.Chats;

@Database(entities = {Chats.class}, version = 1,exportSchema = false)
public abstract class ChatsRoomDataBase extends RoomDatabase {
    public abstract ChatsDao chatsDao();

    private static ChatsRoomDataBase INSTANCE;


    static ChatsRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ChatsRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ChatsRoomDataBase.class, "lista_chats_database")
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
                    new ChatsDbAsync(INSTANCE).execute();
                }
            };
}
