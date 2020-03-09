package com.tec.bufeo.capitan.Activity.Negocios.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "canchas")
public class Canchas {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "cancha_id")
    @SerializedName("cancha_id")
    private String cancha_id;

    @ColumnInfo(name = "id_empresa")
    @SerializedName("id_empresa")
    private String id_empresa;


    @ColumnInfo(name = "nombre_cancha")
    @SerializedName("nombre_cancha")
    private String nombre_cancha;

    @ColumnInfo(name = "dimensiones_cancha")
    @SerializedName("dimensiones_cancha")
    private String dimensiones_cancha;

    @ColumnInfo(name = "precioD")
    @SerializedName("precioD")
    private String precioD;

    @ColumnInfo(name = "precioN")
    @SerializedName("precioN")
    private String precioN;

    @ColumnInfo(name = "foto")
    @SerializedName("foto")
    private String foto;

    @NonNull
    public String getCancha_id() {
        return cancha_id;
    }

    public void setCancha_id(@NonNull String cancha_id) {
        this.cancha_id = cancha_id;
    }

    public String getNombre_cancha() {
        return nombre_cancha;
    }

    public void setNombre_cancha(String nombre_cancha) {
        this.nombre_cancha = nombre_cancha;
    }

    public String getDimensiones_cancha() {
        return dimensiones_cancha;
    }

    public void setDimensiones_cancha(String dimensiones_cancha) {
        this.dimensiones_cancha = dimensiones_cancha;
    }

    public String getPrecioD() {
        return precioD;
    }

    public void setPrecioD(String precioD) {
        this.precioD = precioD;
    }

    public String getPrecioN() {
        return precioN;
    }

    public void setPrecioN(String precioN) {
        this.precioN = precioN;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }
}
