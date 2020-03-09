package com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios;

import android.os.AsyncTask;


public class NegociosDbAsync extends AsyncTask<Void, Void, Void> {

    private final NegociosDao mDao;

    public NegociosDbAsync(NegociosRoomDataBase db) {
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
