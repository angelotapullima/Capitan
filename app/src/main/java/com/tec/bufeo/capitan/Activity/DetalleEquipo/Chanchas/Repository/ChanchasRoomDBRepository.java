package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models.Chanchas;

import java.util.List;


public class ChanchasRoomDBRepository {

    private ChanchasDao misReservasDao;
    LiveData<List<Chanchas>> mAllMisReservas;

    public ChanchasRoomDBRepository(Application application){
        ChanchasRoomDataBase db = ChanchasRoomDataBase.getDatabase(application);
        misReservasDao = db.postInfoDao();

     }

    public LiveData<List<Chanchas>> getChanchas(String id_equipo) {
        mAllMisReservas=misReservasDao.getChanchas(id_equipo);
         return mAllMisReservas;
    }



    public void deleteAllMisReservas() {
        new DeleteAllReservasyncTask(misReservasDao).execute();
    }

    private static class DeleteAllReservasyncTask extends AsyncTask<Void, Void, Void> {
        private ChanchasDao misReservasDao;

        private DeleteAllReservasyncTask(ChanchasDao misReservasDao) {
            this.misReservasDao = misReservasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misReservasDao.deleteAll();
            Log.i("eliminado mis chanchas", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<Chanchas> menuModel) {
        new insertAsyncTask(misReservasDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Chanchas>, Void, Void> {

        private ChanchasDao mAsyncTaskDao;

        insertAsyncTask(ChanchasDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Chanchas>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
