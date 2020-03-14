package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;

import java.util.List;


public class MisReservasRoomDBRepository {

    private MisReservasDao misReservasDao;
    LiveData<List<MisReservas>> mAllMisReservas;

    public MisReservasRoomDBRepository(Application application){
        MisReservasRoomDataBase db = MisReservasRoomDataBase.getDatabase(application);
        misReservasDao = db.postInfoDao();

     }

    public LiveData<List<MisReservas>> getmAll() {
        mAllMisReservas=misReservasDao.getAll();
         return mAllMisReservas;
    }

    public LiveData<List<MisReservas>> getAllID(String id) {
        mAllMisReservas=misReservasDao.getAllID(id);
        return mAllMisReservas;
    }

    public void deleteAllMisReservas() {
        new DeleteAllReservasyncTask(misReservasDao).execute();
    }

    private static class DeleteAllReservasyncTask extends AsyncTask<Void, Void, Void> {
        private MisReservasDao misReservasDao;

        private DeleteAllReservasyncTask(MisReservasDao misReservasDao) {
            this.misReservasDao = misReservasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misReservasDao.deleteAll();
            Log.i("eliminado mis reservas", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<MisReservas> menuModel) {
        new insertAsyncTask(misReservasDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<MisReservas>, Void, Void> {

        private MisReservasDao mAsyncTaskDao;

        insertAsyncTask(MisReservasDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<MisReservas>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
