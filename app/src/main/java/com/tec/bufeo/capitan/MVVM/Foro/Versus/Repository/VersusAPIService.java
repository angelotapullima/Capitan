package com.tec.bufeo.capitan.MVVM.Foro.Versus.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface VersusAPIService {
    @FormUrlEncoded
    @POST("hambre/angelo/versus.php")
    Call<String> makeRequest(@Field("publicacion_id") String id,
                             @Field("app") String app,
                             @Field("token") String token);
}
