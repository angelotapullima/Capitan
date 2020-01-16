package com.tec.bufeo.capitan.MVVM.Torneo.TabTorneo.MisTorneos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MisTorneoAPIService {

    @FormUrlEncoded
    @POST("api/Torneo/listar_mis_torneos")
    Call<String> getRetos(@Field("id_usuario") String id,
                          @Field("app") String app,
                          @Field("token") String token);
}


