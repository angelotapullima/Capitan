package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository;

import android.os.AsyncTask;

;


public class RegistroEquiposTorneoDbAsync extends AsyncTask<Void, Void, Void> {

    private final RegistroEquiposTorneoDao mDao;

    RegistroEquiposTorneoDbAsync(RegistroEquiposTorneoRoomDataBase db) {
        mDao = db.postInfoDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        //mDao.deleteAll();
       /* User user = new User("Chandra","SW");
        mDao.insert(user);
        user = new User("Mohan","student");
        mDao.insert(user);*/
        return null;
    }
}
