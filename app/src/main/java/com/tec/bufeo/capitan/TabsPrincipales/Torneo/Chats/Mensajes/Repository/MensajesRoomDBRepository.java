package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Models.Mensajes;

import java.util.List;

public class MensajesRoomDBRepository {
    private MensajesDao mensajesDao;
    LiveData<List<Mensajes>> mAllComments;

    public MensajesRoomDBRepository(Application application){
        MensajesRoomDataBase db = MensajesRoomDataBase.getDatabase(application);
        mensajesDao = db.postInfoDao();

     }

    public LiveData<List<Mensajes>> getAllMensajes(String chat_id) {



        mAllComments = mensajesDao.getAllMensajes(chat_id);
        return mAllComments;
    }









    public void deleteOne (String id){
        new deleteOneAsyncTask(mensajesDao).execute(id);
    }

    private static  class  deleteOneAsyncTask extends  AsyncTask<String,Void,Void>{
        private MensajesDao mAsyncTaskDao;

        deleteOneAsyncTask(MensajesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(String... params) {
            mAsyncTaskDao.deleteOne(params[0]);
            return null;
        }
    }


    public void insertOneReviews(Mensajes menuModel) {
        new insertOneAsyncTask(mensajesDao).execute(menuModel);
    }

    private static class insertOneAsyncTask extends AsyncTask<Mensajes, Void, Void> {

        private MensajesDao mAsyncTaskDao;

        insertOneAsyncTask(MensajesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Mensajes... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



    public void insertReviews(List<Mensajes> menuModel) {
        new insertAsyncTask(mensajesDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Mensajes>, Void, Void> {

        private MensajesDao mAsyncTaskDao;

        insertAsyncTask(MensajesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Mensajes>... params) {
            mAsyncTaskDao.insertComments(params[0]);
            return null;
        }
    }

    public void deleteAllMensajes() {
        new DeleteAllMensajesAsyncTask(mensajesDao).execute();
    }

    private static class DeleteAllMensajesAsyncTask extends AsyncTask<Void, Void, Void> {
        private MensajesDao feedDao;

        private DeleteAllMensajesAsyncTask(MensajesDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            feedDao.deleteAll();
            Log.d("eliminado mensajes", "doInBackground: eliminado");
            return null;
        }
    }
}
