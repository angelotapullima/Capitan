package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ReseñasAPIService {
    @FormUrlEncoded
    @POST("api/Empresa/listar_valoraciones")
    Call<String> getEquipo(@Field("id_empresa") String id_empresa,
                           @Field("app") String app,
                           @Field("token") String token);
}
