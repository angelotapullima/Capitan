package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository;

import android.os.AsyncTask;


public class OtrosTorneosDbAsync extends AsyncTask<Void, Void, Void> {

    private final OtrosTorneosDao mDao;

    OtrosTorneosDbAsync(OtrosTorneosRoomDataBase db) {
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
