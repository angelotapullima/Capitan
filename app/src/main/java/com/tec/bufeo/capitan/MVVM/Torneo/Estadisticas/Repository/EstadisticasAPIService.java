package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EstadisticasAPIService {
    @FormUrlEncoded
    @POST("api/Torneo/listar_estadisticas")
    Call<String> getEquipo(@Field("id_usuario") String id,
                           @Field("app") String app,
                           @Field("token") String token);
}
