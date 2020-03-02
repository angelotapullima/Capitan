package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import android.os.AsyncTask;


public class MisReservasDbAsync extends AsyncTask<Void, Void, Void> {

    private final MisReservasDao mDao;

    public MisReservasDbAsync(MisReservasRoomDataBase db) {
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
