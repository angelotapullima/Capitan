package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MisTorneoAPIService {

    @FormUrlEncoded
    @POST("index.php?c=Torneo&a=listar_mis_torneos&key_mobile=123456asdfgh")
    Call<String> getRetos(@Field("id_usuario") String id);
}


