package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InstanciasAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_instancias_partidos_por_id_torneo")
    Call<String> savePost(@Field("id_torneo") String id_torneo,
                          @Field("app") String app,
                          @Field("token") String token);
}
