package com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;

import java.util.List;

public class BusquedaEquiposRoomDBRepository {
    private BusquedaEquiposDao equiposDao;
    LiveData<List<BusquedaEquipos>> mAllMiEquipos;

    public BusquedaEquiposRoomDBRepository(Application application){
        BusquedaEquiposRoomDataBase db = BusquedaEquiposRoomDataBase.getDatabase(application);
        equiposDao = db.postInfoDao();

     }




    public LiveData<List<BusquedaEquipos>> getmAll() {
         mAllMiEquipos=equiposDao.getAll();
         return mAllMiEquipos;
    }

    public LiveData<List<BusquedaEquipos>> getAllEquipo(  ) {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllEquipos();
        }*/
        mAllMiEquipos = equiposDao.getAllEquipo();
        return mAllMiEquipos;
    }








    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(equiposDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private BusquedaEquiposDao misEquiposDao;

        private DeleteAllEquiposyncTask(BusquedaEquiposDao misEquiposDao) {
            this.misEquiposDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misEquiposDao.deleteAll();
            Log.i("eliminado equipos", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<BusquedaEquipos> menuModel) {
        new insertAsyncTask(equiposDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<BusquedaEquipos>, Void, Void> {

        private BusquedaEquiposDao mAsyncTaskDao;

        insertAsyncTask(BusquedaEquiposDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<BusquedaEquipos>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
