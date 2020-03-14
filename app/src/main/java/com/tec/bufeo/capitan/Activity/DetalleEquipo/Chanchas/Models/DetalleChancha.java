package com.tec.bufeo.capitan.Activity.DetalleEquipo.Chanchas.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "detalle_chanchas")
public class DetalleChancha {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_colaboracion")
    @SerializedName("id_colaboracion")
    private String id_colaboracion;


    @ColumnInfo(name = "id_detalle_colaboracion")
    @SerializedName("id_detalle_colaboracion")
    private String id_detalle_colaboracion;


    @ColumnInfo(name = "colaboracion_date")
    @SerializedName("colaboracion_date")
    private String colaboracion_date;

    @ColumnInfo(name = "colaboracion_estado")
    @SerializedName("colaboracion_estado")
    private String colaboracion_estado;

    @ColumnInfo(name = "colaboracion_monto")
    @SerializedName("colaboracion_monto")
    private String colaboracion_monto;

    @ColumnInfo(name = "user_nickname")
    @SerializedName("user_nickname")
    private String user_nickname;

    @ColumnInfo(name = "user_image")
    @SerializedName("user_image")
    private String user_image;

    @NonNull
    public String getId_colaboracion() {
        return id_colaboracion;
    }

    public void setId_colaboracion(@NonNull String id_colaboracion) {
        this.id_colaboracion = id_colaboracion;
    }

    public String getId_detalle_colaboracion() {
        return id_detalle_colaboracion;
    }

    public void setId_detalle_colaboracion(String id_detalle_colaboracion) {
        this.id_detalle_colaboracion = id_detalle_colaboracion;
    }

    public String getColaboracion_date() {
        return colaboracion_date;
    }

    public void setColaboracion_date(String colaboracion_date) {
        this.colaboracion_date = colaboracion_date;
    }

    public String getColaboracion_estado() {
        return colaboracion_estado;
    }

    public void setColaboracion_estado(String colaboracion_estado) {
        this.colaboracion_estado = colaboracion_estado;
    }

    public String getColaboracion_monto() {
        return colaboracion_monto;
    }

    public void setColaboracion_monto(String colaboracion_monto) {
        this.colaboracion_monto = colaboracion_monto;
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

    public DetalleChancha(@NonNull String id_colaboracion, String id_detalle_colaboracion, String colaboracion_date, String colaboracion_estado, String colaboracion_monto, String user_nickname, String user_image) {
        this.id_colaboracion = id_colaboracion;
        this.id_detalle_colaboracion = id_detalle_colaboracion;
        this.colaboracion_date = colaboracion_date;
        this.colaboracion_estado = colaboracion_estado;
        this.colaboracion_monto = colaboracion_monto;
        this.user_nickname = user_nickname;
        this.user_image = user_image;
    }
}
