package com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.EquiposUsuarios.Models.EquiposUsuarios;

import java.util.List;


public class EquiposUsuariosRoomDBRepository {

    private EquiposUsuariosDao datosUsuarioDao;
    LiveData<List<EquiposUsuarios>> mAllMiEquipos;

    public EquiposUsuariosRoomDBRepository(Application application){
        EquiposUsuariosRoomDataBase db = EquiposUsuariosRoomDataBase.getDatabase(application);
        datosUsuarioDao = db.postInfoDao();

     }

    public LiveData<List<EquiposUsuarios>> getmAllEquiposUsuarios(String id_user) {
         mAllMiEquipos=datosUsuarioDao.getAllEquiposUsuarios(id_user);
         return mAllMiEquipos;
    }



    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(datosUsuarioDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private EquiposUsuariosDao movimientosDao;

        private DeleteAllEquiposyncTask(EquiposUsuariosDao movimientosDao) {
            this.movimientosDao = movimientosDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movimientosDao.deleteAll();
            Log.d("eliminado equipos", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<EquiposUsuarios> menuModel) {
        new insertAsyncTask(datosUsuarioDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<EquiposUsuarios>, Void, Void> {

        private EquiposUsuariosDao mAsyncTaskDao;

        insertAsyncTask(EquiposUsuariosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<EquiposUsuarios>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
