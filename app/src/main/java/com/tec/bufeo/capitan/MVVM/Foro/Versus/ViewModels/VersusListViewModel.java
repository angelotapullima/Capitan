package com.tec.bufeo.capitan.MVVM.Foro.Versus.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;


import com.tec.bufeo.capitan.MVVM.Foro.Versus.Models.Versus;
import com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository.VersusRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository.VersusWebServiceRepository;

import java.util.List;

public class VersusListViewModel extends AndroidViewModel {

    private VersusRoomDBRepository versusRoomDBRepository;
    private LiveData<List<Versus>> mAllComments;
    private AsyncTask<String, Void, LiveData<List<Versus>>> mComments;
    VersusWebServiceRepository versusWebServiceRepository;
    private LiveData<List<Versus>> retroObservable;


    public VersusListViewModel(Application application){
        super(application);
        versusRoomDBRepository = new VersusRoomDBRepository(application);
        versusWebServiceRepository = new VersusWebServiceRepository(application);
        //retroObservable = commentsWebServiceRepository.providesWebService(id);
        //menuRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllComments= commentsRoomDBRepository.getAllComments(id);
    }

    public LiveData<List<Versus>> getmAllReviews(String id) {
        retroObservable = versusWebServiceRepository.providesWebService(id);
        mAllComments = versusRoomDBRepository.getAllVersus();
        return mAllComments;
    }



   /* public LiveData<List<ResultModel>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

    public void insertOne(Versus comments) {
        versusRoomDBRepository.insertOneReviews(comments);
    }


}
