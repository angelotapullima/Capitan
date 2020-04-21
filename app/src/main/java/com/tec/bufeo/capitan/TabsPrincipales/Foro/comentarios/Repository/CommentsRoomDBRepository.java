package com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.Models.Comments;

import java.util.List;

public class CommentsRoomDBRepository {
    private CommentsDao commentsDao;
    LiveData<List<Comments>> mAllComments;

    public CommentsRoomDBRepository(Application application){
         CommentsRoomDataBase db = CommentsRoomDataBase.getDatabase(application);
        commentsDao = db.postInfoDao();
        //mAllComments = commentsDao.getAllComments();
     }

    public LiveData<List<Comments>> getAllComments(String id) {



        mAllComments = commentsDao.getAllComments(id);
        return mAllComments;
    }









    public void deleteOne (String id){
        new deleteOneAsyncTask(commentsDao).execute(id);
    }

    private static  class  deleteOneAsyncTask extends  AsyncTask<String,Void,Void>{
        private CommentsDao mAsyncTaskDao;

        deleteOneAsyncTask(CommentsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.d("eliminando", "doInBackground: eliminado" );
            mAsyncTaskDao.deleteOne(params[0]);
            return null;
        }
    }


    public void insertOneReviews(Comments menuModel) {
        new insertOneAsyncTask(commentsDao).execute(menuModel);
    }

    private static class insertOneAsyncTask extends AsyncTask<Comments, Void, Void> {

        private CommentsDao mAsyncTaskDao;

        insertOneAsyncTask(CommentsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Comments... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



    public void insertReviews(List<Comments> menuModel) {
        new insertAsyncTask(commentsDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Comments>, Void, Void> {

        private CommentsDao mAsyncTaskDao;

        insertAsyncTask(CommentsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Comments>... params) {
            mAsyncTaskDao.insertComments(params[0]);
            return null;
        }
    }

    public void deleteAllComments() {
        new DeleteAllCommentsAsyncTask(commentsDao).execute();
    }

    private static class DeleteAllCommentsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CommentsDao instanciasDao;

        private DeleteAllCommentsAsyncTask(CommentsDao instanciasDao) {
            this.instanciasDao = instanciasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            instanciasDao.deleteAll();
            Log.d("eliminado Comments", "doInBackground: eliminado");
            return null;
        }
    }
}
