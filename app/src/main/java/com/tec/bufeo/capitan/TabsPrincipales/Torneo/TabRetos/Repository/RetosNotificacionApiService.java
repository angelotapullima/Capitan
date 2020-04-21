package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetosNotificacionApiService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_reto_por_id")
    Call<String> getRetos(@Field("id_reto") String id_reto,
                          @Field("app") String app,
                          @Field("token") String token);
}
