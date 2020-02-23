package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.ViewModels;

import android.app.Application;

import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Models.InstanciasModel;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository.InstanciasRoomDBRepository;
import com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository.InstanciasWebServiceRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class InstanciasViewModel extends AndroidViewModel {

    private InstanciasRoomDBRepository instanciasRoomDBRepository;
    private LiveData<List<InstanciasModel>> mAllPosts;
    InstanciasWebServiceRepository instanciasWebServiceRepository;
    private LiveData<List<InstanciasModel>> retroObservable;


    public InstanciasViewModel(Application application){
        super(application);
        instanciasRoomDBRepository = new InstanciasRoomDBRepository(application);
        instanciasWebServiceRepository = new InstanciasWebServiceRepository(application);
        //retroObservable = feedWebServiceRepository.providesWebService();
        //feedTorneoRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllPosts = feedTorneoRoomDBRepository.getAllPosts();

    }

    public LiveData<List<InstanciasModel>> getAllGrupos() {
        //retroObservable = feedWebServiceRepository.providesWebService();
        mAllPosts = instanciasRoomDBRepository.getAllGrupos();
        return mAllPosts;
    }



    public LiveData<List<InstanciasModel>> getIdTorneo(String id_torneo,String token) {
        retroObservable = instanciasWebServiceRepository.providesWebService(id_torneo,token);
        mAllPosts = instanciasRoomDBRepository.getIdTorneo(id_torneo);
        return mAllPosts;
    }


    public void insert(InstanciasModel instanciasModel){
        instanciasRoomDBRepository.insert(instanciasModel);
    }


    public void deleteAllFeed() {
        instanciasRoomDBRepository.deleteAllInstancias();
    }
   /*public LiveData<List<PublicacionesTorneo>> getProjectRetroListObservable() {
        return retroObservable;
    }*/

/*    public void insert(User user) {
        userRepository.insert(user);
    }*/
}
