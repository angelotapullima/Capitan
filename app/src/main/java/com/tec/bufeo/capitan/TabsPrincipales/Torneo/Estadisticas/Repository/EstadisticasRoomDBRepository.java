package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Estadisticas.Models.Estadisticas;

import java.util.List;

public class EstadisticasRoomDBRepository {
    private EstadisticasDao estadisticasDao;
    LiveData<List<Estadisticas>> mAllEstadisticas;

    public EstadisticasRoomDBRepository(Application application){
        EstadisticasRoomDataBase db = EstadisticasRoomDataBase.getDatabase(application);
        estadisticasDao = db.postInfoDao();

     }

    /*public LiveData<List<RegistroEquiposTorneo>> getAllEquipos(String mi_equipo) {
        boolean online = ForoFragment.isOnLine();

        if (online){
            deleteAllEquipos();
        }
        mAllEquipos = equiposDao.getAllEquipos(mi_equipo);
        return mAllEquipos;
    }*/


    public LiveData<List<Estadisticas>> getAllEstadisticas() {

        mAllEstadisticas = estadisticasDao.getAllEstadisticas();
        return mAllEstadisticas;
    }





    public void deleteAllEstadisticas() {
        new DeleteAllEstadisticasyncTask(estadisticasDao).execute();
    }

    private static class DeleteAllEstadisticasyncTask extends AsyncTask<Void, Void, Void> {
        private EstadisticasDao estadisticasDao;

        private DeleteAllEstadisticasyncTask(EstadisticasDao estadisticasDao) {
            this.estadisticasDao = estadisticasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            estadisticasDao.deleteAll();
            Log.d("eliminado estadisticas", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEstadisticas(List<Estadisticas> menuModel) {
        new insertAsyncTask(estadisticasDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Estadisticas>, Void, Void> {

        private EstadisticasDao mAsyncTaskDao;

        insertAsyncTask(EstadisticasDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Estadisticas>... params) {
            mAsyncTaskDao.insertEstadisticas(params[0]);
            return null;
        }
    }
}
