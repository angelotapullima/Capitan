package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.FeedRoomDBRepository;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.FeedWebServiceRepository;

import java.util.List;



public class FeedListViewModel extends AndroidViewModel {


    private FeedRoomDBRepository feedTorneoRoomDBRepository;
    private LiveData<List<ModelFeed>> mAllPosts;
    FeedWebServiceRepository feedTorneoWebServiceRepository;
    private LiveData<List<ModelFeed>> retroObservable;


    public FeedListViewModel(Application application){
        super(application);
        feedTorneoRoomDBRepository = new FeedRoomDBRepository(application);
        feedTorneoWebServiceRepository = new FeedWebServiceRepository(application);
        //retroObservable = feedTorneoWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }

    public LiveData<List<ModelFeed>> getAllIdPosts() {
        //retroObservable = feedTorneoWebServiceRepository.providesWebService();
        mAllPosts = feedTorneoRoomDBRepository.getAllIdPosts();
        return mAllPosts;
    }
    public LiveData<List<ModelFeed>> getAllPosts() {
        //retroObservable = feedTorneoWebServiceRepository.providesWebService();
        mAllPosts = feedTorneoRoomDBRepository.getAllPosts();
        return mAllPosts;
    }

    public LiveData<List<ModelFeed>> getIdTorneo(String id_usuario,String token,String id_torneo) {
        retroObservable = feedTorneoWebServiceRepository.providesWebService(id_usuario,"0","0",token,id_torneo,"torneo");
        mAllPosts = feedTorneoRoomDBRepository.getIdTorneo(id_torneo);
        return mAllPosts;
    }

    public LiveData<List<ModelFeed>> getIdUsuario(String id_usuario,String token,String id_torneo) {
        retroObservable = feedTorneoWebServiceRepository.providesWebService(id_usuario,"0","0",token,id_torneo,"usuario");
        mAllPosts = feedTorneoRoomDBRepository.getIdUsuario(id_usuario);
        return mAllPosts;
    }
    public LiveData<List<ModelFeed>> getSearh(String query) {

        mAllPosts = feedTorneoRoomDBRepository.getSearch(query);
        return mAllPosts;
    }

    public void insert(ModelFeed feedTorneo){
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
