package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Repository;

import android.os.AsyncTask;


public class MisEquiposDbAsync extends AsyncTask<Void, Void, Void> {

    private final MisEquiposDao mDao;

    public MisEquiposDbAsync(MisEquiposRoomDataBase db) {
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
