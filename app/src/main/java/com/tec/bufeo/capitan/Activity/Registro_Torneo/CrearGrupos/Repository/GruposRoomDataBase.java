package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository;

import android.content.Context;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Grupos.class}, version = 1)
public abstract class GruposRoomDataBase extends RoomDatabase {
    public abstract GruposTorneoDao postInfoDao();

    private static GruposRoomDataBase INSTANCE;


    static GruposRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GruposRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GruposRoomDataBase.class, "grupos_database")
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
                    new GruposDbAsync(INSTANCE).execute();
                }
            };
}
