package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels;

import android.app.Application;


import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.PublicacionesTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones.PublicacionesTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones.PublicacionesTorneoWebServiceRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;



public class PublicacionesTorneoViewModel extends AndroidViewModel {

    private PublicacionesTorneoRoomDBRepository feedTorneoRoomDBRepository;
    private LiveData<List<PublicacionesTorneo>> mAllPosts;
    PublicacionesTorneoWebServiceRepository feedTorneoWebServiceRepository;
    private LiveData<List<PublicacionesTorneo>> retroObservable;


    public PublicacionesTorneoViewModel(Application application){
        super(application);
        feedTorneoRoomDBRepository = new PublicacionesTorneoRoomDBRepository(application);
        feedTorneoWebServiceRepository = new PublicacionesTorneoWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }

    public LiveData<List<PublicacionesTorneo>> getAllIdPosts() {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = feedTorneoRoomDBRepository.getAllIdPosts();
        return mAllPosts;
    }
    public LiveData<List<PublicacionesTorneo>> getAllPosts() {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = feedTorneoRoomDBRepository.getAllPosts();
        return mAllPosts;
    }

    public LiveData<List<PublicacionesTorneo>> getIdTorneo(String id_torneo) {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = feedTorneoRoomDBRepository.getIdTorneo(id_torneo);
        return mAllPosts;
    }

    public LiveData<List<PublicacionesTorneo>> getSearh(String query) {

        mAllPosts = feedTorneoRoomDBRepository.getSearch(query);
        return mAllPosts;
    }

    public void insert(PublicacionesTorneo feedTorneo){
        feedTorneoRoomDBRepository.insert(feedTorneo);
    }


    public void deleteAllFeed() {
        feedTorneoRoomDBRepository.deleteAllFeed();
    }

    public void deleteOneFeed(String foro_id){
        feedTorneoRoomDBRepository.deleteOneFeed(foro_id);
    }
   /*public LiveData<List<PublicacionesTorneo>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
