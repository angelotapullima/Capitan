package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Repository;

import android.os.AsyncTask;

import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Repository.EquiposGrupoDao;
import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Repository.EquiposGrupoRoomDataBase;


public class EquiposGrupoDbAsync extends AsyncTask<Void, Void, Void> {

    private final EquiposGrupoDao mDao;

    public EquiposGrupoDbAsync(EquiposGrupoRoomDataBase db) {
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
