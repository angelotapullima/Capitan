package com.tec.bufeo.capitan.MVVM.Foro.comentarios.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Comments")
public class Comments {

    public Comments() {
    }



    @PrimaryKey()
    @NonNull@ColumnInfo(name = "comments_id")
    @SerializedName("comments_id")
    private String comments_id;


    @ColumnInfo(name = "publicacion_id")
    @SerializedName("publicacion_id")
    private String publicacion_id;


    @ColumnInfo(name = "comments_foto")
    @SerializedName("comments_foto")
    private String comments_foto;

    @ColumnInfo(name = "comments_fecha")
    @SerializedName("comments_fecha")
    private String comments_fecha;

    @ColumnInfo(name = "comments_nombre")
    @SerializedName("comments_nombre")
    private String comments_nombre;

    @ColumnInfo(name = "comments_comentario")
    @SerializedName("comments_comentario")
    private String comments_comentario;

    @ColumnInfo(name = "id_usuario")
    @SerializedName("id_usuario")
    private String id_usuario;


    @NonNull
    public String getComments_id() {
        return comments_id;
    }

    public void setComments_id(@NonNull String comments_id) {
        this.comments_id = comments_id;
    }

    public String getPublicacion_id() {
        return publicacion_id;
    }

    public void setPublicacion_id(String publicacion_id) {
        this.publicacion_id = publicacion_id;
    }

    public String getComments_foto() {
        return comments_foto;
    }

    public void setComments_foto(String comments_foto) {
        this.comments_foto = comments_foto;
    }

    public String getComments_fecha() {
        return comments_fecha;
    }

    public void setComments_fecha(String comments_fecha) {
        this.comments_fecha = comments_fecha;
    }

    public String getComments_nombre() {
        return comments_nombre;
    }

    public void setComments_nombre(String comments_nombre) {
        this.comments_nombre = comments_nombre;
    }

    public String getComments_comentario() {
        return comments_comentario;
    }

    public void setComments_comentario(String comments_comentario) {
        this.comments_comentario = comments_comentario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
