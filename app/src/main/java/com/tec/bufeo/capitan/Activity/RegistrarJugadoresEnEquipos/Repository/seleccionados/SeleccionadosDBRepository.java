package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.seleccionados;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.JugadoresSeleccionados;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SeleccionadosDBRepository {

    private SeleccionadosDao seleccionadosDao;
    LiveData<List<JugadoresSeleccionados>> mAllJugadores;

    public SeleccionadosDBRepository (Application application){
        SeleccionadosRoomDatabase db = SeleccionadosRoomDatabase.getDatabase(application);
        seleccionadosDao=db.postInfoDao();
    }

    public LiveData<List<JugadoresSeleccionados>> getAllSeleccionados() {
        mAllJugadores=seleccionadosDao.getAll();
        return mAllJugadores;
    }


    public void deleteAllSeleccionados() {
        new deleteAllSeleccionados(seleccionadosDao).execute();
    }

    private static class deleteAllSeleccionados extends AsyncTask<Void, Void, Void> {
        private SeleccionadosDao seleccionadosDao;

        private deleteAllSeleccionados(SeleccionadosDao seleccionadosDao) {
            this.seleccionadosDao = seleccionadosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            seleccionadosDao.deleteAll();
            Log.i("eliminado jugadores", "doInBackground: eliminado");
            return null;
        }
    }


    public void insertSeleccionados(JugadoresSeleccionados menuModel) {
        new insertSeleccionados(seleccionadosDao).execute(menuModel);
    }

    private static class insertSeleccionados extends AsyncTask<JugadoresSeleccionados, Void, Void> {

        private SeleccionadosDao mAsyncTaskDao;

        insertSeleccionados(SeleccionadosDao dao) {
            mAsyncTaskDao = dao;
        }



        @Override
        protected Void doInBackground(JugadoresSeleccionados... jugadoresSeleccionados) {
            mAsyncTaskDao.insert(jugadoresSeleccionados[0]);
            return null;
        }
    }

    public void DeleteOne(String menuModel) {
        new DeleteOne(seleccionadosDao).execute(menuModel);
    }

    private static class DeleteOne extends AsyncTask<String, Void, Void> {

        private SeleccionadosDao mAsyncTaskDao;

        DeleteOne(SeleccionadosDao dao) {
            mAsyncTaskDao = dao;
        }



        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao.deleteOne(String.valueOf(strings[0]));
            return null;
        }
    }

}
