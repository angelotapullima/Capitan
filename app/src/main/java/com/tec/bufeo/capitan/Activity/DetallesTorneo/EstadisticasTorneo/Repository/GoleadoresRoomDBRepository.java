package com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EstadisticasTorneo.Models.Goleadores;

import java.util.List;

public class GoleadoresRoomDBRepository {

    private GoleadoresDao goleadoresDao;
    LiveData<List<Goleadores>> mAllGoleadores;

    public GoleadoresRoomDBRepository(Application application){
        GoleadoresRoomDatabase db = GoleadoresRoomDatabase.getDatabase(application);
        goleadoresDao = db.postInfoDao();
        //emAllPosts = feedTorneoDao.getAllPosts();
    }




    public LiveData<List<Goleadores>> getAllGoleadores(String id_torneo) {


        mAllGoleadores = goleadoresDao.getAllGoleadores(id_torneo);
        return mAllGoleadores;
    }







    public void deleteAllGoleadores() {
        new DeleteAllGoleadoresAsyncTask(goleadoresDao).execute();
    }

    private static class DeleteAllGoleadoresAsyncTask extends AsyncTask<Void, Void, Void> {
        private GoleadoresDao goleadoresDao;

        private DeleteAllGoleadoresAsyncTask(GoleadoresDao goleadoresDao) {
            this.goleadoresDao = goleadoresDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            goleadoresDao.deleteAll();
            Log.d("goleadores", "doInBackground: eliminado");
            return null;
        }
    }



    public void insert(Goleadores goleadores){
        new insertAsyncTask(goleadoresDao).execute(goleadores);
    }

    private static class insertAsyncTask extends AsyncTask<Goleadores, Void, Void> {

        private GoleadoresDao mAsyncTaskDao;

        insertAsyncTask(GoleadoresDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Goleadores... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<Goleadores> goleadores) {
        new insertGoleadoresAsyncTask(goleadoresDao).execute(goleadores);
    }

    private static class insertGoleadoresAsyncTask extends AsyncTask<List<Goleadores>, Void, Void> {

        private GoleadoresDao mAsyncTaskDao;

        insertGoleadoresAsyncTask(GoleadoresDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Goleadores>... params) {
            mAsyncTaskDao.insertGoleadores(params[0]);
            return null;
        }
    }







}
