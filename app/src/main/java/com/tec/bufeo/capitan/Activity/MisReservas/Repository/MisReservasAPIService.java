package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MisReservasAPIService {
    @FormUrlEncoded
    @POST("api/Empresa/listar_reservas_por_usuario")
    Call<String> getEquipo(@Field("id_usuario") String id_usuario,
                           @Field("app") String app,
                           @Field("token") String token);
}
