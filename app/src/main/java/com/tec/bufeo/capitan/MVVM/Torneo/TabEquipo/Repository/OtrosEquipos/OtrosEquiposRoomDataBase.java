package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.OtrosEquipos;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;

@Database(entities = {Mequipos.class}, version = 1)
public abstract class OtrosEquiposRoomDataBase extends RoomDatabase {
    public abstract OtrosEquiposDao postInfoDao();

    private static OtrosEquiposRoomDataBase INSTANCE;


    public static OtrosEquiposRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OtrosEquiposRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OtrosEquiposRoomDataBase.class, "equipos_database")
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
                    new OtrosEquiposDbAsync(INSTANCE).execute();
                }
            };
}
