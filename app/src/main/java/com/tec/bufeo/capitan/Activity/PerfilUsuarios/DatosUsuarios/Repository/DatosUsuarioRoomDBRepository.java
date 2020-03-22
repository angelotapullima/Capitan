package com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;

import java.util.List;


public class DatosUsuarioRoomDBRepository {

    private DatosUsuarioDao datosUsuarioDao;
    LiveData<List<DatosUsuario>> mAllDatosUsuario;

    public DatosUsuarioRoomDBRepository(Application application){
         DatosUsuarioRoomDataBase db = DatosUsuarioRoomDataBase.getDatabase(application);
        datosUsuarioDao = db.postInfoDao();

     }

    public LiveData<List<DatosUsuario>> getAllDatosUsuario(String id_user) {
         mAllDatosUsuario=datosUsuarioDao.getAllDatosUsuario(id_user);
         return mAllDatosUsuario;
    }



    public void deleteAllDatosUsuario() {
        new DeleteAllDatosUsuarioyncTask(datosUsuarioDao).execute();
    }

    private static class DeleteAllDatosUsuarioyncTask extends AsyncTask<Void, Void, Void> {
        private DatosUsuarioDao datosUsuarioDao;

        private DeleteAllDatosUsuarioyncTask(DatosUsuarioDao datosUsuarioDao) {
            this.datosUsuarioDao = datosUsuarioDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            datosUsuarioDao.deleteAll();
            Log.d("eliminado equipos", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertdatosUsuarioDao(List<DatosUsuario> menuModel) {
        new insertAsyncTask(datosUsuarioDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<DatosUsuario>, Void, Void> {

        private DatosUsuarioDao mAsyncTaskDao;

        insertAsyncTask(DatosUsuarioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<DatosUsuario>... params) {
            mAsyncTaskDao.insertDatosUsuario(params[0]);
            return null;
        }
    }
}
