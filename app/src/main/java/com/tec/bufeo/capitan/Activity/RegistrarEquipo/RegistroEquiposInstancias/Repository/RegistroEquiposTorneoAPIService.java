package com.tec.bufeo.capitan.Activity.RegistrarEquipo.RegistroEquiposInstancias.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegistroEquiposTorneoAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_equipos_en_torneo")
    Call<String> getEquipo(@Field("id_torneo") String id,
                           @Field("app") String app,
                           @Field("token") String token);
}
