package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;

import java.util.List;

public class MisTorneoRoomDBRepository {
    private MisTorneoDao misTorneoDao;
    LiveData<List<Torneo>> mAllRetos;

    public MisTorneoRoomDBRepository(Application application){
         MisTorneoRoomDataBase db = MisTorneoRoomDataBase.getDatabase(application);
        misTorneoDao = db.postInfoDao();

     }

    public LiveData<List<Torneo>> getAllMisTorneo(String si) {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllRetos();
        }*/
        mAllRetos = misTorneoDao.getAllMisTorneos(si);
        return mAllRetos;
    }

    public void deleteAllRetos() {
        new DeleteAllRetosAsyncTask(misTorneoDao).execute();
    }

    private static class DeleteAllRetosAsyncTask extends AsyncTask<Void, Void, Void> {
        private MisTorneoDao retosDao;

        private DeleteAllRetosAsyncTask(MisTorneoDao retosDao) {
            this.retosDao = retosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            retosDao.deleteAll();
            Log.i("eliminado mis torneos", "doInBackground: eliminado");
            return null;
        }
    }


    public void insertRetos(List<Torneo> menuModel) {
        new insertAsyncTask(misTorneoDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Torneo>, Void, Void> {

        private MisTorneoDao mAsyncTaskDao;

        insertAsyncTask(MisTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Torneo>... params) {
            mAsyncTaskDao.insertRetos(params[0]);
            return null;
        }
    }



    public void inserOneReto(Torneo mRetos){
        new insertOneRetosAsyncTask(misTorneoDao).execute(mRetos);
    }

    private static class insertOneRetosAsyncTask extends AsyncTask<Torneo,Void,Void>{
        private MisTorneoDao mAsyncTaskDao;

        insertOneRetosAsyncTask(MisTorneoDao retosDao){
            mAsyncTaskDao = retosDao;
        }

        @Override
        protected Void doInBackground(final Torneo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
