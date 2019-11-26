package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Models.Torneo;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository.OtrosTorneosRoomDBRepository;
import com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.OtrosTorneos.Repository.OtrosTorneosWebServiceRepository;

import java.util.List;

public class OtrosTorneosViewModel extends AndroidViewModel {

    private OtrosTorneosRoomDBRepository otrosTorneosRoomDBRepository;

    private LiveData<List<Torneo>> mAllTorneo;

    OtrosTorneosWebServiceRepository otrosTorneosWebServiceRepository;

    private  LiveData<List<Torneo>> retroObservable;


    public OtrosTorneosViewModel(Application application){
        super(application);
        otrosTorneosRoomDBRepository = new OtrosTorneosRoomDBRepository(application);
        otrosTorneosWebServiceRepository = new OtrosTorneosWebServiceRepository(application);
        //retroObservable = retosWebServiceRepository.providesWebService();
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        //mAllRetos = retosRoomDBRepository.getAllRetos();
    }

    public LiveData<List<Torneo>> getAllOtrosTorneos(String id_usuario, String no) {
        retroObservable = otrosTorneosWebServiceRepository.providesWebService(id_usuario);
        //listPlatosRoomDBRepository.insertPosts(retroObservable.getValue());
        mAllTorneo = otrosTorneosRoomDBRepository.getAllOtrosTorneo(no);
        return mAllTorneo;
    }

    public void insertOneReto(Torneo mRetos){
        otrosTorneosRoomDBRepository.inserOneReto(mRetos);
    }

}
