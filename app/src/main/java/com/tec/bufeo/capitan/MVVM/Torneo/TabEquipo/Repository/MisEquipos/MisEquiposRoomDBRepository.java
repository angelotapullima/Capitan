package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;

import java.util.List;

public class MisEquiposRoomDBRepository {
    private MisEquiposDao equiposDao;
    LiveData<List<Mequipos>> mAllMiEquipos;

    public MisEquiposRoomDBRepository(Application application){
         MisEquiposRoomDataBase db = MisEquiposRoomDataBase.getDatabase(application);
        equiposDao = db.postInfoDao();

     }

    /*public LiveData<List<Mequipos>> getAllEquipos(String mi_equipo) {
        boolean online = ForoFragment.isOnLine();

        if (online){
            deleteAllEquipos();
        }
        mAllEquipos = equiposDao.getAllEquipos(mi_equipo);
        return mAllEquipos;
    }*/


    public LiveData<List<Mequipos>> getAllMiEquipo(String si ) {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllEquipos();
        }*/
        mAllMiEquipos = equiposDao.getAllMIEquipo(si);
        return mAllMiEquipos;
    }





    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(equiposDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private MisEquiposDao misEquiposDao;

        private DeleteAllEquiposyncTask(MisEquiposDao misEquiposDao) {
            this.misEquiposDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misEquiposDao.deleteAll();
            Log.i("eliminado", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<Mequipos> menuModel) {
        new insertAsyncTask(equiposDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Mequipos>, Void, Void> {

        private MisEquiposDao mAsyncTaskDao;

        insertAsyncTask(MisEquiposDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Mequipos>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
