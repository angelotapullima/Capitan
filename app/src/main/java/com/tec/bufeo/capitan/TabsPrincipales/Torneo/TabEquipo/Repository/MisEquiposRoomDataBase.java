package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Repository;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Models.Mequipos;

@Database(entities = {Mequipos.class}, version = 2,exportSchema = false)
public abstract class MisEquiposRoomDataBase extends RoomDatabase {
    public abstract MisEquiposDao postInfoDao();

    private static MisEquiposRoomDataBase INSTANCE;


    public static MisEquiposRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MisEquiposRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MisEquiposRoomDataBase.class, "equipos_database")
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
                    new MisEquiposDbAsync(INSTANCE).execute();
                }
            };
}
