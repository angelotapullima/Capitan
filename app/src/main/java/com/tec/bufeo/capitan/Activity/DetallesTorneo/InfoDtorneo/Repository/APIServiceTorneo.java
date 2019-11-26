package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServiceTorneo {
    @FormUrlEncoded
    @POST("index.php?c=Torneo&a=listar_publicaciones_por_id_torneo&key_mobile=123456asdfgh")
    Call<String> savePost(@Field("id_usuario") String id_usuario,
                          @Field("id_torneo") String id_torneo,
                          @Field("limite_sup") String limite_sup,
                          @Field("limite_inf") String limite_inf);
}
