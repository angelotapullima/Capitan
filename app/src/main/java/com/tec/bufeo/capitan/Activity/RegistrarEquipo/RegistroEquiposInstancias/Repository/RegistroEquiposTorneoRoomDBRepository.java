package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Models.RegistroEquiposTorneo;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;

import java.util.List;

import androidx.lifecycle.LiveData;

public class RegistroEquiposTorneoRoomDBRepository {
    private RegistroEquiposTorneoDao registroEquiposTorneoDao;
    LiveData<List<RegistroEquiposTorneo>> mAllotros;

    public RegistroEquiposTorneoRoomDBRepository(Application application){
        RegistroEquiposTorneoRoomDataBase db = RegistroEquiposTorneoRoomDataBase.getDatabase(application);
        registroEquiposTorneoDao = db.postInfoDao();

     }




    public LiveData<List<RegistroEquiposTorneo>> getAllEquiposTorneo(  ) {
        //boolean online = ForoFragment.isOnLine();

        /*if (online){
            deleteAllEquipos();
        }*/
        mAllotros = registroEquiposTorneoDao.getAllEquiposTorneo();
        return mAllotros;
    }


    public LiveData<List<RegistroEquiposTorneo>> getLocal(){
        mAllotros = registroEquiposTorneoDao.getLocal();
        return mAllotros;
    }

    public LiveData<List<RegistroEquiposTorneo>> getVisitante(){
        mAllotros = registroEquiposTorneoDao.getVisitante();
        return mAllotros;
    }



    public void actualizarEstado0(String id) {
        new actualizarEstado0(registroEquiposTorneoDao).execute(id);
    }

    private static class actualizarEstado0 extends AsyncTask<String, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private actualizarEstado0(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            registroEquiposTorneoDao.actualizarEstado0(modelFeeds[0]);

            return null;
        }
    }

    public void actualizarEstado1(String id) {
        new actualizarEstado1(registroEquiposTorneoDao).execute(id);
    }

    private static class actualizarEstado1 extends AsyncTask<String, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private actualizarEstado1(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            registroEquiposTorneoDao.actualizarEstado1(modelFeeds[0]);

            return null;
        }
    }


    public void actualizarLocal1(String id) {
        new actualizarLocal1(registroEquiposTorneoDao).execute(id);
    }

    private static class actualizarLocal1 extends AsyncTask<String, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private actualizarLocal1(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            registroEquiposTorneoDao.actualizarlocal1(modelFeeds[0]);

            return null;
        }
    }

    public void actualizarLocal0(String id) {
        new actualizarLocal0(registroEquiposTorneoDao).execute(id);
    }

    private static class actualizarLocal0 extends AsyncTask<String, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private actualizarLocal0(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            registroEquiposTorneoDao.actualizarlocal0(modelFeeds[0]);

            return null;
        }
    }
    public void actualizarVisitante1(String id) {
        new actualizarVisitante1(registroEquiposTorneoDao).execute(id);
    }

    private static class actualizarVisitante1 extends AsyncTask<String, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private actualizarVisitante1(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            registroEquiposTorneoDao.actualizarvisita1(modelFeeds[0]);

            return null;
        }
    }

    public void actualizarVisitante0(String id) {
        new actualizarVisitante0(registroEquiposTorneoDao).execute(id);
    }

    private static class actualizarVisitante0 extends AsyncTask<String, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private actualizarVisitante0(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            registroEquiposTorneoDao.actualizarvisita0(modelFeeds[0]);

            return null;
        }
    }





    public void insertEquipos(List<RegistroEquiposTorneo> menuModel) {
        new insertAsyncTask(registroEquiposTorneoDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<RegistroEquiposTorneo>, Void, Void> {

        private RegistroEquiposTorneoDao mAsyncTaskDao;

        insertAsyncTask(RegistroEquiposTorneoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<RegistroEquiposTorneo>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }



    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(registroEquiposTorneoDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private DeleteAllEquiposyncTask(RegistroEquiposTorneoDao otrosEquiposDao) {
            this.registroEquiposTorneoDao = otrosEquiposDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            registroEquiposTorneoDao.deleteAll();
            Log.d("eliminado", "doInBackground: eliminado");
            return null;
        }
    }

    public void local0() {
        new local0(registroEquiposTorneoDao).execute();
    }

    private static class local0 extends AsyncTask<Void, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private local0(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            registroEquiposTorneoDao.local0();

            return null;
        }
    }

    public void visitante0() {
        new visitante0(registroEquiposTorneoDao).execute();
    }

    private static class visitante0 extends AsyncTask<Void, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private visitante0(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(Void... modelFeeds) {
            registroEquiposTorneoDao.visitante0();

            return null;
        }
    }


    public void Estado0() {
        new Estado0(registroEquiposTorneoDao).execute();
    }

    private static class Estado0 extends AsyncTask<Void, Void, Void> {
        private RegistroEquiposTorneoDao registroEquiposTorneoDao;

        private Estado0(RegistroEquiposTorneoDao registroEquiposTorneoDao)  {
            this.registroEquiposTorneoDao = registroEquiposTorneoDao;
        }

        @Override
        protected Void doInBackground(Void... modelFeeds) {
            registroEquiposTorneoDao.estado0();

            return null;
        }
    }
}
