package com.tec.bufeo.capitan.Activity.Registro_Torneo.CrearInstancias.RegistrarInstancias.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InstanciasAPIService {
    @FormUrlEncoded
    @POST("index.php?c=Torneo&a=listar_instancias_partidos_por_id_torneo&key_mobile=123456asdfgh")
    Call<String> savePost(@Field("id_torneo") String id_torneo);
}