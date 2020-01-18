package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "jugadores_seleccionados")
public class JugadoresSeleccionados {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "jugadors_id")
    @SerializedName("jugadors_id")
    private String jugadors_id;

    @ColumnInfo(name = "jugadors_nombre")
    @SerializedName("jugadors_nombre")
    private String jugadors_nombre;


    @ColumnInfo(name = "jugadors_foto")
    @SerializedName("jugadors_foto")
    private String jugadors_foto;



    @ColumnInfo(name = "jugadors_posicion")
    @SerializedName("jugadors_posicion")
    private String jugadors_posicion;

    @ColumnInfo(name = "jugadors_habilidad")
    @SerializedName("jugadors_habilidad")
    private String jugadors_habilidad;


    @ColumnInfo(name = "jugadors_numero")
    @SerializedName("jugadors_numero")
    private String jugadors_numero;

    @ColumnInfo(name = "jugadors_estado")
    @SerializedName("jugadors_estado")
    private String jugadors_estado;

    @NonNull
    public String getJugadors_id() {
        return jugadors_id;
    }

    public void setJugadors_id(@NonNull String jugadors_id) {
        this.jugadors_id = jugadors_id;
    }

    public String getJugadors_nombre() {
        return jugadors_nombre;
    }

    public void setJugadors_nombre(String jugadors_nombre) {
        this.jugadors_nombre = jugadors_nombre;
    }

    public String getJugadors_foto() {
        return jugadors_foto;
    }

    public void setJugadors_foto(String jugadors_foto) {
        this.jugadors_foto = jugadors_foto;
    }

    public String getJugadors_posicion() {
        return jugadors_posicion;
    }

    public void setJugadors_posicion(String jugadors_posicion) {
        this.jugadors_posicion = jugadors_posicion;
    }

    public String getJugadors_habilidad() {
        return jugadors_habilidad;
    }

    public void setJugadors_habilidad(String jugadors_habilidad) {
        this.jugadors_habilidad = jugadors_habilidad;
    }

    public String getJugadors_numero() {
        return jugadors_numero;
    }

    public void setJugadors_numero(String jugadors_numero) {
        this.jugadors_numero = jugadors_numero;
    }

    public String getJugadors_estado() {
        return jugadors_estado;
    }

    public void setJugadors_estado(String jugadors_estado) {
        this.jugadors_estado = jugadors_estado;
    }
}
