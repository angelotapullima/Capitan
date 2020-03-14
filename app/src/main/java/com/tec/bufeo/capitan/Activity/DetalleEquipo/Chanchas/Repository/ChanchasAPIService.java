package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ChanchasAPIService {
    @FormUrlEncoded
    @POST("api/Empresa/obtener_chanchas_disponibles_por_equipo")
    Call<String> getEquipo(@Field("id_equipo") String id_equipo,
                           @Field("app") String app,
                           @Field("token") String token);
}
