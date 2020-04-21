package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.ReviewsComentarios.Models.Reseñas;

import java.util.List;


public class ReseñasRoomDBRepository {

    private ReseñasDao misReservasDao;
    LiveData<List<Reseñas>> mAllMisReservas;

    public ReseñasRoomDBRepository(Application application){
        ReseñasRoomDataBase db = ReseñasRoomDataBase.getDatabase(application);
        misReservasDao = db.postInfoDao();

     }

    public LiveData<List<Reseñas>> getValor(String valor) {
        mAllMisReservas=misReservasDao.getValor(valor);
         return mAllMisReservas;
    }

    public LiveData<List<Reseñas>> getAll() {
        mAllMisReservas=misReservasDao.getAll();
        return mAllMisReservas;
    }

    public void deleteAllMisReseñas() {
        new DeleteAllReseñasyncTask(misReservasDao).execute();
    }

    private static class DeleteAllReseñasyncTask extends AsyncTask<Void, Void, Void> {
        private ReseñasDao misReservasDao;

        private DeleteAllReseñasyncTask(ReseñasDao misReservasDao) {
            this.misReservasDao = misReservasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misReservasDao.deleteAll();
            Log.d("eliminado  reseñas", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertReseñas(List<Reseñas> menuModel) {
        new insertAsyncTask(misReservasDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Reseñas>, Void, Void> {

        private ReseñasDao mAsyncTaskDao;

        insertAsyncTask(ReseñasDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Reseñas>... params) {
            mAsyncTaskDao.insertReseñas(params[0]);
            return null;
        }
    }
}
