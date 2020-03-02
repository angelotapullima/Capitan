package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository;

import android.os.AsyncTask;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository.PublicacionesUsuarioDao;


public class PublicacionesUsuarioDbAsync extends AsyncTask<Void, Void, Void> {

    private final PublicacionesUsuarioDao mDao;

    public PublicacionesUsuarioDbAsync(PublicacionesUsuarioRoomDataBase db) {
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
