package com.tec.bufeo.capitan.MVVM.Torneo.Chats.Mensajes.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MensajesAPIService {
    @FormUrlEncoded
    @POST("index.php?c=Usuario&a=listar_mensajes_por_chat&key_mobile=123456asdfgh")
    Call<String> makeRequest(@Field("id_chat") String id);
}
