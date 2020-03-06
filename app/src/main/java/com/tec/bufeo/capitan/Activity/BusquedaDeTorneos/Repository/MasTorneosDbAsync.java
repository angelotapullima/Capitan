package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository;

import android.os.AsyncTask;


public class MasTorneosDbAsync extends AsyncTask<Void, Void, Void> {

    private final MasTorneosDao mDao;

    public MasTorneosDbAsync(MasTorneosRoomDataBase db) {
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
