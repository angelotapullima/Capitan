package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.seleccionados;

import android.os.AsyncTask;

public class SeleccionadosDbAsync extends AsyncTask<Void,Void,Void> {

    private final SeleccionadosDao mDao;
    public SeleccionadosDbAsync(SeleccionadosRoomDatabase db) {
        this.mDao = db.postInfoDao();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}
