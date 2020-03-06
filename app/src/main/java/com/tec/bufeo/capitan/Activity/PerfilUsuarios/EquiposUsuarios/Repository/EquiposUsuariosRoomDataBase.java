package com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models.EquiposUsuarios;


@Database(entities = {EquiposUsuarios.class}, version = 1,exportSchema = false)
public abstract class EquiposUsuariosRoomDataBase extends RoomDatabase {
    public abstract EquiposUsuariosDao postInfoDao();

    private static EquiposUsuariosRoomDataBase INSTANCE;


    public static EquiposUsuariosRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EquiposUsuariosRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EquiposUsuariosRoomDataBase.class, "equipos_usuario_database")
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
                    new EquiposUsuariosDbAsync(INSTANCE).execute();
                }
            };
}
