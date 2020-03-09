package com.tec.bufeo.capitan.Activity.Negocios.Repository.Canchas;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.Negocios.Model.Canchas;

import java.util.List;


public class CanchasRoomDBRepository {

    private CanchasDao canchasDao;
    LiveData<List<Canchas>> mAllMisReservas;

    public CanchasRoomDBRepository(Application application){
        CanchasRoomDataBase db = CanchasRoomDataBase.getDatabase(application);
        canchasDao = db.postInfoDao();

     }

    public LiveData<List<Canchas>> getCancha(String id) {
        mAllMisReservas=canchasDao.getCancha(id);
         return mAllMisReservas;
    }


    public void deleteAllMisReservas() {
        new DeleteAllReservasyncTask(canchasDao).execute();
    }

    private static class DeleteAllReservasyncTask extends AsyncTask<Void, Void, Void> {
        private CanchasDao misReservasDao;

        private DeleteAllReservasyncTask(CanchasDao misReservasDao) {
            this.misReservasDao = misReservasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misReservasDao.deleteAll();
            Log.i("eliminado  canchas", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<Canchas> menuModel) {
        new insertAsyncTask(canchasDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Canchas>, Void, Void> {

        private CanchasDao mAsyncTaskDao;

        insertAsyncTask(CanchasDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Canchas>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
