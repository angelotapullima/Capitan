package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.OtrosEquipos;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;
import com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Models.Mequipos;

import java.util.List;

public class OtrosEquiposRoomDBRepository {
    private OtrosEquiposDao otrosEquiposDao;
    LiveData<List<Mequipos>> mAllotros;

    public OtrosEquiposRoomDBRepository(Application application){
         OtrosEquiposRoomDataBase db = OtrosEquiposRoomDataBase.getDatabase(application);
        otrosEquiposDao = db.postInfoDao();

     }

    /*public LiveData<List<EquiposTorneo>> getAllEquipos(String mi_equipo) {
        boolean online = ForoFragment.isOnLine();

        if (online){
            deleteAllEquipos();
        }
        mAllEquipos = equiposDao.getAllEquipos(mi_equipo);
        return mAllEquipos;
    }*/


    public LiveData<List<Mequipos>> getAllOtrosEquipos(String no ) {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllEquipos();
        }*/
        mAllotros = otrosEquiposDao.getAllOtrosEquipos(no);
        return mAllotros;
    }





    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(otrosEquiposDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private OtrosEquiposDao otrosEquiposDao;

        private DeleteAllEquiposyncTask(OtrosEquiposDao otrosEquiposDao) {
            this.otrosEquiposDao = otrosEquiposDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            otrosEquiposDao.deleteAll();
            Log.i("eliminado", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<Mequipos> menuModel) {
        new insertAsyncTask(otrosEquiposDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Mequipos>, Void, Void> {

        private OtrosEquiposDao mAsyncTaskDao;

        insertAsyncTask(OtrosEquiposDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Mequipos>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
