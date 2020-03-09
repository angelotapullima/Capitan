package com.tec.bufeo.capitan.Activity.Negocios.Repository.Canchas;

import android.os.AsyncTask;


public class CanchasDbAsync extends AsyncTask<Void, Void, Void> {

    private final CanchasDao mDao;

    public CanchasDbAsync(CanchasRoomDataBase db) {
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
