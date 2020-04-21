package com.tec.bufeo.capitan.TabsPrincipales.Torneo.TabEquipo.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MisEquiposAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_equipos_por_id_usuario")
    Call<String> getEquipo(@Field("id_usuario") String id,
                           @Field("app") String app,
                           @Field("token") String token);
}
