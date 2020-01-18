package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServiceBusquedaJugador {

    @FormUrlEncoded
    @POST("api/Torneo/buscar_jugadores_nickname")
    Call<String> getBuscarJugadores(@Field("id_equipo") String id,
                              @Field("app") String app,
                              @Field("token") String token,
                              @Field("dato") String dato);
}
