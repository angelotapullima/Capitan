package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.DetalleTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Detalle.DetalleTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Detalle.DetalleTorneoWebServiceRepository;

import java.util.List;

public class DetalleTorneoViewModel extends AndroidViewModel {

    private DetalleTorneoRoomDBRepository detalleTorneoRoomDBRepository;
    private LiveData<List<DetalleTorneo>> mAllPosts;
    DetalleTorneoWebServiceRepository detalleTorneoWebServiceRepository;
    private LiveData<List<DetalleTorneo>> retroObservable;


    public DetalleTorneoViewModel(Application application){
        super(application);
        detalleTorneoRoomDBRepository = new DetalleTorneoRoomDBRepository(application);
        detalleTorneoWebServiceRepository = new DetalleTorneoWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }



    public LiveData<List<DetalleTorneo>> getIdTorneo(String id_torneo,String token) {
        retroObservable = detalleTorneoWebServiceRepository.providesWebService(id_torneo,token);
        mAllPosts = detalleTorneoRoomDBRepository.getIdTorneo(id_torneo);
        return mAllPosts;
    }



    public void insert(DetalleTorneo feedTorneo){
        detalleTorneoRoomDBRepository.insert(feedTorneo);
    }


    public void deleteAllFeed() {
        detalleTorneoRoomDBRepository.deleteAllFeed();
    }


}
