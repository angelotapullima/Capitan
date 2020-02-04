package com.tec.bufeo.capitan.Activity.MisMovimientos.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.MisMovimientos.Models.Movimientos;

import java.util.List;


public class MovimientosRoomDBRepository {

    private MovimientosDao movimientosDao;
    LiveData<List<Movimientos>> mAllMiEquipos;

    public MovimientosRoomDBRepository(Application application){
         MovimientosRoomDataBase db = MovimientosRoomDataBase.getDatabase(application);
        movimientosDao = db.postInfoDao();

     }

    public LiveData<List<Movimientos>> getmAll() {
         mAllMiEquipos=movimientosDao.getAll();
         return mAllMiEquipos;
    }

    public LiveData<List<Movimientos>> getAllEquipo(  ) {

        mAllMiEquipos = movimientosDao.getAll();
        return mAllMiEquipos;
    }

    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(movimientosDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private MovimientosDao movimientosDao;

        private DeleteAllEquiposyncTask(MovimientosDao movimientosDao) {
            this.movimientosDao = movimientosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movimientosDao.deleteAll();
            Log.i("eliminado equipos", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<Movimientos> menuModel) {
        new insertAsyncTask(movimientosDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Movimientos>, Void, Void> {

        private MovimientosDao mAsyncTaskDao;

        insertAsyncTask(MovimientosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Movimientos>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
