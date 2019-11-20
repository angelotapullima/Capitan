package com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Repository.MensajesRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Repository.MensajesWebServiceRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Models.Mensajes;

import java.util.List;

public class MensajesViewModel extends AndroidViewModel {

    private MensajesRoomDBRepository mensajesRoomDBRepository;
    private LiveData<List<Mensajes>> mAllComments;
    private AsyncTask<String, Void, LiveData<List<Mensajes>>> mComments;
    MensajesWebServiceRepository mensajesWebServiceRepository;
    private LiveData<List<Mensajes>> retroObservable;


    public MensajesViewModel(Application application){
        super(application);
        mensajesRoomDBRepository = new MensajesRoomDBRepository(application);
        mensajesWebServiceRepository = new MensajesWebServiceRepository(application);
        //retroObservable = mensajesWebServiceRepository.providesWebService(id);
        //menuRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllComments= mensajesRoomDBRepository.getAllComments(id);
    }

    public LiveData<List<Mensajes>> getmAllMensajes(String chat_id) {
        retroObservable = mensajesWebServiceRepository.providesWebService(chat_id);
        mAllComments = mensajesRoomDBRepository.getAllMensajes(chat_id);
        return mAllComments;
    }



   /* public LiveData<List<ResultModel>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

    public void insertOne(Mensajes comments) {
        mensajesRoomDBRepository.insertOneReviews(comments);
    }


}
