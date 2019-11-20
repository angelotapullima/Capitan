package com.tec.bufeo.capitan.MVVM.Torneo.TabRetos.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetosAPIService {
    @FormUrlEncoded
    @POST("index.php?c=Torneo&a=listar_mis_retos&key_mobile=123456asdfgh")
    Call<String> getRetos(@Field("id_usuario") String id);
}
