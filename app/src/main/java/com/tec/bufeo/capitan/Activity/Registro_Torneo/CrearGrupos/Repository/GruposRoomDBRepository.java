package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;

import java.util.List;

import androidx.lifecycle.LiveData;


public class GruposRoomDBRepository {
    private GruposTorneoDao gruposTorneoDao;
    LiveData<List<Grupos>> mAllPosts;

    public GruposRoomDBRepository(Application application){
         GruposRoomDataBase db = GruposRoomDataBase.getDatabase(application);
        gruposTorneoDao = db.postInfoDao();
        //emAllPosts = gruposTorneoDao.getAllPosts();
     }



    public LiveData<List<Grupos>> getIdTorneo(String id_torneo) {


        mAllPosts = gruposTorneoDao.getIdTorneo(id_torneo);
        return mAllPosts;
    }
    public LiveData<List<Grupos>> getAllGrupos() {


        mAllPosts = gruposTorneoDao.getAllGrupos();
        return mAllPosts;
    }



    public void deleteAllGrupos() {
        new DeleteAllFeedAsyncTask(gruposTorneoDao).execute();
    }

    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void> {
        private GruposTorneoDao gruposTorneoDao;

        private DeleteAllFeedAsyncTask(GruposTorneoDao gruposTorneoDao) {
            this.gruposTorneoDao = gruposTorneoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            gruposTorneoDao.deleteAll();
            Log.d("eliminado", "doInBackground: eliminado");
            return null;
        }
    }




    public void insert(Grupos feedTorneo){
        new insertAsyncTask(gruposTorneoDao).execute(feedTorneo);
    }

    private static class insertAsyncTask extends AsyncTask<Grupos, Void, Void> {

        private GruposTorneoDao mAsyncTaskDao;

        insertAsyncTask(GruposTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Grupos... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<Grupos> feedTorneo) {
        new insertPostsAsyncTask(gruposTorneoDao).execute(feedTorneo);
    }

    private static class insertPostsAsyncTask extends AsyncTask<List<Grupos>, Void, Void> {

        private GruposTorneoDao mAsyncTaskDao;

        insertPostsAsyncTask(GruposTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Grupos>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }

}
