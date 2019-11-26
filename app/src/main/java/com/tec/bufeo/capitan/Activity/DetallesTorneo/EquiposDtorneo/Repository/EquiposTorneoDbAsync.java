package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Repository;

import android.os.AsyncTask;
;


public class EquiposTorneoDbAsync extends AsyncTask<Void, Void, Void> {

    private final EquiposTorneoDao mDao;

    EquiposTorneoDbAsync(EquiposTorneoRoomDataBase db) {
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
