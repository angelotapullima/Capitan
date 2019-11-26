package com.tec.bufeo.capitan.Activity.DetallesTorneo.EquiposDtorneo.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EquiposTorneoAPIService {
    @FormUrlEncoded
    @POST("index.php?c=Torneo&a=listar_equipos_en_torneo&key_mobile=123456asdfgh")
    Call<String> getEquipo(@Field("id_torneo") String id);
}
