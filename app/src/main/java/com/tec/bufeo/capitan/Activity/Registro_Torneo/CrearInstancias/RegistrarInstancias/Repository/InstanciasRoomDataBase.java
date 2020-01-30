package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository;

import android.content.Context;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Models.InstanciasModel;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {InstanciasModel.class}, version = 1)
public abstract class InstanciasRoomDataBase extends RoomDatabase {
    public abstract InstanciasDao postInfoDao();

    private static InstanciasRoomDataBase INSTANCE;


    static InstanciasRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (InstanciasRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InstanciasRoomDataBase.class, "instancias_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
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
                    new InstanciasDbAsync(INSTANCE).execute();
                }
            };
}