package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository;

import android.os.AsyncTask;


public class MisTorneoDbAsync extends AsyncTask<Void, Void, Void> {

    private final MisTorneoDao mDao;

    MisTorneoDbAsync(MisTorneoRoomDataBase db) {
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
