package com.tec.bufeo.capitan.Activity.MisReservas.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MisReservasNotificacionAPIService {
    @FormUrlEncoded
    @POST("api/Empresa/listar_reserva_por_id")
    Call<String> getEquipo(@Field("id_reserva") String id_reserva,
                           @Field("app") String app,
                           @Field("token") String token);
}
