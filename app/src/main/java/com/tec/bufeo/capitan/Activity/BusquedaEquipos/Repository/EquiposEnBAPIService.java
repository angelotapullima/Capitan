package com.tec.bufeo.capitan.Activity.BusquedaEquipos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EquiposEnBAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_equipos")
    Call<String> getEquipo(@Field("limite_sup") String limite_sup,
                           @Field("limite_inf") String limite_inf,
                           @Field("app") String app,
                           @Field("token") String token);
}
