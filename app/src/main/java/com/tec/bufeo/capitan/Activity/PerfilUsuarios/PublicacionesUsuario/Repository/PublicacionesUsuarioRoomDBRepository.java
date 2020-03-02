package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository.PublicacionesUsuarioDao;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Models.PublicacionesUsuario;

import java.util.List;


public class PublicacionesUsuarioRoomDBRepository {


    private PublicacionesUsuarioDao feedTorneoDao;
    LiveData<List<PublicacionesUsuario>> mAllPosts;

    public PublicacionesUsuarioRoomDBRepository(Application application){
        PublicacionesUsuarioRoomDataBase db = PublicacionesUsuarioRoomDataBase.getDatabase(application);
        feedTorneoDao = db.postInfoDao();
        //emAllPosts = feedTorneoDao.getAllPosts();
    }




    public LiveData<List<PublicacionesUsuario>> getAllPosts(String id_user) {


        mAllPosts = feedTorneoDao.getAllPosts(id_user);
        return mAllPosts;
    }

    public void actualizarSup(String sup) {
        new ActualizarSup(feedTorneoDao).execute(sup);
    }

    public void ActualizarInf(String inf) {
        new ActualizarInf(feedTorneoDao).execute(inf);
    }





    public void deleteAllFeed() {
        new DeleteAllFeedAsyncTask(feedTorneoDao).execute();
    }

    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void> {
        private PublicacionesUsuarioDao feedTorneoDao;

        private DeleteAllFeedAsyncTask(PublicacionesUsuarioDao feedTorneoDao) {
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


    public void insert(PublicacionesUsuario feedTorneo){
        new insertAsyncTask(feedTorneoDao).execute(feedTorneo);
    }

    private static class insertAsyncTask extends AsyncTask<PublicacionesUsuario, Void, Void> {

        private PublicacionesUsuarioDao mAsyncTaskDao;

        insertAsyncTask(PublicacionesUsuarioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PublicacionesUsuario... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<PublicacionesUsuario> feedTorneo) {
        new insertPostsAsyncTask(feedTorneoDao).execute(feedTorneo);
    }

    private static class insertPostsAsyncTask extends AsyncTask<List<PublicacionesUsuario>, Void, Void> {

        private PublicacionesUsuarioDao mAsyncTaskDao;

        insertPostsAsyncTask(PublicacionesUsuarioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<PublicacionesUsuario>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }




    private static class DeleteOneFeedAsyncTask extends AsyncTask<String, Void, Void> {
        private PublicacionesUsuarioDao feedTorneoDao;

        private DeleteOneFeedAsyncTask(PublicacionesUsuarioDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.deleteOneFeed(modelFeeds[0]);

            return null;
        }
    }




    private static class ActualizarSup extends AsyncTask<String, Void, Void> {
        private PublicacionesUsuarioDao feedTorneoDao;

        private ActualizarSup(PublicacionesUsuarioDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.actualizarSup(modelFeeds[0]);

            return null;
        }
    }

    private static class ActualizarInf extends AsyncTask<String, Void, Void> {
        private PublicacionesUsuarioDao feedTorneoDao;

        private ActualizarInf(PublicacionesUsuarioDao feedTorneoDao) {
            this.feedTorneoDao = feedTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedTorneoDao.actualizarInf(modelFeeds[0]);

            return null;
        }
    }


}
