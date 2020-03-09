package com.tec.bufeo.capitan.Activity.Negocios.Repository.Canchas;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CanchasAPIService {
    @FormUrlEncoded
    @POST("api/Empresa/listar_canchas_por_id_empresa")
    Call<String> getEquipo(@Field("id_empresa") String id_empresa,
                           @Field("app") String app,
                           @Field("token") String token);
}
