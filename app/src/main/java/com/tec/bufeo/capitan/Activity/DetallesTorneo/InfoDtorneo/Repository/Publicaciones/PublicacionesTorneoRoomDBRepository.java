package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.PublicacionesTorneo;


import java.util.List;



public class PublicacionesTorneoRoomDBRepository {
    private PublicacionesTorneoDao feedTorneoDao;
    LiveData<List<PublicacionesTorneo>> mAllPosts;

    public PublicacionesTorneoRoomDBRepository(Application application){
        PublicacionesTorneoRoomDataBase db = PublicacionesTorneoRoomDataBase.getDatabase(application);
        feedTorneoDao = db.postInfoDao();
        //emAllPosts = feedTorneoDao.getAllPosts();
     }



    public LiveData<List<PublicacionesTorneo>> getAllIdPosts() {


        //mAllPosts = feedTorneoDao.getAllIdPosts();
        mAllPosts = feedTorneoDao.getAllPosts();
        return mAllPosts;
    }
    public LiveData<List<PublicacionesTorneo>> getAllPosts() {


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
    public LiveData<List<PublicacionesTorneo>> getIdTorneo(String id_torneo) {


        mAllPosts = feedTorneoDao.getIdTorneo(id_torneo);
        return mAllPosts;
    }

    public LiveData<List<PublicacionesTorneo>> getSearch(String query){
        mAllPosts = feedTorneoDao.searchFeeds(query);
        return mAllPosts;

    }



    public void deleteAllFeed() {
        new DeleteAllFeedAsyncTask(feedTorneoDao).execute();
    }

    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void> {
        private PublicacionesTorneoDao feedTorneoDao;

        private DeleteAllFeedAsyncTask(PublicacionesTorneoDao feedTorneoDao) {
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


    public void insert(PublicacionesTorneo feedTorneo){
        new insertAsyncTask(feedTorneoDao).execute(feedTorneo);
    }

    private static class insertAsyncTask extends AsyncTask<PublicacionesTorneo, Void, Void> {

        private PublicacionesTorneoDao mAsyncTaskDao;

        insertAsyncTask(PublicacionesTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PublicacionesTorneo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<PublicacionesTorneo> feedTorneo) {
        new insertPostsAsyncTask(feedTorneoDao).execute(feedTorneo);
    }

    private static class insertPostsAsyncTask extends AsyncTask<List<PublicacionesTorneo>, Void, Void> {

        private PublicacionesTorneoDao mAsyncTaskDao;

        insertPostsAsyncTask(PublicacionesTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<PublicacionesTorneo>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }




    private static class DeleteOneFeedAsyncTask extends AsyncTask<String, Void, Void> {
        private PublicacionesTorneoDao feedTorneoDao;

        private DeleteOneFeedAsyncTask(PublicacionesTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.deleteOneFeed(modelFeeds[0]);

            return null;
        }
    }




    private static class ActualizarSup extends AsyncTask<String, Void, Void> {
        private PublicacionesTorneoDao feedTorneoDao;

        private ActualizarSup(PublicacionesTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.actualizarSup(modelFeeds[0]);

            return null;
        }
    }

    private static class ActualizarInf extends AsyncTask<String, Void, Void> {
        private PublicacionesTorneoDao feedTorneoDao;

        private ActualizarInf(PublicacionesTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.actualizarInf(modelFeeds[0]);

            return null;
        }
    }

    private static class NuevosDatos extends AsyncTask<String, Void, Void> {
        private PublicacionesTorneoDao feedTorneoDao;

        private NuevosDatos(PublicacionesTorneoDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.NuevosDatos(modelFeeds[0]);

            return null;
        }
    }
}
