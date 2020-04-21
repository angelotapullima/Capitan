package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.Mensajes.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MensajesAPIService {
    @FormUrlEncoded
    @POST("api/User/listar_mensajes_por_chat")
    Call<String> makeRequest(@Field("id_chat") String id,
                             @Field("app") String app,
                             @Field("token") String token);
}
