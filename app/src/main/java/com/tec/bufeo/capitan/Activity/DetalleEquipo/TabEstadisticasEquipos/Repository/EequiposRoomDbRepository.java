package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Models.EstadisticasDeEquipos;
import com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Views.ForoFragment;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EequiposRoomDbRepository {

    private EequiposDao e;
    LiveData<List<EstadisticasDeEquipos>> mAllTorneosEquipos;

    public EequiposRoomDbRepository(Application application){
        EequiposRoomDataBase db = EequiposRoomDataBase.getDatabase(application);
        e = db.postInfoDao();

    }

    public LiveData<List<EstadisticasDeEquipos>> getAllEstadisticas() {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllRetos();
        }*/
        mAllTorneosEquipos = e.getAllEstadisticasDeEquipos();
        return mAllTorneosEquipos;
    }

    public void DeleteAllEstadisticasEquipos() {
        new DeleteAllEstadisticasEquiposAsyncTask(e).execute();
    }

    private static class DeleteAllEstadisticasEquiposAsyncTask extends AsyncTask<Void, Void, Void> {
        private EequiposDao tequiposDao;

        private DeleteAllEstadisticasEquiposAsyncTask(EequiposDao retosDao) {
            this.tequiposDao = retosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tequiposDao.deleteAll();
            Log.d("elim Estadisticas equ", "doInBackground: eliminado");
            return null;
        }
    }


    public void insertEstadisticasEquipos(List<EstadisticasDeEquipos> menuModel) {
        new insertAsyncTask(e).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<EstadisticasDeEquipos>, Void, Void> {

        private EequiposDao mAsyncTaskDao;

        insertAsyncTask(EequiposDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<EstadisticasDeEquipos>... params) {
            mAsyncTaskDao.insertEstadisticasEquipos(params[0]);
            return null;
        }
    }




}
