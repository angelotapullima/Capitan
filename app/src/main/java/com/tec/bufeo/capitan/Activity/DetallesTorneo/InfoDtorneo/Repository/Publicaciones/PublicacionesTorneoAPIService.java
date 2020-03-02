package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Publicaciones;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PublicacionesTorneoAPIService{
    @FormUrlEncoded
    @POST("api/Torneo/listar_publicaciones_por_id_torneo")
    Call<String> savePost(@Field("id_usuario") String id_usuario,
                          @Field("id_torneo") String id_torneo,
                          @Field("limite_sup") String limite_sup,
                          @Field("app") String app,
                          @Field("token") String token,
                          @Field("limite_inf") String limite_inf);
}
