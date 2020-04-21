package com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.TabsPrincipales.Foro.Notificaciones.Models.Notificaciones;

import java.util.List;


public class NotificacionesRoomDBRepository {

    private NotificacionesDao misReservasDao;
    LiveData<List<Notificaciones>> mAllMisReservas;

    public NotificacionesRoomDBRepository(Application application){
        NotificacionesRoomDataBase db = NotificacionesRoomDataBase.getDatabase(application);
        misReservasDao = db.postInfoDao();

     }

    public LiveData<List<Notificaciones>> getmAllNotificaciones() {
        mAllMisReservas=misReservasDao.getAllNotificaciones();
         return mAllMisReservas;
    }
    public LiveData<List<Notificaciones>> getAllNoVistos() {
        mAllMisReservas=misReservasDao.getAllNoVistos();
        return mAllMisReservas;
    }

    public void actualizarEstado(String sup) {
        new actualizarEstado(misReservasDao).execute(sup);
    }
    private static class actualizarEstado extends AsyncTask<String, Void, Void> {
        private NotificacionesDao feedDao;

        private actualizarEstado(NotificacionesDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            feedDao.updateEstado(modelFeeds[0]);

            return null;
        }
    }

    public void deleteAllMisReservas() {
        new DeleteAllReservasyncTask(misReservasDao).execute();
    }

    private static class DeleteAllReservasyncTask extends AsyncTask<Void, Void, Void> {
        private NotificacionesDao misReservasDao;

        private DeleteAllReservasyncTask(NotificacionesDao misReservasDao) {
            this.misReservasDao = misReservasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            misReservasDao.deleteAll();
            Log.d("eli 1notificaciones", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertEquipos(List<Notificaciones> menuModel) {
        new insertAsyncTask(misReservasDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Notificaciones>, Void, Void> {

        private NotificacionesDao mAsyncTaskDao;

        insertAsyncTask(NotificacionesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Notificaciones>... params) {
            mAsyncTaskDao.insertEquipo(params[0]);
            return null;
        }
    }
}
