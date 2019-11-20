package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Models.Chats;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository.ChatsRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository.ChatsWebServiceRepository;

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

    public LiveData<List<Chats>> getmAllChats(String id) {
        retroObservable = chatsWebServiceRepository.providesWebService(id);
        mAllComments = chatsRoomDBRepository.getAllComments();
        return mAllComments;
    }



    public void insertOne(Chats chats) {
        chatsRoomDBRepository.insertOneReviews(chats);
    }


}
