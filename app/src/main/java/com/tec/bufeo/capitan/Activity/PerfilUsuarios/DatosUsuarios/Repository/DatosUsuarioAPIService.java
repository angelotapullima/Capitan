package com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DatosUsuarioAPIService {
    @FormUrlEncoded
    @POST("api/User/perfil_app")
    Call<String> getEquipo(@Field("id_user") String id_user,
                           @Field("app") String app,
                           @Field("token") String token);
}
