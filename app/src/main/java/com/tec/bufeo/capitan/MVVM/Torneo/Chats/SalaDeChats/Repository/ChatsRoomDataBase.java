package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models.Chats;

@Database(entities = {Chats.class}, version = 1)
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
