package com.tec.bufeo.capitan.MVVM.Foro.comentarios.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Models.Comments;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository.CommentsRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository.CommentsWebServiceRepository;

import java.util.List;

public class CommentsListViewModel extends AndroidViewModel {

    private CommentsRoomDBRepository commentsRoomDBRepository;
    private LiveData<List<Comments>> mAllComments;
    private AsyncTask<String, Void, LiveData<List<Comments>>> mComments;
    CommentsWebServiceRepository commentsWebServiceRepository;
    private LiveData<List<Comments>> retroObservable;


    public CommentsListViewModel(Application application){
        super(application);
        commentsRoomDBRepository = new CommentsRoomDBRepository(application);
        commentsWebServiceRepository = new CommentsWebServiceRepository(application);
        //retroObservable = commentsWebServiceRepository.providesWebService(id);
        //menuRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllComments= commentsRoomDBRepository.getAllComments(id);
    }

    public LiveData<List<Comments>> getmAllReviews(String id,String token) {
        retroObservable = commentsWebServiceRepository.providesWebService(id,token);
        mAllComments = commentsRoomDBRepository.getAllComments(id);
        return mAllComments;
    }



   /* public LiveData<List<ResultModel>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

    public void insertOne(Comments comments) {
        Log.d("ingreso coment : ", comments.toString());
        commentsRoomDBRepository.insertOneReviews(comments);
    }


}
