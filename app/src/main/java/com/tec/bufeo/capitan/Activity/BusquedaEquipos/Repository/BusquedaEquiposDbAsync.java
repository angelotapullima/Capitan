package com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository;

import android.os.AsyncTask;


public class BusquedaEquiposDbAsync extends AsyncTask<Void, Void, Void> {

    private final BusquedaEquiposDao mDao;

    public BusquedaEquiposDbAsync(BusquedaEquiposRoomDataBase db) {
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
