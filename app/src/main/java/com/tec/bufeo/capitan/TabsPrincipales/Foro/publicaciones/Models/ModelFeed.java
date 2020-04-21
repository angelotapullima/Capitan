package com.tec.bufeo.capitan.TabsPrincipales.Foro.publicaciones.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "feed")
public class ModelFeed {

    /*@PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "foro_id")
    @SerializedName("foro_id")
    private int foro_id;*/


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

    @ColumnInfo(name = "publicacion_concepto")
    @SerializedName("publicacion_concepto")
    private String publicacion_concepto;


    @ColumnInfo(name = "id_torneo")
    @SerializedName("id_torneo")
    private String id_torneo;

    @ColumnInfo(name = "torneo_foto")
    @SerializedName("torneo_foto")
    private String torneo_foto;


    @ColumnInfo(name = "publicacion_torneo")
    @SerializedName("publicacion_torneo")
    private String publicacion_torneo;



    @ColumnInfo(name = "usuario_id")
    @SerializedName("usuario_id")
    private String usuario_id;

    @ColumnInfo(name = "foro_feccha")
    @SerializedName("foro_feccha")
    private String foro_feccha;



    @ColumnInfo(name = "foro_tipo")
    @SerializedName("foro_tipo")
    private String foro_tipo;

    @ColumnInfo(name = "cant_likes")
    @SerializedName("cant_likes")
    private String cant_likes;

    @ColumnInfo(name = "cant_Comentarios")
    @SerializedName("cant_Comentarios")
    private String cant_Comentarios;


    //para confirmar si el usuario dio o no dio like
    // 0 para el que no dio like y 1 para que el si lo dio
    @ColumnInfo(name = "dio_like")
    @SerializedName("dio_like")
    private String dio_like;

    @ColumnInfo(name = "usuario_foto")
    @SerializedName("usuario_foto")
    private String usuario_foto;

    //dato paea mostrar las nuevas publicaciones en primer lugar
    //0 datos antiguos , 1 para datos nuevos al publicar
    @ColumnInfo(name = "orden")
    @SerializedName("orden")
    private String orden;

    @ColumnInfo(name = "limite_sup")
    @SerializedName("limite_sup")
    private String limite_sup;

    @ColumnInfo(name = "limite_inf")
    @SerializedName("limite_inf")
    private String limite_inf;

    @ColumnInfo(name = "nuevos_datos")
    @SerializedName("nuevos_datos")
    private String nuevos_datos;


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

    public String getForo_foto() {
        return foro_foto;
    }

    public void setForo_foto(String foro_foto) {
        this.foro_foto = foro_foto;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
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

    public String getForo_feccha() {
        return foro_feccha;
    }

    public void setForo_feccha(String foro_feccha) {
        this.foro_feccha = foro_feccha;
    }



    public String getForo_tipo() {
        return foro_tipo;
    }

    public void setForo_tipo(String foro_tipo) {
        this.foro_tipo = foro_tipo;
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

    public String getPublicacion_concepto() {
        return publicacion_concepto;
    }

    public void setPublicacion_concepto(String publicacion_concepto) {
        this.publicacion_concepto = publicacion_concepto;
    }

    public String getPublicacion_torneo() {
        return publicacion_torneo;
    }

    public void setPublicacion_torneo(String publicacion_torneo) {
        this.publicacion_torneo = publicacion_torneo;
    }

    public String getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(String id_torneo) {
        this.id_torneo = id_torneo;
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

    public String getNuevos_datos() {
        return nuevos_datos;
    }

    public void setNuevos_datos(String nuevos_datos) {
        this.nuevos_datos = nuevos_datos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTorneo_foto() {
        return torneo_foto;
    }

    public void setTorneo_foto(String torneo_foto) {
        this.torneo_foto = torneo_foto;
    }

    @Override
    public String toString() {
        return "ModelFeed{" +
                "publicacion_id='" + publicacion_id + '\'' +
                ", usuario_nombre='" + usuario_nombre + '\'' +
                ", foro_foto='" + foro_foto + '\'' +
                ", foro_titulo='" + foro_titulo + '\'' +
                ", foro_descripcion='" + foro_descripcion + '\'' +
                ", publicacion_concepto='" + publicacion_concepto + '\'' +
                ", id_torneo='" + id_torneo + '\'' +
                ", torneo_foto='" + torneo_foto + '\'' +
                ", publicacion_torneo='" + publicacion_torneo + '\'' +
                ", usuario_id='" + usuario_id + '\'' +
                ", foro_feccha='" + foro_feccha + '\'' +
                ", foro_tipo='" + foro_tipo + '\'' +
                ", cant_likes='" + cant_likes + '\'' +
                ", cant_Comentarios='" + cant_Comentarios + '\'' +
                ", dio_like='" + dio_like + '\'' +
                ", usuario_foto='" + usuario_foto + '\'' +
                ", orden='" + orden + '\'' +
                ", limite_sup='" + limite_sup + '\'' +
                ", limite_inf='" + limite_inf + '\'' +
                ", nuevos_datos='" + nuevos_datos + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
