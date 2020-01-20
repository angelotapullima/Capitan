package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommentsAPIService {
    @FormUrlEncoded
    @POST("index.php?c=Foro&a=listar_comentarios&key_mobile=123456asdfgh")
    Call<String> makeRequest(@Field("publicacion_id") String id,
                             @Field("app") String app,
                             @Field("token") String token);
}
