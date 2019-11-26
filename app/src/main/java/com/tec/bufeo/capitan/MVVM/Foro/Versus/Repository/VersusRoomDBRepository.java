package com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.Versus.Models.Versus;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;

import java.util.List;

public class VersusRoomDBRepository {
    private VersusDao versusDao;
    LiveData<List<Versus>> mAllVersus;

    public VersusRoomDBRepository(Application application){
         VersusRoomDataBase db = VersusRoomDataBase.getDatabase(application);
        versusDao = db.postInfoDao();
        //mAllComments = commentsDao.getAllComments();
     }

    public LiveData<List<Versus>> getAllVersus() {

        boolean online = ForoFragment.isOnLine();



        mAllVersus = versusDao.getAllVersus();
        return mAllVersus;
    }



    public void deleteAllVersus() {
        new VersusRoomDBRepository.DeleteAllVersusAsyncTask(versusDao).execute();
    }

    private static class DeleteAllVersusAsyncTask extends AsyncTask<Void, Void, Void> {
        private VersusDao versusDao;

        private DeleteAllVersusAsyncTask(VersusDao versusDao) {
            this.versusDao = versusDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            versusDao.deleteAll();
            Log.i("eliminado", "doInBackground: eliminado");
            return null;
        }
    }


    public void insertOneReviews(Versus menuModel) {
        new insertOneAsyncTask(versusDao).execute(menuModel);
    }

    private static class insertOneAsyncTask extends AsyncTask<Versus, Void, Void> {

        private VersusDao mAsyncTaskDao;

        insertOneAsyncTask(VersusDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Versus... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



    public void insertVersus(List<Versus> menuModel) {
        new insertAsyncTask(versusDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Versus>, Void, Void> {

        private VersusDao mAsyncTaskDao;

        insertAsyncTask(VersusDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Versus>... params) {
            mAsyncTaskDao.insertComments(params[0]);
            return null;
        }
    }
}
