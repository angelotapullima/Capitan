package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Models.MasTorneos;

import java.util.List;

public class MasTorneosRoomDBRepository {
    private MasTorneosDao equiposDao;
    LiveData<List<MasTorneos>> mAllMiEquipos;

    public MasTorneosRoomDBRepository(Application application){
        MasTorneosRoomDataBase db = MasTorneosRoomDataBase.getDatabase(application);
        equiposDao = db.postInfoDao();

     }




    public LiveData<List<MasTorneos>> getmAll() {
         mAllMiEquipos=equiposDao.getAll();
         return mAllMiEquipos;
    }





    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(equiposDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private MasTorneosDao misEquiposDao;

        private DeleteAllEquiposyncTask(MasTorneosDao misEquiposDao) {
            this.misEquiposDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misEquiposDao.deleteAll();
            Log.i("eliminado equipos", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<MasTorneos> menuModel) {
        new insertAsyncTask(equiposDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<MasTorneos>, Void, Void> {

        private MasTorneosDao mAsyncTaskDao;

        insertAsyncTask(MasTorneosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<MasTorneos>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
