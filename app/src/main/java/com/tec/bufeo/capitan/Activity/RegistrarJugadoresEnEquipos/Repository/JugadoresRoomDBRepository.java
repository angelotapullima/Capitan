package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model.Jugadores;

import java.util.List;

import androidx.lifecycle.LiveData;

public class JugadoresRoomDBRepository {
    private JugadoresDao jugadoresDao;
    LiveData<List<Jugadores>> mAllJugadores;

    public JugadoresRoomDBRepository (Application application){
        JugadoresRoomDatabase db = JugadoresRoomDatabase.getDatabase(application);
        jugadoresDao=db.postInfoDao();
    }

    public LiveData<List<Jugadores>> getAllJugadores() {
        mAllJugadores=jugadoresDao.getAll();
        return mAllJugadores;
    }


    public void deleteAllJugadores() {
        new DeleteAlljugadoresyncTask(jugadoresDao).execute();
    }

    private static class DeleteAlljugadoresyncTask extends AsyncTask<Void, Void, Void> {
        private JugadoresDao jugadoresDao;

        private DeleteAlljugadoresyncTask(JugadoresDao jugadoresDao) {
            this.jugadoresDao = jugadoresDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            jugadoresDao.deleteAll();
            Log.i("eliminado jugadores", "doInBackground: eliminado");
            return null;
        }
    }



    public void insertJugadores(List<Jugadores> menuModel) {
        new insertAsyncTask(jugadoresDao).execute(menuModel);
    }

    private static class insertAsyncTask extends AsyncTask<List<Jugadores>, Void, Void> {

        private JugadoresDao mAsyncTaskDao;

        insertAsyncTask(JugadoresDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Jugadores>... params) {
            mAsyncTaskDao.insertJugadores(params[0]);
            return null;
        }
    }
}
