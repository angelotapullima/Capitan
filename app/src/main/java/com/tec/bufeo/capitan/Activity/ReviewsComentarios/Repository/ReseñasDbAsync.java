package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Repository;

import android.os.AsyncTask;


public class ReseñasDbAsync extends AsyncTask<Void, Void, Void> {

    private final ReseñasDao mDao;

    public ReseñasDbAsync(ReseñasRoomDataBase db) {
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
