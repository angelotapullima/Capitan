package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;

import java.util.List;

public class OtrosTorneosRoomDBRepository {
    private OtrosTorneosDao otrosTorneosDao;
    LiveData<List<Torneo>> mAllRetos;

    public OtrosTorneosRoomDBRepository(Application application){
         OtrosTorneosRoomDataBase db = OtrosTorneosRoomDataBase.getDatabase(application);
        otrosTorneosDao = db.postInfoDao();

     }

    public LiveData<List<Torneo>> getAllOtrosTorneo(String no) {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllRetos();
        }*/
        mAllRetos = otrosTorneosDao.getAllOtrosTorneos(no);
        return mAllRetos;
    }

    public void deleteAllRetos() {
        new DeleteAllRetosAsyncTask(otrosTorneosDao).execute();
    }

    private static class DeleteAllRetosAsyncTask extends AsyncTask<Void, Void, Void> {
        private OtrosTorneosDao retosDao;

        private DeleteAllRetosAsyncTask(OtrosTorneosDao retosDao) {
            this.retosDao = retosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            retosDao.deleteAll();
            Log.e("eliminado otros retos", "doInBackground: eliminado");
            return null;
        }
    }


    public void insertRetos(List<Torneo> menuModel) {
        new insertAsyncTask(otrosTorneosDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Torneo>, Void, Void> {

        private OtrosTorneosDao mAsyncTaskDao;

        insertAsyncTask(OtrosTorneosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Torneo>... params) {
            mAsyncTaskDao.insertRetos(params[0]);
            return null;
        }
    }



    public void inserOneReto(Torneo mRetos){
        new insertOneRetosAsyncTask(otrosTorneosDao).execute(mRetos);
    }

    private static class insertOneRetosAsyncTask extends AsyncTask<Torneo,Void,Void>{
        private OtrosTorneosDao mAsyncTaskDao;

        insertOneRetosAsyncTask(OtrosTorneosDao retosDao){
            mAsyncTaskDao = retosDao;
        }

        @Override
        protected Void doInBackground(final Torneo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
