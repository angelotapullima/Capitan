package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Models.Retos;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository.RetosRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository.RetosWebServiceRepository;

import java.util.ArrayList;
import java.util.List;

public class RetosViewModel extends AndroidViewModel {

    private RetosRoomDBRepository retosRoomDBRepository;
    private LiveData<List<Retos>> mAllRetos;
    RetosWebServiceRepository retosWebServiceRepository;


    public RetosViewModel(Application application){
        super(application);
        retosRoomDBRepository = new RetosRoomDBRepository(application);
        retosWebServiceRepository = new RetosWebServiceRepository(application);

    }

    public LiveData<List<Retos>> getAllRetos(String respuesta_reto) {

        mAllRetos = retosRoomDBRepository.getAllRetos(respuesta_reto);
        return mAllRetos;
    }



    public void insertOneReto(Retos retos){
        retosRoomDBRepository.inserOneReto(retos);
    }

    public void  ElimarRetos (){
        retosRoomDBRepository.deleteAllRetos();
    }
}
