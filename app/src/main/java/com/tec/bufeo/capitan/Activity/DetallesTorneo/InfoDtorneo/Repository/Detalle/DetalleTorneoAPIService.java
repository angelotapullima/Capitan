package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository.Detalle;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DetalleTorneoAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_torneo_por_id")
    Call<String> savePost(@Field("id_torneo") String id_torneo,
                          @Field("app") String app,
                          @Field("token") String token);
}
