package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository;

import android.os.AsyncTask;


public class    GruposDbAsync extends AsyncTask<Void, Void, Void> {

    private final GruposTorneoDao mDao;

    GruposDbAsync(GruposRoomDataBase db) {
        mDao = db.postInfoDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        //mDao.deleteAll(); //esto borra todo lo guardado
       /* User user = new User("Chandra","SW");
        mDao.insert(user);
        user = new User("Mohan","student");
        mDao.insert(user);*/
        return null;
    }
}
