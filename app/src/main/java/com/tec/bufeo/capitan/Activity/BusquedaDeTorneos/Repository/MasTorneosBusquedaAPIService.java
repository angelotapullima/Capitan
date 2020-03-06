package com.tec.bufeo.capitan.Activity.BusquedaDeTorneos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MasTorneosBusquedaAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/buscar_torneos")
    Call<String> getEquipo(@Field("dato") String dato,
                           @Field("app") String app,
                           @Field("token") String token);
}
