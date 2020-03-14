package com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NotificacionesAPIService {
    @FormUrlEncoded
    @POST("api/User/listar_notificaciones")
    Call<String> getEquipo(@Field("id_usuario") String id_usuario,
                           @Field("app") String app,
                           @Field("token") String token);
}
