package com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServiceFeed {
    @FormUrlEncoded
    @POST("api/Foro/listar_publicaciones")
    Call<String> savePost(@Field("id_usuario") String id_usuario,
                          @Field("limite_sup") String limite_sup,
                          @Field("app") String app,
                          @Field("token") String token,
                          @Field("limite_inf") String limite_inf);
}
