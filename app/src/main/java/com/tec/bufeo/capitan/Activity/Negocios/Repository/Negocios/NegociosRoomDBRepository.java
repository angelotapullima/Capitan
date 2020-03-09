package com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.Negocios.Model.Negocios;

import java.util.List;


public class NegociosRoomDBRepository {

    private NegociosDao negociosDao;
    LiveData<List<Negocios>> mAllMisReservas;

    public NegociosRoomDBRepository(Application application){
        NegociosRoomDataBase db = NegociosRoomDataBase.getDatabase(application);
        negociosDao = db.postInfoDao();

     }

    public LiveData<List<Negocios>> getmAll() {
        mAllMisReservas=negociosDao.getAll();
         return mAllMisReservas;
    }

    public LiveData<List<Negocios>> getAllMisNegocios() {
        mAllMisReservas=negociosDao.getAllMisNegocios();
        return mAllMisReservas;
    }

    public LiveData<List<Negocios>> getAllDetalle(String id) {
        mAllMisReservas=negociosDao.getAllDetalle(id);
        return mAllMisReservas;
    }

    public void deleteAllMisNegocios() {
        new DeleteAllNegociosyncTask(negociosDao).execute();
    }

    private static class DeleteAllNegociosyncTask extends AsyncTask<Void, Void, Void> {
        private NegociosDao misReservasDao;

        private DeleteAllNegociosyncTask(NegociosDao misReservasDao) {
            this.misReservasDao = misReservasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misReservasDao.deleteAll();
            Log.i("eliminado negocios", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertNegocios(List<Negocios> menuModel) {
        new insertAsyncTask(negociosDao).execute(menuModel);
    }

    private  class insertAsyncTask extends AsyncTask<List<Negocios>, Void, Void> {

        private NegociosDao mAsyncTaskDao;

        insertAsyncTask(NegociosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Negocios>... params) {
            mAsyncTaskDao.insertNegocios(params[0]);
            return null;
        }
    }
}
