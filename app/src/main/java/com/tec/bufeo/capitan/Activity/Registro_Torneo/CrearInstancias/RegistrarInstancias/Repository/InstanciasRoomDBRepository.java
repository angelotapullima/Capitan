package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Models.InstanciasModel;

import java.util.List;

import androidx.lifecycle.LiveData;

public class InstanciasRoomDBRepository {

    private InstanciasDao instanciasDao;
    LiveData<List<InstanciasModel>> mAllPosts;

    public InstanciasRoomDBRepository(Application application){
        InstanciasRoomDataBase db = InstanciasRoomDataBase.getDatabase(application);
        instanciasDao = db.postInfoDao();
        //emAllPosts = gruposTorneoDao.getAllPosts();
    }



    public LiveData<List<InstanciasModel>> getIdTorneo(String id_torneo) {


        mAllPosts = instanciasDao.getIdTorneo(id_torneo);
        return mAllPosts;
    }
    public LiveData<List<InstanciasModel>> getAllGrupos() {


        mAllPosts = instanciasDao.getAllInstancias();
        return mAllPosts;
    }



    public void deleteAllInstancias() {
        new DeleteAllFeedAsyncTask(instanciasDao).execute();
    }

    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void> {
        private InstanciasDao instanciasDao;

        private DeleteAllFeedAsyncTask(InstanciasDao instanciasDao) {
            this.instanciasDao = instanciasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            instanciasDao.deleteAll();
            Log.i("instancias", "doInBackground: eliminado");
            return null;
        }
    }




    public void insert(InstanciasModel instanciasModel){
        new insertAsyncTask(instanciasDao).execute(instanciasModel);
    }

    private static class insertAsyncTask extends AsyncTask<InstanciasModel, Void, Void> {

        private InstanciasDao instanciasDao;

        insertAsyncTask(InstanciasDao dao) {
            instanciasDao = dao;
        }

        @Override
        protected Void doInBackground(final InstanciasModel... params) {
            instanciasDao.insert(params[0]);
            return null;
        }
    }
    public void insertPosts (List<InstanciasModel> instanciasModels) {
        new insertPostsAsyncTask(instanciasDao).execute(instanciasModels);
    }

    private static class insertPostsAsyncTask extends AsyncTask<List<InstanciasModel>, Void, Void> {

        private InstanciasDao instanciasDao;

        insertPostsAsyncTask(InstanciasDao dao) {
            instanciasDao = dao;
        }

        @Override
        protected Void doInBackground(final List<InstanciasModel>... params) {
            instanciasDao.insertPosts(params[0]);
            return null;
        }
    }


}