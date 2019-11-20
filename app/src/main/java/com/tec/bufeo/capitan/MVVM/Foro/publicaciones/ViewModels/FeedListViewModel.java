package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models.ModelFeed;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.FeedRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Repository.FeedWebServiceRepository;

import java.util.List;



public class FeedListViewModel extends AndroidViewModel {

    private FeedRoomDBRepository feedRoomDBRepository;
    private LiveData<List<ModelFeed>> mAllPosts;
    FeedWebServiceRepository feedWebServiceRepository;
    private LiveData<List<ModelFeed>> retroObservable;


    public FeedListViewModel(Application application){
        super(application);
        feedRoomDBRepository = new FeedRoomDBRepository(application);
        feedWebServiceRepository = new FeedWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedRoomDBRepository.getAllPosts();

    }

    public LiveData<List<ModelFeed>> getAllPosts() {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = feedRoomDBRepository.getAllPosts();
        return mAllPosts;
    }

    public LiveData<List<ModelFeed>> getSearh(String query) {

        mAllPosts = feedRoomDBRepository.getSearch(query);
        return mAllPosts;
    }

    public void insert(ModelFeed modelFeed){
        feedRoomDBRepository.insert(modelFeed);
    }


    public void deleteAllFeed() {
        feedRoomDBRepository.deleteAllFeed();
    }

    public void deleteOneFeed(String foro_id){
        feedRoomDBRepository.deleteOneFeed(foro_id);
    }
   /*public LiveData<List<ModelFeed>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
