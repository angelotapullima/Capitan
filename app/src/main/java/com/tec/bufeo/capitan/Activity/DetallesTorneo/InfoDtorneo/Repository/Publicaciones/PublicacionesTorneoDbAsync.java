package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones;

import android.os.AsyncTask;



public class PublicacionesTorneoDbAsync extends AsyncTask<Void, Void, Void> {

    private final PublicacionesTorneoDao mDao;

    PublicacionesTorneoDbAsync(PublicacionesTorneoRoomDataBase db) {
        mDao = db.postInfoDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        //mDao.deleteAll(); //esto borra todo lo guardado
       /* User user = new User("Chandra","SW");
        mDao.insert(user);
        user = new User("Mohan","student");
        mDao.insert(user);*/
        return null;
    }
}
