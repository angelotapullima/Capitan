package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabTorneosDeEquipos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServiceTequipos {
    @FormUrlEncoded
    @POST("api/Torneo/listar_torneos_en_equipo")
    Call<String> getRetos(@Field("id_equipo") String id,
                          @Field("app") String app,
                          @Field("token") String token);
}
