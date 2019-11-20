package com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Repository;

import android.os.AsyncTask;

public class MensajesDbAsync extends AsyncTask<Void, Void, Void> {

    private final MensajesDao mensajesDao;

    MensajesDbAsync(MensajesRoomDataBase db) {
        mensajesDao = db.postInfoDao();
    }



    @Override
    protected Void doInBackground(final Void... params) {


        return null;
    }
}
