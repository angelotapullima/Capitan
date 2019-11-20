package com.tec.bufeo.capitan.MVVM.Torneo.Estadisticas.Repository;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EstadisticasAPIService {
    @FormUrlEncoded
    @POST("index.php?c=Torneo&a=listar_estadisticas&key_mobile=123456asdfgh")
    Call<String> getEquipo(@Field("id_usuario") String id);
}
