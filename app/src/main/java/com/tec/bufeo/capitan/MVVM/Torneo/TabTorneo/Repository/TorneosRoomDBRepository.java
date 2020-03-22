package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.Models.Torneo;

import java.util.List;

public class TorneosRoomDBRepository {
    private TorneosDao misTorneoDao;
    LiveData<List<Torneo>> misTorneos;

    public TorneosRoomDBRepository(Application application){
         TorneosRoomDataBase db = TorneosRoomDataBase.getDatabase(application);
        misTorneoDao = db.postInfoDao();

     }

    public LiveData<List<Torneo>> getAllMisTorneo(String si) {

        misTorneos = misTorneoDao.getAllMisTorneos(si);
        return misTorneos;
    }

    public LiveData<List<Torneo>> getIdTorneo(String id) {

        misTorneos = misTorneoDao.getIdTorneo(id);
        return misTorneos;
    }



    public LiveData<List<Torneo>> searchTorneo(String query){
        misTorneos = misTorneoDao.searchTorneo(query);
        return misTorneos;

    }
    public LiveData<List<Torneo>> getAllOtrosTorneo(String no) {

        misTorneos = misTorneoDao.getAllOtrosTorneos(no);
        return misTorneos;
    }
    public void DeleteAllTorneosAsyncTask() {
        new DeleteAllTorneosAsyncTask(misTorneoDao).execute();
    }

    private static class DeleteAllTorneosAsyncTask extends AsyncTask<Void, Void, Void> {
        private TorneosDao retosDao;

        private DeleteAllTorneosAsyncTask(TorneosDao retosDao) {
            this.retosDao = retosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            retosDao.deleteAll();
            Log.d("eliminado  torneos", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertTorneo(List<Torneo> menuModel) {
        new insertAsyncTask(misTorneoDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Torneo>, Void, Void> {

        private TorneosDao mAsyncTaskDao;

        insertAsyncTask(TorneosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Torneo>... params) {
            mAsyncTaskDao.insertTorneo(params[0]);
            return null;
        }
    }




}
