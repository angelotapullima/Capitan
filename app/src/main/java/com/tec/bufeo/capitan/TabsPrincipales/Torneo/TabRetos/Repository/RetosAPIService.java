package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabRetos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetosAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_mis_retos")
    Call<String> getRetos(@Field("id_usuario") String id,
                          @Field("app") String app,
                          @Field("token") String token);
}
