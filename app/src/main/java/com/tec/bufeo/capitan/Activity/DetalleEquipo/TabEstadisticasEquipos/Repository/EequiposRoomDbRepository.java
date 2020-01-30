package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Models.EstadisticasDeEquipos;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EequiposRoomDbRepository {

    private EequiposDao e;
    LiveData<List<EstadisticasDeEquipos>> mAllTorneosEquipos;

    public EequiposRoomDbRepository(Application application){
        EequiposRoomDataBase db = EequiposRoomDataBase.getDatabase(application);
        e = db.postInfoDao();

    }

    public LiveData<List<EstadisticasDeEquipos>> getAllTorneoEquipos() {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllRetos();
        }*/
        mAllTorneosEquipos = e.getAllTorneoEquipos();
        return mAllTorneosEquipos;
    }

    public void deleteAllTorneosEquipos() {
        new DeleteAllTorneosEquiposAsyncTask(e).execute();
    }

    private static class DeleteAllTorneosEquiposAsyncTask extends AsyncTask<Void, Void, Void> {
        private EequiposDao tequiposDao;

        private DeleteAllTorneosEquiposAsyncTask(EequiposDao retosDao) {
            this.tequiposDao = retosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tequiposDao.deleteAll();
            Log.i("elim Torneos Equipos", "doInBackground: eliminado");
            return null;
        }
    }


    public void insertTorneosEquipos(List<EstadisticasDeEquipos> menuModel) {
        new insertAsyncTask(e).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<EstadisticasDeEquipos>, Void, Void> {

        private EequiposDao mAsyncTaskDao;

        insertAsyncTask(EequiposDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<EstadisticasDeEquipos>... params) {
            mAsyncTaskDao.insertTorneosEquipos(params[0]);
            return null;
        }
    }




}
