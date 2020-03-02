package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.MisReservas.Models.MisReservas;

import java.util.List;


public class MisReservasRoomDBRepository {

    private MisReservasDao movimientosDao;
    LiveData<List<MisReservas>> mAllMiEquipos;

    public MisReservasRoomDBRepository(Application application){
        MisReservasRoomDataBase db = MisReservasRoomDataBase.getDatabase(application);
        movimientosDao = db.postInfoDao();

     }

    public LiveData<List<MisReservas>> getmAll() {
         mAllMiEquipos=movimientosDao.getAll();
         return mAllMiEquipos;
    }

    public LiveData<List<MisReservas>> getAllEquipo(  ) {

        mAllMiEquipos = movimientosDao.getAll();
        return mAllMiEquipos;
    }

    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(movimientosDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private MisReservasDao movimientosDao;

        private DeleteAllEquiposyncTask(MisReservasDao movimientosDao) {
            this.movimientosDao = movimientosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movimientosDao.deleteAll();
            Log.i("eliminado equipos", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<MisReservas> menuModel) {
        new insertAsyncTask(movimientosDao).execute(menuModel);
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
