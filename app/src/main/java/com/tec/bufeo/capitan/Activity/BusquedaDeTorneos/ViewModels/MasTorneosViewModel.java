package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Models.MasTorneos;
import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository.MasTorneosRoomDBRepository;
import com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository.MasTorneosWebServiceRepository;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Models.BusquedaEquipos;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposRoomDBRepository;
import com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository.BusquedaEquiposWebServiceRepository;

import java.util.List;

public class MasTorneosViewModel extends AndroidViewModel {

    private MasTorneosRoomDBRepository misEquiposRoomDBRepository;
    public LiveData<List<MasTorneos>> mAllMiEquipos;


    MasTorneosWebServiceRepository misEquiposWebServiceRepository;


    public  LiveData<List<MasTorneos>> retroObservable;
    public  LiveData<List<MasTorneos>> MiequiporetroObservable;


    public MasTorneosViewModel(Application application ){
        super(application);
        misEquiposRoomDBRepository = new MasTorneosRoomDBRepository(application);
        misEquiposWebServiceRepository =  new MasTorneosWebServiceRepository(application);

    }

    public LiveData<List<MasTorneos>> getmAll(String id_usuario,String token ) {

        misEquiposWebServiceRepository.providesWebService("carga",id_usuario,"",token);
        mAllMiEquipos = misEquiposRoomDBRepository.getmAll();
        return mAllMiEquipos;
    }



    /*public LiveData<List<MasTorneos>> getAll() {
        mAllMiEquipos=misEquiposRoomDBRepository.getmAll();
        return mAllMiEquipos;
    }
*/

}
