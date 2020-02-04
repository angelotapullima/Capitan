package com.tec.bufeo.capitan.Activity.MisMovimientos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MovimientosAPIService {
    @FormUrlEncoded
    @POST("api/User/listar_pagos_por_id_usuario")
    Call<String> getEquipo(@Field("id_user") String id_user,
                           @Field("app") String app,
                           @Field("token") String token);
}
