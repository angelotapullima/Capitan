package com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;
import com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Models.Notificaciones;


@Database(entities = {Notificaciones.class}, version = 2,exportSchema = false)
public abstract class NotificacionesRoomDataBase extends RoomDatabase {
    public abstract NotificacionesDao postInfoDao();

    private static NotificacionesRoomDataBase INSTANCE;


    public static NotificacionesRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotificacionesRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotificacionesRoomDataBase.class, "notificaciones_database")
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
                    new NotificacionesDbAsync(INSTANCE).execute();
                }
            };
}
