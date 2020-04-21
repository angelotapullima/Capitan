package com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.Repository;

import android.os.AsyncTask;


public class NotificacionesDbAsync extends AsyncTask<Void, Void, Void> {

    private final NotificacionesDao mDao;

    public NotificacionesDbAsync(NotificacionesRoomDataBase db) {
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
