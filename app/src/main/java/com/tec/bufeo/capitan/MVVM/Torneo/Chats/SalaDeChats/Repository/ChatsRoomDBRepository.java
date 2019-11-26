package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models.Chats;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Views.ForoFragment;

import java.util.List;

public class ChatsRoomDBRepository {
    private ChatsDao chatsDao;
    LiveData<List<Chats>> mAllComments;

    public ChatsRoomDBRepository(Application application){
        ChatsRoomDataBase db = ChatsRoomDataBase.getDatabase(application);
        chatsDao = db.chatsDao();
        //mAllComments = chatsDao.getAllComments();
     }

    public LiveData<List<Chats>> getAllComments() {

        boolean online = ForoFragment.isOnLine();

        if (online){
            deleteAllEquipos();
        }

        mAllComments = chatsDao.getAllChats();
        return mAllComments;
    }



    public void deleteAllEquipos() {
        new DeleteAllEquiposyncTask(chatsDao).execute();
    }

    private static class DeleteAllEquiposyncTask extends AsyncTask<Void, Void, Void> {
        private ChatsDao chatsDao;

        private DeleteAllEquiposyncTask(ChatsDao chatsDao) {
            this.chatsDao = chatsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            chatsDao.deleteAll();
            Log.i("eliminado", "doInBackground: eliminado");
            return null;
        }
    }





    public void deleteOne (String id){
        new deleteOneAsyncTask(chatsDao).execute(id);
    }

    private static  class  deleteOneAsyncTask extends  AsyncTask<String,Void,Void>{
        private ChatsDao mAsyncTaskDao;

        deleteOneAsyncTask(ChatsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(String... params) {
            mAsyncTaskDao.deleteOne(params[0]);
            return null;
        }
    }


    public void insertOneReviews(Chats menuModel) {
        new insertOneAsyncTask(chatsDao).execute(menuModel);
    }

    private static class insertOneAsyncTask extends AsyncTask<Chats, Void, Void> {

        private ChatsDao mAsyncTaskDao;

        insertOneAsyncTask(ChatsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Chats... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



    public void insertReviews(List<Chats> menuModel) {
        new insertAsyncTask(chatsDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Chats>, Void, Void> {

        private ChatsDao mAsyncTaskDao;

        insertAsyncTask(ChatsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Chats>... params) {
            mAsyncTaskDao.insertComments(params[0]);
            return null;
        }
    }
}
