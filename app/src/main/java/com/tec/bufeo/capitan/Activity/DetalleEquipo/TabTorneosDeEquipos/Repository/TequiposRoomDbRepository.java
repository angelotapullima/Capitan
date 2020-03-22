package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Models.TorneosDeEquipos;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TequiposRoomDbRepository {

    private TequiposDao tequiposDao;
    LiveData<List<TorneosDeEquipos>> mAllTorneosEquipos;

    public TequiposRoomDbRepository(Application application){
        TequiposRoomDataBase db = TequiposRoomDataBase.getDatabase(application);
        tequiposDao = db.postInfoDao();

    }

    public LiveData<List<TorneosDeEquipos>> getAllTorneoEquipos() {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllRetos();
        }*/
        mAllTorneosEquipos = tequiposDao.getAllTorneoEquipos();
        return mAllTorneosEquipos;
    }

    public void deleteAllTorneosEquipos() {
        new DeleteAllTorneosEquiposAsyncTask(tequiposDao).execute();
    }

    private static class DeleteAllTorneosEquiposAsyncTask extends AsyncTask<Void, Void, Void> {
        private TequiposDao tequiposDao;

        private DeleteAllTorneosEquiposAsyncTask(TequiposDao retosDao) {
            this.tequiposDao = retosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tequiposDao.deleteAll();
            Log.d("elim Torneos Equipos", "doInBackground: eliminado");
            return null;
        }
    }


    public void insertTorneosEquipos(List<TorneosDeEquipos> menuModel) {
        new insertAsyncTask(tequiposDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<TorneosDeEquipos>, Void, Void> {

        private TequiposDao mAsyncTaskDao;

        insertAsyncTask(TequiposDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<TorneosDeEquipos>... params) {
            mAsyncTaskDao.insertTorneosEquipos(params[0]);
            return null;
        }
    }




}
