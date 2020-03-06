package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquipoEnGrupo.Model.EquiposGrupo;

import java.util.List;

public class EquiposGrupoRoomDBRepository {
    private EquiposGrupoDao equiposDao;
    LiveData<List<EquiposGrupo>> mAllMiEquipos;

    public EquiposGrupoRoomDBRepository(Application application){
        EquiposGrupoRoomDataBase db = EquiposGrupoRoomDataBase.getDatabase(application);
        equiposDao = db.postInfoDao();

     }





    public LiveData<List<EquiposGrupo>> getAllEquipoGrupo(  ) {

        mAllMiEquipos = equiposDao.getAllEquipoGrupo();
        return mAllMiEquipos;
    }


    public void deleteID(String id) {
        new DeleteIDEquiposyncTask(equiposDao).execute(id);
    }

    private static class DeleteIDEquiposyncTask extends AsyncTask<String , Void, Void> {
        private EquiposGrupoDao equiposGrupoDao;

        private DeleteIDEquiposyncTask(EquiposGrupoDao misEquiposDao) {
            this.equiposGrupoDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            equiposGrupoDao.deleteID( strings[0]);
            Log.i("eliminado equipos", "doInBackground: eliminado");
            return null;
        }
    }




    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(equiposDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private EquiposGrupoDao misEquiposDao;

        private DeleteAllEquiposyncTask(EquiposGrupoDao misEquiposDao) {
            this.misEquiposDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misEquiposDao.deleteAll();
            Log.i("eliminado equipos", "doInBackground: eliminado");
            return null;
        }
    }




    public void insertSeleccionados(EquiposGrupo menuModel) {
        new insertSeleccionados(equiposDao).execute(menuModel);
    }

    private static class insertSeleccionados extends AsyncTask<EquiposGrupo, Void, Void> {

        private EquiposGrupoDao mAsyncTaskDao;

        insertSeleccionados(EquiposGrupoDao dao) {
            mAsyncTaskDao = dao;
        }



        @Override
        protected Void doInBackground(EquiposGrupo... jugadoresSeleccionados) {
            mAsyncTaskDao.insert(jugadoresSeleccionados[0]);
            return null;
        }
    }

    public void insertEquipos(List<EquiposGrupo> menuModel) {
        new insertAsyncTask(equiposDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<EquiposGrupo>, Void, Void> {

        private EquiposGrupoDao mAsyncTaskDao;

        insertAsyncTask(EquiposGrupoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<EquiposGrupo>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
