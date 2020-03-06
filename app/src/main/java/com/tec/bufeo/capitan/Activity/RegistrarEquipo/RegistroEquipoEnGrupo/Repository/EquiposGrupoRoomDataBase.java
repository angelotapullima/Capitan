package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Model.EquiposGrupo;

@Database(entities = {EquiposGrupo.class}, version = 1,exportSchema = false)
public abstract class EquiposGrupoRoomDataBase extends RoomDatabase {
    public abstract EquiposGrupoDao postInfoDao();

    private static EquiposGrupoRoomDataBase INSTANCE;


    public static EquiposGrupoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EquiposGrupoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EquiposGrupoRoomDataBase.class, "equipos_grupo_database")
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
                    new EquiposGrupoDbAsync(INSTANCE).execute();
                }
            };
}
