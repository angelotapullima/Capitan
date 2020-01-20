package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.ViewModels;

import android.app.Application;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Models.Grupos;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository.GruposRoomDBRepository;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository.GruposWebServiceRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class GruposListViewModel extends AndroidViewModel {

    private GruposRoomDBRepository gruposRoomDBRepository;
    private LiveData<List<Grupos>> mAllPosts;
    GruposWebServiceRepository gruposWebServiceRepository;
    private LiveData<List<Grupos>> retroObservable;


    public GruposListViewModel(Application application){
        super(application);
        gruposRoomDBRepository = new GruposRoomDBRepository(application);
        gruposWebServiceRepository = new GruposWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }

    public LiveData<List<Grupos>> getAllGrupos() {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = gruposRoomDBRepository.getAllGrupos();
        return mAllPosts;
    }



    public LiveData<List<Grupos>> getIdTorneo(String id_torneo,String token) {
        retroObservable = gruposWebServiceRepository.providesWebService(id_torneo,token);
        mAllPosts = gruposRoomDBRepository.getIdTorneo(id_torneo);
        return mAllPosts;
    }


    public void insert(Grupos grupos){
        gruposRoomDBRepository.insert(grupos);
    }


    public void deleteAllFeed() {
        gruposRoomDBRepository.deleteAllGrupos();
    }
   /*public LiveData<List<PublicacionesTorneo>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
