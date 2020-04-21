package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Repository;

import android.os.AsyncTask;

public class ChatsDbAsync extends AsyncTask<Void, Void, Void> {

    ChatsDbAsync(ChatsRoomDataBase db) {
        ChatsDao chatsDao = db.chatsDao();
    }



    @Override
    protected Void doInBackground(final Void... params) {

        return null;
    }
}
