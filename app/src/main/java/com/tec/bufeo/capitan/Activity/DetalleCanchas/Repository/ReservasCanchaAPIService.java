package com.tec.bufeo.capitan.Activity.DetalleCanchas.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ReservasCanchaAPIService {

    @FormUrlEncoded
    @POST("api/Empresa/listar_reservados_por_cancha_por_fecha")
    Call<String> savePost(@Field("id_cancha") String id_cancha,
                          @Field("fecha") String fecha,
                          @Field("app") String app,
                          @Field("token") String token);
}
