package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Repository;

import android.os.AsyncTask;


public class TorneosDbAsync extends AsyncTask<Void, Void, Void> {

    private final TorneosDao mDao;

    TorneosDbAsync(TorneosRoomDataBase db) {
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
