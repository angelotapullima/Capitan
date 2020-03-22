package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Detalle;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.DetalleTorneo;

import java.util.List;

public class DetalleTorneoRoomDBRepository {

    private DetalleTorneoDao feedTorneoDao;
    LiveData<List<DetalleTorneo>> mAllPosts;

    public DetalleTorneoRoomDBRepository(Application application){
        DetalleTorneoRoomDataBase db = DetalleTorneoRoomDataBase.getDatabase(application);
        feedTorneoDao = db.postInfoDao();
        //emAllPosts = feedTorneoDao.getAllPosts();
    }



    public LiveData<List<DetalleTorneo>> getIdTorneo(String id_torneo) {


        mAllPosts = feedTorneoDao.getIdTorneo(id_torneo);
        return mAllPosts;
    }






    public void deleteAllFeed() {
        new DeleteAllFeedAsyncTask(feedTorneoDao).execute();
    }

    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void> {
        private DetalleTorneoDao feedTorneoDao;

        private DeleteAllFeedAsyncTask(DetalleTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            feedTorneoDao.deleteAll();
            Log.d("eliminado", "doInBackground: eliminado");
            return null;
        }
    }




    public void insert(DetalleTorneo feedTorneo){
        new insertAsyncTask(feedTorneoDao).execute(feedTorneo);
    }

    private static class insertAsyncTask extends AsyncTask<DetalleTorneo, Void, Void> {

        private DetalleTorneoDao mAsyncTaskDao;

        insertAsyncTask(DetalleTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DetalleTorneo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<DetalleTorneo> feedTorneo) {
        new insertPostsAsyncTask(feedTorneoDao).execute(feedTorneo);
    }

    private static class insertPostsAsyncTask extends AsyncTask<List<DetalleTorneo>, Void, Void> {

        private DetalleTorneoDao mAsyncTaskDao;

        insertPostsAsyncTask(DetalleTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<DetalleTorneo>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }







}
