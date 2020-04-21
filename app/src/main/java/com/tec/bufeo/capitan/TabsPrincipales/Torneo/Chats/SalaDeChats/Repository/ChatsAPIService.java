package com.tec.bufeo.capitan.TabsPrincipales.Torneo.Chats.SalaDeChats.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ChatsAPIService {


    @FormUrlEncoded
    @POST("api/User/listar_chats_por_id_usuario")
    Call<String> makeRequest(@Field("id_user") String id,
                             @Field("app") String app,
                             @Field("token") String token);
}
