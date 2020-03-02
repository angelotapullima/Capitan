package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PublicacionesUsuarioAPIService {
    @FormUrlEncoded
    @POST("api/Foro/listar_publicaciones_por_id_usuario")
    Call<String> savePost(@Field("id_usuario") String id_usuario,
                          @Field("limite_sup") String limite_sup,
                          @Field("app") String app,
                          @Field("token") String token,
                          @Field("limite_inf") String limite_inf);
}
