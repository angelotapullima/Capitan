package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TorneosBusquedaAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/buscar_torneos")
    Call<String> getEquipo(@Field("dato") String dato,
                           @Field("app") String app,
                           @Field("token") String token);
}
