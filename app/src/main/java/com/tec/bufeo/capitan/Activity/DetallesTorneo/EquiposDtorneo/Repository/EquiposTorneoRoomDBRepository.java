package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Models.EquiposTorneo;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;

import java.util.List;

public class EquiposTorneoRoomDBRepository {
    private EquiposTorneoDao equiposTorneoDao;
    LiveData<List<EquiposTorneo>> mAllotros;

    public EquiposTorneoRoomDBRepository(Application application){
        EquiposTorneoRoomDataBase db = EquiposTorneoRoomDataBase.getDatabase(application);
        equiposTorneoDao = db.postInfoDao();

     }

    /*public LiveData<List<RegistroEquiposTorneo>> getAllEquipos(String mi_equipo) {
        boolean online = ForoFragment.isOnLine();

        if (online){
            deleteAllEquipos();
        }
        mAllEquipos = equiposDao.getAllEquipos(mi_equipo);
        return mAllEquipos;
    }*/


    public LiveData<List<EquiposTorneo>> getAllEquiposTorneo(String equipo_torneo ) {
        boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllEquipos();
        }*/
        mAllotros = equiposTorneoDao.getAllEquiposTorneo(equipo_torneo);
        return mAllotros;
    }





    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(equiposTorneoDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private EquiposTorneoDao equiposTorneoDao;

        private DeleteAllEquiposyncTask(EquiposTorneoDao otrosEquiposDao) {
            this.equiposTorneoDao = otrosEquiposDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            equiposTorneoDao.deleteAll();
            Log.i("eliminado", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<EquiposTorneo> menuModel) {
        new insertAsyncTask(equiposTorneoDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<EquiposTorneo>, Void, Void> {

        private EquiposTorneoDao mAsyncTaskDao;

        insertAsyncTask(EquiposTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<EquiposTorneo>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
