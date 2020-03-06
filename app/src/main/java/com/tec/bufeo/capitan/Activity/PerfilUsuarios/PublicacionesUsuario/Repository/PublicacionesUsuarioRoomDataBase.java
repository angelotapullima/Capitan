package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository.PublicacionesUsuarioDao;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Models.PublicacionesUsuario;


@Database(entities = {PublicacionesUsuario.class}, version = 2,exportSchema = false)
public abstract class PublicacionesUsuarioRoomDataBase extends RoomDatabase {
    public abstract PublicacionesUsuarioDao postInfoDao();

    private static PublicacionesUsuarioRoomDataBase INSTANCE;


    public static PublicacionesUsuarioRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PublicacionesUsuarioRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PublicacionesUsuarioRoomDataBase.class, "publicaciones_usuario_database")
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
                    new PublicacionesUsuarioDbAsync(INSTANCE).execute();
                }
            };
}
