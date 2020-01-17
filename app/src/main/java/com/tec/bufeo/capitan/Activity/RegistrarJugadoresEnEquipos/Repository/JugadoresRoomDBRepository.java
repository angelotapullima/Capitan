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

    public LiveData<List<Jugadores>> getAllJugadores(String estado) {
        mAllJugadores=jugadoresDao.getAllJugadores(estado);
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


    public void EstadoSeleccionado(String id) {
        new EstadoSeleccionado(jugadoresDao).execute(id);
    }

    private static class EstadoSeleccionado extends AsyncTask<String, Void, Void> {
        private JugadoresDao misEquiposDao;

        private EstadoSeleccionado(JugadoresDao misEquiposDao)  {
            this.misEquiposDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            misEquiposDao.EstadoSeleccionado(modelFeeds[0]);

            return null;
        }
    }

    public void EstadoVacio(String id) {
        new EstadoVacio(jugadoresDao).execute(id);
    }

    private static class EstadoVacio extends AsyncTask<String, Void, Void> {
        private JugadoresDao misEquiposDao;

        private EstadoVacio(JugadoresDao misEquiposDao)  {
            this.misEquiposDao = misEquiposDao;
        }

        @Override
        protected Void doInBackground(String... modelFeeds) {
            misEquiposDao.EstadoVacio(modelFeeds[0]);

            return null;
        }
    }
}
