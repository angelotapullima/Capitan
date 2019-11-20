package com.tec.bufeo.capitan.MVVM.Torneo.TabEquipo.Repository.MisEquipos;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MisEquiposAPIService {
    @FormUrlEncoded
    @POST("index.php?c=Torneo&a=listar_equipos_por_id_usuario&key_mobile=123456asdfgh")
    Call<String> getEquipo(@Field("id_usuario") String id);
}
