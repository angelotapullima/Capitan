package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;


import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;

import java.util.List;



public class FeedRoomDBRepository {
    private FeedDao feedDao;
    LiveData<List<ModelFeed>> mAllPosts;

    public FeedRoomDBRepository(Application application){
        FeedRoomDataBase db = FeedRoomDataBase.getDatabase(application);
        feedDao = db.postInfoDao();
        //emAllPosts = feedTorneoDao.getAllPosts();
     }



    public LiveData<List<ModelFeed>> getAllIdPosts() {


        //mAllPosts = feedDao.getAllIdPosts();
        mAllPosts = feedDao.getAllPosts();
        return mAllPosts;
    }
    public LiveData<List<ModelFeed>> getAllPosts() {


        mAllPosts = feedDao.getAllPosts();
        return mAllPosts;
    }

    public void actualizarSup(String sup) {
        new ActualizarSup(feedDao).execute(sup);
    }
    private static class ActualizarSup extends AsyncTask<String, Void, Void> {
        private FeedDao feedDao;

        private ActualizarSup(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedDao.actualizarSup(modelFeeds[0]);

            return null;
        }
    }


    public void ActualizarInf(String inf) {
        new ActualizarInf(feedDao).execute(inf);
    }

    public void NuevosDatos(String nuevos) {
        new NuevosDatos(feedDao).execute(nuevos);
    }
    public LiveData<List<ModelFeed>> getIdTorneo(String id_torneo) {


        mAllPosts = feedDao.getIdTorneo(id_torneo);
        return mAllPosts;
    }

    public LiveData<List<ModelFeed>> getIdUsuario(String id_usuario) {


        mAllPosts = feedDao.getIdUsuario(id_usuario);
        return mAllPosts;
    }
    public LiveData<List<ModelFeed>> getSearch(String query){
        mAllPosts = feedDao.searchFeeds(query);
        return mAllPosts;

    }

    public void cantidadLikes(String cantidad) {
        new cantidadLikes(feedDao).execute(cantidad);
    }

    private static class cantidadLikes extends AsyncTask<String, Void, Void> {
        private FeedDao feedDao;

        private cantidadLikes(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedDao.cantidadLikes(modelFeeds[0]);

            return null;
        }
    }
    public void dislike(String like) {
        new dislike(feedDao).execute(like);
    }

    private static class dislike extends AsyncTask<String, Void, Void> {
        private FeedDao feedDao;

        private dislike(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedDao.disLike(modelFeeds[0]);

            return null;
        }
    }

    public void darlike(String like) {
        new darlike(feedDao).execute(like);
    }

    private static class darlike extends AsyncTask<String, Void, Void> {
        private FeedDao feedDao;

        private darlike(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedDao.darLike(modelFeeds[0]);

            return null;
        }
    }


    public void deleteAllFeed() {
        new DeleteAllFeedAsyncTask(feedDao).execute();
    }

    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void> {
        private FeedDao feedDao;

        private DeleteAllFeedAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            feedDao.deleteAll();
            Log.d("feed eliminado", "doInBackground: eliminado");
            return null;
        }
    }

    public void deleteOneFeed(String foro_id) {
        new DeleteOneFeedAsyncTask(feedDao).execute(foro_id);
    }


    public void insert(ModelFeed feedTorneo){
        new insertAsyncTask(feedDao).execute(feedTorneo);
    }

    private static class insertAsyncTask extends AsyncTask<ModelFeed, Void, Void> {

        private FeedDao feedDao;

        insertAsyncTask(FeedDao feedDao) {
            feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(final ModelFeed... params) {
            feedDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<ModelFeed> feedTorneo) {
        new insertPostsAsyncTask(feedDao).execute(feedTorneo);
    }

    private static class insertPostsAsyncTask extends AsyncTask<List<ModelFeed>, Void, Void> {

        private FeedDao mAsyncTaskDao;

        insertPostsAsyncTask(FeedDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<ModelFeed>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }




    private static class DeleteOneFeedAsyncTask extends AsyncTask<String, Void, Void> {
        private FeedDao feedDao;

        private DeleteOneFeedAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedDao.deleteOneFeed(modelFeeds[0]);

            return null;
        }
    }






    private static class ActualizarInf extends AsyncTask<String, Void, Void> {
        private FeedDao feedDao;

        private ActualizarInf(FeedDao feedDao)  {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedDao.actualizarInf(modelFeeds[0]);

            return null;
        }
    }

    private static class NuevosDatos extends AsyncTask<String, Void, Void> {
        private FeedDao feedDao;

        private NuevosDatos(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedDao.NuevosDatos(modelFeeds[0]);

            return null;
        }
    }
}
