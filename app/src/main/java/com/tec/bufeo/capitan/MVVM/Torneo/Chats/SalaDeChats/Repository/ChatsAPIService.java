package com.tec.bufeo.capitan.MVVM.Torneo.Chats.SalaDeChats.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ChatsAPIService {


    @FormUrlEncoded
    @POST("index.php?c=Usuario&a=listar_chats_por_id_usuario&key_mobile=123456asdfgh")
    Call<String> makeRequest(@Field("id_usuario") String id);
}
