package com.tec.bufeo.capitan.Activity.MisMovimientos.Repository;

import android.os.AsyncTask;


public class MovimientosDbAsync extends AsyncTask<Void, Void, Void> {

    private final MovimientosDao mDao;

    public MovimientosDbAsync(MovimientosRoomDataBase db) {
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
