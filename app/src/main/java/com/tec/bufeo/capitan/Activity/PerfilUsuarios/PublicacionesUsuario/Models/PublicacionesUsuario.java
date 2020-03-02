package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "publicaciones_usuario")
public class PublicacionesUsuario
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "publicacion_id")
    @SerializedName("publicacion_id")
    private String publicacion_id;

    @ColumnInfo(name = "usuario_nombre")
    @SerializedName("usuario_nombre")
    private String usuario_nombre;

    @ColumnInfo(name = "foro_foto")
    @SerializedName("foro_foto")
    private String foro_foto;

    @ColumnInfo(name = "foro_titulo")
    @SerializedName("foro_titulo")
    private String foro_titulo;


    @ColumnInfo(name = "foro_descripcion")
    @SerializedName("foro_descripcion")
    private String foro_descripcion;



    @ColumnInfo(name = "usuario_id")
    @SerializedName("usuario_id")
    private String usuario_id;

    @ColumnInfo(name = "foro_feccha")
    @SerializedName("foro_feccha")
    private String foro_feccha;


    @ColumnInfo(name = "cant_likes")
    @SerializedName("cant_likes")
    private String cant_likes;

    @ColumnInfo(name = "cant_Comentarios")
    @SerializedName("cant_Comentarios")
    private String cant_Comentarios;

    @ColumnInfo(name = "dio_like")
    @SerializedName("dio_like")
    private String dio_like;

    @ColumnInfo(name = "usuario_foto")
    @SerializedName("usuario_foto")
    private String usuario_foto;

    @ColumnInfo(name = "orden")
    @SerializedName("orden")
    private String orden;

    @ColumnInfo(name = "limite_sup")
    @SerializedName("limite_sup")
    private String limite_sup;

    @ColumnInfo(name = "limite_inf")
    @SerializedName("limite_inf")
    private String limite_inf;

    @ColumnInfo(name = "estado")
    @SerializedName("estado")
    private String estado;




    @NonNull
    public String getPublicacion_id() {
        return publicacion_id;
    }

    public void setPublicacion_id(@NonNull String publicacion_id) {
        this.publicacion_id = publicacion_id;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getForo_foto() {
        return foro_foto;
    }

    public void setForo_foto(String foro_foto) {
        this.foro_foto = foro_foto;
    }

    public String getForo_titulo() {
        return foro_titulo;
    }

    public void setForo_titulo(String foro_titulo) {
        this.foro_titulo = foro_titulo;
    }

    public String getForo_descripcion() {
        return foro_descripcion;
    }

    public void setForo_descripcion(String foro_descripcion) {
        this.foro_descripcion = foro_descripcion;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getForo_feccha() {
        return foro_feccha;
    }

    public void setForo_feccha(String foro_feccha) {
        this.foro_feccha = foro_feccha;
    }

    public String getCant_likes() {
        return cant_likes;
    }

    public void setCant_likes(String cant_likes) {
        this.cant_likes = cant_likes;
    }

    public String getCant_Comentarios() {
        return cant_Comentarios;
    }

    public void setCant_Comentarios(String cant_Comentarios) {
        this.cant_Comentarios = cant_Comentarios;
    }

    public String getDio_like() {
        return dio_like;
    }

    public void setDio_like(String dio_like) {
        this.dio_like = dio_like;
    }

    public String getUsuario_foto() {
        return usuario_foto;
    }

    public void setUsuario_foto(String usuario_foto) {
        this.usuario_foto = usuario_foto;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getLimite_sup() {
        return limite_sup;
    }

    public void setLimite_sup(String limite_sup) {
        this.limite_sup = limite_sup;
    }

    public String getLimite_inf() {
        return limite_inf;
    }

    public void setLimite_inf(String limite_inf) {
        this.limite_inf = limite_inf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}