package com.tec.bufeo.capitan.Activity.DetalleCanchas.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.DetalleCanchas.Models.ReservasCancha;

import java.util.List;

public class ReservasCanchaRoomDBRepository {

    private ReservasCanchaDao goleadoresDao;
    LiveData<List<ReservasCancha>> mAllGoleadores;

    public ReservasCanchaRoomDBRepository(Application application){
        ReservasCanchaRoomDatabase db = ReservasCanchaRoomDatabase.getDatabase(application);
        goleadoresDao = db.postInfoDao();
        //emAllPosts = feedTorneoDao.getAllPosts();
    }




    public LiveData<List<ReservasCancha>> getReservasDia(String fecha,String cancha_id) {


        mAllGoleadores = goleadoresDao.getReservasDia(fecha,cancha_id);
        return mAllGoleadores;
    }







    public void deleteAllGoleadores() {
        new DeleteAllGoleadoresAsyncTask(goleadoresDao).execute();
    }

    private static class DeleteAllGoleadoresAsyncTask extends AsyncTask<Void, Void, Void> {
        private ReservasCanchaDao goleadoresDao;

        private DeleteAllGoleadoresAsyncTask(ReservasCanchaDao goleadoresDao) {
            this.goleadoresDao = goleadoresDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            goleadoresDao.deleteAll();
            Log.d("goleadores", "doInBackground: eliminado");
            return null;
        }
    }



    public void insert(ReservasCancha goleadores){
        new insertAsyncTask(goleadoresDao).execute(goleadores);
    }

    private static class insertAsyncTask extends AsyncTask<ReservasCancha, Void, Void> {

        private ReservasCanchaDao mAsyncTaskDao;

        insertAsyncTask(ReservasCanchaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ReservasCancha... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<ReservasCancha> goleadores) {
        new insertGoleadoresAsyncTask(goleadoresDao).execute(goleadores);
    }

    private static class insertGoleadoresAsyncTask extends AsyncTask<List<ReservasCancha>, Void, Void> {

        private ReservasCanchaDao mAsyncTaskDao;

        insertGoleadoresAsyncTask(ReservasCanchaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<ReservasCancha>... params) {
            mAsyncTaskDao.insertReservas(params[0]);
            return null;
        }
    }







}
