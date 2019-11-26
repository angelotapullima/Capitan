package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.ViewModels;

import android.app.Application;

import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models.FeedTorneo;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.FeedTorneoRoomDBRepository;
import com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.FeedTorneoWebServiceRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;



public class FeedTorneoListViewModel extends AndroidViewModel {

    private FeedTorneoRoomDBRepository feedTorneoRoomDBRepository;
    private LiveData<List<FeedTorneo>> mAllPosts;
    FeedTorneoWebServiceRepository feedTorneoWebServiceRepository;
    private LiveData<List<FeedTorneo>> retroObservable;


    public FeedTorneoListViewModel(Application application){
        super(application);
        feedTorneoRoomDBRepository = new FeedTorneoRoomDBRepository(application);
        feedTorneoWebServiceRepository = new FeedTorneoWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }

    public LiveData<List<FeedTorneo>> getAllIdPosts() {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = feedTorneoRoomDBRepository.getAllIdPosts();
        return mAllPosts;
    }
    public LiveData<List<FeedTorneo>> getAllPosts() {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = feedTorneoRoomDBRepository.getAllPosts();
        return mAllPosts;
    }

    public LiveData<List<FeedTorneo>> getIdTorneo(String id_torneo) {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = feedTorneoRoomDBRepository.getIdTorneo(id_torneo);
        return mAllPosts;
    }

    public LiveData<List<FeedTorneo>> getSearh(String query) {

        mAllPosts = feedTorneoRoomDBRepository.getSearch(query);
        return mAllPosts;
    }

    public void insert(FeedTorneo feedTorneo){
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
