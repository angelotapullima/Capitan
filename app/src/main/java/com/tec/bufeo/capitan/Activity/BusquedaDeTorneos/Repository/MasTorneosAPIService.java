package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MasTorneosAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_torneos")
    Call<String> getEquipo(@Field("id_usuario") String id_usuario,
                           @Field("app") String app,
                           @Field("token") String token);
}
