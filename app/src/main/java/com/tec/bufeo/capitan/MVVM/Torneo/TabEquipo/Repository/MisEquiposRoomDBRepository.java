package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
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




    public LiveData<List<Mequipos>> getmAll() {
         mAllMiEquipos=equiposDao.getAll();
         return mAllMiEquipos;
    }

    public LiveData<List<Mequipos>> getAllEquipo(String mio ) {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllEquipos();
        }*/
        mAllMiEquipos = equiposDao.getAllEquipo(mio);
        return mAllMiEquipos;
    }




    public void actualizarEstado0(String id) {
        new actualizarEstado0(equiposDao).execute(id);
    }

    private static class actualizarEstado0 extends AsyncTask<String, Void, Void> {
        private MisEquiposDao misEquiposDao;

        private actualizarEstado0(MisEquiposDao misEquiposDao)  {
            this.misEquiposDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            misEquiposDao.actualizarEstado0(modelFeeds[0]);

            return null;
        }
    }

    public void actualizarEstado1(String id) {
        new actualizarEstado1(equiposDao).execute(id);
    }

    private static class actualizarEstado1 extends AsyncTask<String, Void, Void> {
        private MisEquiposDao misEquiposDao;

        private actualizarEstado1(MisEquiposDao misEquiposDao)  {
            this.misEquiposDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            misEquiposDao.actualizarEstado1(modelFeeds[0]);

            return null;
        }
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
            Log.i("eliminado equipos", "doInBackground: eliminado");
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
