package com.tec.bufeo.capitan.TabsPrincipales.Negocios.Model;

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

    @ColumnInfo(name = "promo_precio")
    @SerializedName("promo_precio")
    private String promo_precio;

    @ColumnInfo(name = "promo_inicio")
    @SerializedName("promo_inicio")
    private String promo_inicio;

    @ColumnInfo(name = "promo_fin")
    @SerializedName("promo_fin")
    private String promo_fin;

    @ColumnInfo(name = "promo_estado")
    @SerializedName("promo_estado")
    private String promo_estado;


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

    public String getPromo_precio() {
        return promo_precio;
    }

    public void setPromo_precio(String promo_precio) {
        this.promo_precio = promo_precio;
    }

    public String getPromo_inicio() {
        return promo_inicio;
    }

    public void setPromo_inicio(String promo_inicio) {
        this.promo_inicio = promo_inicio;
    }

    public String getPromo_fin() {
        return promo_fin;
    }

    public void setPromo_fin(String promo_fin) {
        this.promo_fin = promo_fin;
    }

    public String getPromo_estado() {
        return promo_estado;
    }

    public void setPromo_estado(String promo_estado) {
        this.promo_estado = promo_estado;
    }
}
