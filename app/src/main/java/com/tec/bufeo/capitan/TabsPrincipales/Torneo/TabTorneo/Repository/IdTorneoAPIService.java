package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabTorneo.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IdTorneoAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_torneo_por_id")
    Call<String> getIdTorneo(@Field("id_torneo") String id_torneo,
                          @Field("app") String app,
                          @Field("token") String token);
}
