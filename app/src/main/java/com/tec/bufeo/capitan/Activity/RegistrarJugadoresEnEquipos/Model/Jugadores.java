package com.tec.bufeo.capitan.Activity.RegistrarJugadoresEnEquipos.Model;


import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "jugadores")
public class Jugadores {


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "jugador_id")
    @SerializedName("jugador_id")
    private String jugador_id;

    @ColumnInfo(name = "jugador_nombre")
    @SerializedName("jugador_nombre")
    private String jugador_nombre;


    @ColumnInfo(name = "jugador_foto")
    @SerializedName("jugador_foto")
    private String jugador_foto;



    @ColumnInfo(name = "jugador_posicion")
    @SerializedName("jugador_posicion")
    private String jugador_posicion;

    @ColumnInfo(name = "jugador_habilidad")
    @SerializedName("jugador_habilidad")
    private String jugador_habilidad;


    @ColumnInfo(name = "jugador_numero")
    @SerializedName("jugador_numero")
    private String jugador_numero;

    @ColumnInfo(name = "jugador_estado")
    @SerializedName("jugador_estado")
    private String jugador_estado;




    @NonNull
    public String getJugador_id() {
        return jugador_id;
    }

    public void setJugador_id(@NonNull String jugador_id) {
        this.jugador_id = jugador_id;
    }

    public String getJugador_nombre() {
        return jugador_nombre;
    }

    public void setJugador_nombre(String jugador_nombre) {
        this.jugador_nombre = jugador_nombre;
    }

    public String getJugador_foto() {
        return jugador_foto;
    }

    public void setJugador_foto(String jugador_foto) {
        this.jugador_foto = jugador_foto;
    }

    public String getJugador_posicion() {
        return jugador_posicion;
    }

    public void setJugador_posicion(String jugador_posicion) {
        this.jugador_posicion = jugador_posicion;
    }

    public String getJugador_habilidad() {
        return jugador_habilidad;
    }

    public void setJugador_habilidad(String jugador_habilidad) {
        this.jugador_habilidad = jugador_habilidad;
    }

    public String getJugador_numero() {
        return jugador_numero;
    }

    public void setJugador_numero(String jugador_numero) {
        this.jugador_numero = jugador_numero;
    }

    public String getJugador_estado() {
        return jugador_estado;
    }

    public void setJugador_estado(String jugador_estado) {
        this.jugador_estado = jugador_estado;
    }
}
