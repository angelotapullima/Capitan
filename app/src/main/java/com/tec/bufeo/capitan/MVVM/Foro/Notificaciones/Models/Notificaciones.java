package com.tec.bufeo.capitan.MVVM.Foro.Notificaciones.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "notificaciones")
public class Notificaciones
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_notificacion")
    @SerializedName("id_notificacion")
    private String id_notificacion;

    @ColumnInfo(name = "id_user")
    @SerializedName("id_user")
    private String id_user;

    @ColumnInfo(name = "notificacion_tipo")
    @SerializedName("notificacion_tipo")
    private String notificacion_tipo;

    @ColumnInfo(name = "notificacion_id_rel")
    @SerializedName("notificacion_id_rel")
    private String notificacion_id_rel;

    @ColumnInfo(name = "notificacion_mensaje")
    @SerializedName("notificacion_mensaje")
    private String notificacion_mensaje;

    @ColumnInfo(name = "notificacion_datetime")
    @SerializedName("notificacion_datetime")
    private String notificacion_datetime;


    @ColumnInfo(name = "notificacion_estado")
    @SerializedName("notificacion_estado")
    private String notificacion_estado;



    @NonNull
    public String getId_notificacion() {
        return id_notificacion;
    }

    public void setId_notificacion(@NonNull String id_notificacion) {
        this.id_notificacion = id_notificacion;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNotificacion_tipo() {
        return notificacion_tipo;
    }

    public void setNotificacion_tipo(String notificacion_tipo) {
        this.notificacion_tipo = notificacion_tipo;
    }

    public String getNotificacion_id_rel() {
        return notificacion_id_rel;
    }

    public void setNotificacion_id_rel(String notificacion_id_rel) {
        this.notificacion_id_rel = notificacion_id_rel;
    }

    public String getNotificacion_mensaje() {
        return notificacion_mensaje;
    }

    public void setNotificacion_mensaje(String notificacion_mensaje) {
        this.notificacion_mensaje = notificacion_mensaje;
    }

    public String getNotificacion_datetime() {
        return notificacion_datetime;
    }

    public void setNotificacion_datetime(String notificacion_datetime) {
        this.notificacion_datetime = notificacion_datetime;
    }

    public String getNotificacion_estado() {
        return notificacion_estado;
    }

    public void setNotificacion_estado(String notificacion_estado) {
        this.notificacion_estado = notificacion_estado;
    }


}