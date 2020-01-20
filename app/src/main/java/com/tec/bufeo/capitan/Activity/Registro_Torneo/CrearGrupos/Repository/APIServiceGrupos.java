package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearGrupos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServiceGrupos {
    @FormUrlEncoded
    @POST("api/Torneo/listar_grupos_por_id_torneo")
    Call<String> savePost(@Field("id_torneo") String id_torneo,
                          @Field("app") String app,
                          @Field("token") String token);
}
