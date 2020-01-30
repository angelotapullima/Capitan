package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository;

import android.os.AsyncTask;

public class EequiposDbAsync extends AsyncTask<Void, Void, Void>  {
    private final EequiposDao mDao;

    EequiposDbAsync(EequiposRoomDataBase db) {
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
