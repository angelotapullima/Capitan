package com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Models.Mensajes;

@Database(entities = {Mensajes.class}, version = 1)
public abstract class MensajesRoomDataBase extends RoomDatabase {
    public abstract MensajesDao postInfoDao();

    private static MensajesRoomDataBase INSTANCE;


    static MensajesRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MensajesRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MensajesRoomDataBase.class, "lista_mensajes_database")
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
                    new MensajesDbAsync(INSTANCE).execute();
                }
            };
}
