package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;

import java.util.List;

public class RetosRoomDBRepository {
    private RetosDao retosDao;
    LiveData<List<Retos>> mAllRetos;
    LiveData<List<Retos>> mAllRetosResulados;

    public RetosRoomDBRepository(Application application){
         RetosRoomDataBase db = RetosRoomDataBase.getDatabase(application);
        retosDao = db.postInfoDao();

     }

     public LiveData<List<Retos>>getRetosResultados(String id_reto){
         mAllRetosResulados = retosDao.getRetosResutado(id_reto);
         return mAllRetosResulados;
     }

     public LiveData<List<Retos>> getAll (){
         mAllRetos = retosDao.getAll();
         return mAllRetos;
     }

    public LiveData<List<Retos>> getAllRetoID (String id){
        mAllRetos = retosDao.getAllRetoID(id);
        return mAllRetos;
    }
    public LiveData<List<Retos>> getAllRetos( String respuesta_reto) {
        /*boolean online = ForoFragment.isOnLine();

        if (online){
            deleteAllRetos();
        }*/
        mAllRetos = retosDao.getAllRetos(respuesta_reto);
        return mAllRetos;
    }

    public void deleteAllRetos() {
        new DeleteAllRetosAsyncTask(retosDao).execute();
    }

    private static class DeleteAllRetosAsyncTask extends AsyncTask<Void, Void, Void> {
        private RetosDao retosDao;

        private DeleteAllRetosAsyncTask(RetosDao retosDao) {
            this.retosDao = retosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            retosDao.deleteAll();
            Log.d("eliminado retos", "doInBackground: eliminado");
            return null;
        }
    }


    public void insertRetos(List<Retos> menuModel) {
        new insertAsyncTask(retosDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Retos>, Void, Void> {

        private RetosDao mAsyncTaskDao;

        insertAsyncTask(RetosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Retos>... params) {
            mAsyncTaskDao.insertRetos(params[0]);
            return null;
        }
    }



    public void inserOneReto(Retos retos){
        new insertOneRetosAsyncTask(retosDao).execute(retos);
    }

    private static class insertOneRetosAsyncTask extends AsyncTask<Retos,Void,Void>{
        private RetosDao mAsyncTaskDao;

        insertOneRetosAsyncTask(RetosDao retosDao){
            mAsyncTaskDao = retosDao;
        }

        @Override
        protected Void doInBackground(final Retos... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
