package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Models.PublicacionesUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository.PublicacionesUsuarioRoomDBRepository;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository.PublicacionesUsuarioWebServiceRepository;

import java.util.List;

public class PublicacionesUsuarioViewModel extends AndroidViewModel {

    private PublicacionesUsuarioRoomDBRepository publicacionesUsuarioRoomDBRepository;
    private LiveData<List<PublicacionesUsuario>> mAllPosts;
    PublicacionesUsuarioWebServiceRepository publicacionesUsuarioWebServiceRepository;
    private LiveData<List<PublicacionesUsuario>> retroObservable;


    public PublicacionesUsuarioViewModel(Application application){
        super(application);
        publicacionesUsuarioRoomDBRepository = new PublicacionesUsuarioRoomDBRepository(application);
        publicacionesUsuarioWebServiceRepository = new PublicacionesUsuarioWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }


    public LiveData<List<PublicacionesUsuario>> getAllPosts(String id_user, String superior ,String inferior,String token) {
        retroObservable = publicacionesUsuarioWebServiceRepository.providesWebService(id_user,superior,inferior,token);
        mAllPosts = publicacionesUsuarioRoomDBRepository.getAllPosts(id_user);
        return mAllPosts;
    }



    public void insert(PublicacionesUsuario feedTorneo){
        publicacionesUsuarioRoomDBRepository.insert(feedTorneo);
    }


    public void deleteAllFeed() {
        publicacionesUsuarioRoomDBRepository.deleteAllFeed();
    }

    public void deleteOneFeed(String foro_id){
        publicacionesUsuarioRoomDBRepository.deleteOneFeed(foro_id);
    }
   /*public LiveData<List<PublicacionesTorneo>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/

}
