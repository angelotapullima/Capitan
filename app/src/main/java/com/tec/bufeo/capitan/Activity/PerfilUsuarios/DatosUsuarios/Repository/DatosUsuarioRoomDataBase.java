package com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;


@Database(entities = {DatosUsuario.class}, version = 1)
public abstract class DatosUsuarioRoomDataBase extends RoomDatabase {
    public abstract DatosUsuarioDao postInfoDao();

    private static DatosUsuarioRoomDataBase INSTANCE;


    public static DatosUsuarioRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatosUsuarioRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatosUsuarioRoomDataBase.class, "datos_usuario_database")
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
                    new DatosUsuarioDbAsync(INSTANCE).execute();
                }
            };
}
