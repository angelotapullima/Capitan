package com.tec.bufeo.capitan.Activity.ReviewsComentarios.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "reseñas")
public class Reseñas
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_rating_empresa")
    @SerializedName("id_rating_empresa")
    private String id_rating_empresa;

    @ColumnInfo(name = "rating_empresa_valor")
    @SerializedName("rating_empresa_valor")
    private String rating_empresa_valor;

    @ColumnInfo(name = "rating_empresa_comentario")
    @SerializedName("rating_empresa_comentario")
    private String rating_empresa_comentario;

    @ColumnInfo(name = "id_user")
    @SerializedName("id_user")
    private String id_user;

    @ColumnInfo(name = "user_nickname")
    @SerializedName("user_nickname")
    private String user_nickname;


    @ColumnInfo(name = "user_image")
    @SerializedName("user_image")
    private String user_image;

    @NonNull
    public String getId_rating_empresa() {
        return id_rating_empresa;
    }

    public void setId_rating_empresa(@NonNull String id_rating_empresa) {
        this.id_rating_empresa = id_rating_empresa;
    }

    public String getRating_empresa_valor() {
        return rating_empresa_valor;
    }

    public void setRating_empresa_valor(String rating_empresa_valor) {
        this.rating_empresa_valor = rating_empresa_valor;
    }

    public String getRating_empresa_comentario() {
        return rating_empresa_comentario;
    }

    public void setRating_empresa_comentario(String rating_empresa_comentario) {
        this.rating_empresa_comentario = rating_empresa_comentario;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}