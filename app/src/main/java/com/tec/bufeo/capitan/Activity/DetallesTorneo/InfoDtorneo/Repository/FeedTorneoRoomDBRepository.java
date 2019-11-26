package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.FeedTorneo;


import java.util.List;



public class FeedTorneoRoomDBRepository {
    private FeedTorneoDao feedTorneoDao;
    LiveData<List<FeedTorneo>> mAllPosts;

    public FeedTorneoRoomDBRepository(Application application){
         FeedTorneoRoomDataBase db = FeedTorneoRoomDataBase.getDatabase(application);
        feedTorneoDao = db.postInfoDao();
        //emAllPosts = feedTorneoDao.getAllPosts();
     }



    public LiveData<List<FeedTorneo>> getAllIdPosts() {


        mAllPosts = feedTorneoDao.getAllIdPosts();
        return mAllPosts;
    }
    public LiveData<List<FeedTorneo>> getAllPosts() {


        mAllPosts = feedTorneoDao.getAllPosts();
        return mAllPosts;
    }

    public void actualizarSup(String sup) {
        new ActualizarSup(feedTorneoDao).execute(sup);
    }

    public void ActualizarInf(String inf) {
        new ActualizarInf(feedTorneoDao).execute(inf);
    }

    public void NuevosDatos(String nuevos) {
        new NuevosDatos(feedTorneoDao).execute(nuevos);
    }
    public LiveData<List<FeedTorneo>> getIdTorneo(String id_torneo) {


        mAllPosts = feedTorneoDao.getIdTorneo(id_torneo);
        return mAllPosts;
    }

    public LiveData<List<FeedTorneo>> getSearch(String query){
        mAllPosts = feedTorneoDao.searchFeeds(query);
        return mAllPosts;

    }



    public void deleteAllFeed() {
        new DeleteAllFeedAsyncTask(feedTorneoDao).execute();
    }

    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void> {
        private FeedTorneoDao feedTorneoDao;

        private DeleteAllFeedAsyncTask(FeedTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            feedTorneoDao.deleteAll();
            Log.i("eliminado", "doInBackground: eliminado");
            return null;
        }
    }

    public void deleteOneFeed(String foro_id) {
        new DeleteOneFeedAsyncTask(feedTorneoDao).execute(foro_id);
    }


    public void insert(FeedTorneo feedTorneo){
        new insertAsyncTask(feedTorneoDao).execute(feedTorneo);
    }

    private static class insertAsyncTask extends AsyncTask<FeedTorneo, Void, Void> {

        private FeedTorneoDao mAsyncTaskDao;

        insertAsyncTask(FeedTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FeedTorneo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<FeedTorneo> feedTorneo) {
        new insertPostsAsyncTask(feedTorneoDao).execute(feedTorneo);
    }

    private static class insertPostsAsyncTask extends AsyncTask<List<FeedTorneo>, Void, Void> {

        private FeedTorneoDao mAsyncTaskDao;

        insertPostsAsyncTask(FeedTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<FeedTorneo>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }




    private static class DeleteOneFeedAsyncTask extends AsyncTask<String, Void, Void> {
        private FeedTorneoDao feedTorneoDao;

        private DeleteOneFeedAsyncTask(FeedTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.deleteOneFeed(modelFeeds[0]);

            return null;
        }
    }




    private static class ActualizarSup extends AsyncTask<String, Void, Void> {
        private FeedTorneoDao feedTorneoDao;

        private ActualizarSup(FeedTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.actualizarSup(modelFeeds[0]);

            return null;
        }
    }

    private static class ActualizarInf extends AsyncTask<String, Void, Void> {
        private FeedTorneoDao feedTorneoDao;

        private ActualizarInf(FeedTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.actualizarInf(modelFeeds[0]);

            return null;
        }
    }

    private static class NuevosDatos extends AsyncTask<String, Void, Void> {
        private FeedTorneoDao feedTorneoDao;

        private NuevosDatos(FeedTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.NuevosDatos(modelFeeds[0]);

            return null;
        }
    }
}
