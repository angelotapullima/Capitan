package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.seleccionados;



import android.content.Context;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.JugadoresSeleccionados;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {JugadoresSeleccionados.class}, version = 1)
public abstract class SeleccionadosRoomDatabase extends RoomDatabase {
    public abstract SeleccionadosDao postInfoDao();

    private static SeleccionadosRoomDatabase INSTANCE;

    public static SeleccionadosRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SeleccionadosRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SeleccionadosRoomDatabase.class, "seleccionados_database")
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
                    new SeleccionadosDbAsync(INSTANCE).execute();
                }
            };
}
