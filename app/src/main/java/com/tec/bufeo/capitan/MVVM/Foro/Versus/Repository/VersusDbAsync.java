package com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository;

import android.os.AsyncTask;

public class VersusDbAsync extends AsyncTask<Void, Void, Void> {

    private final VersusDao versusDao;

    VersusDbAsync(VersusRoomDataBase db) {
        versusDao = db.postInfoDao();
    }



    @Override
    protected Void doInBackground(final Void... params) {

        /*boolean pues = DataConnection.isOnLine();
        if (pues){
            commentsDao.deleteAll();
        }*/
        //
       /* User user = new User("Chandra","SW");
        mDao.insert(user);
        user = new User("Mohan","student");
        mDao.insert(user);*/
        return null;
    }
}
