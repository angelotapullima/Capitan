package com.tec.bufeo.capitan.Activity.Negocios.Repository.Negocios;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NegociosAPIService {
    @FormUrlEncoded
    @POST("api/Empresa/listar_empresas_por_id_ciudad")
    Call<String> getEquipo(@Field("id_ciudad") String id_ciudad,
                           @Field("id_usuario") String id_usuario,
                           @Field("app") String app,
                           @Field("token") String token);
}
