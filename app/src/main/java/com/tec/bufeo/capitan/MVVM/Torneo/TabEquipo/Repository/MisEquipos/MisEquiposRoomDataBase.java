package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;

@Database(entities = {Mequipos.class}, version = 1)
public abstract class MisEquiposRoomDataBase extends RoomDatabase {
    public abstract MisEquiposDao postInfoDao();

    private static MisEquiposRoomDataBase INSTANCE;


    static MisEquiposRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MisEquiposRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MisEquiposRoomDataBase.class, "equipos_database")
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
                    new MisEquiposDbAsync(INSTANCE).execute();
                }
            };
}
