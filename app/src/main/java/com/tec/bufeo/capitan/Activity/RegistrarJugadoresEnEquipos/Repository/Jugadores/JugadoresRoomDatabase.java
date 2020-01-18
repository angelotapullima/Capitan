package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores;

import android.content.Context;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Jugadores.class}, version = 1)
public abstract class JugadoresRoomDatabase extends RoomDatabase {

    public abstract JugadoresDao postInfoDao();

    private static JugadoresRoomDatabase INSTANCE;

    public static JugadoresRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (JugadoresRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            JugadoresRoomDatabase.class, "jugadores_database")
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
                    new JugadoresDbAsync(INSTANCE).execute();
                }
            };
}
