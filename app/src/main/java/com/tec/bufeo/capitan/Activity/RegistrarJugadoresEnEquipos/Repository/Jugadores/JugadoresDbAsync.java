package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores;

import android.os.AsyncTask;

public class JugadoresDbAsync extends AsyncTask<Void,Void,Void> {

    private final JugadoresDao mDao;

    public JugadoresDbAsync(JugadoresRoomDatabase db) {
        this.mDao = db.postInfoDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
