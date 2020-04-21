package com.tec.bufeo.capitan.TabsPrincipales.Foro.comentarios.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommentsAPIService {
    @FormUrlEncoded
    @POST("api/Foro/listar_comentarios")
    Call<String> makeRequest(@Field("publicacion_id") String id,
                             @Field("app") String app,
                             @Field("token") String token);
}
