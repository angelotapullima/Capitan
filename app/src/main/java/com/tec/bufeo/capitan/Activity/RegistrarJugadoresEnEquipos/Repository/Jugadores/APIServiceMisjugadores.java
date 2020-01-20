package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Repository.Jugadores;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServiceMisjugadores {
    @FormUrlEncoded
    @POST("api/Torneo/listar_detalle_equipo")
    Call<String> getMisJugadores(@Field("id_equipo") String id,
                              @Field("app") String app,
                              @Field("token") String token);
}
