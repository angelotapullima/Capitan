package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServiceJugadores {
    @FormUrlEncoded
    @POST("api/Torneo/buscar_jugadores_nuevos")
    Call<String> getJugadores(@Field("id_equipo") String id,
                           @Field("app") String app,
                           @Field("token") String token);
}
