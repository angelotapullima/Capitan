package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository;

import android.os.AsyncTask;


public class ChanchasDbAsync extends AsyncTask<Void, Void, Void> {

    private final ChanchasDao mDao;

    public ChanchasDbAsync(ChanchasRoomDataBase db) {
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
