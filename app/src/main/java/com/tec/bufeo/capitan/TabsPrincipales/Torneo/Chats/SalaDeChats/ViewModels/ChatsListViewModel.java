package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Models.Chats;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Repository.ChatsRoomDBRepository;
import com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Repository.ChatsWebServiceRepository;

import java.util.List;

public class ChatsListViewModel extends AndroidViewModel {

    private ChatsRoomDBRepository chatsRoomDBRepository;
    private LiveData<List<Chats>> mAllComments;
    ChatsWebServiceRepository chatsWebServiceRepository;
    private LiveData<List<Chats>> retroObservable;


    public ChatsListViewModel(Application application){
        super(application);
        chatsRoomDBRepository = new ChatsRoomDBRepository(application);
        chatsWebServiceRepository = new ChatsWebServiceRepository(application);

    }

    public LiveData<List<Chats>> getmAllChats() {
        //retroObservable = chatsWebServiceRepository.providesWebService(id,token);
        mAllComments = chatsRoomDBRepository.getAllComments();
        return mAllComments;
    }



    public void insertOne(Chats chats) {
        chatsRoomDBRepository.insertOneReviews(chats);
    }


}
