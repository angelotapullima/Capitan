package com.tec.bufeo.capitan.Activity.DetalleEquipo.TabEstadisticasEquipos.Models;


import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "estadisticas_equipos")
public class EstadisticasDeEquipos {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "equipo_id")
    @SerializedName("equipo_id")
    private int  equipo_id;

    @ColumnInfo(name = "nombre")
    @SerializedName("nombre")
    private String nombre;


    @ColumnInfo(name = "foto")
    @SerializedName("foto")
    private String foto;

    @ColumnInfo(name = "temporada")
    @SerializedName("temporada")
    private String temporada;

    @ColumnInfo(name = "semana")
    @SerializedName("semana")
    private String semana;

    @ColumnInfo(name = "puntaje_acumulado")
    @SerializedName("puntaje_acumulado")
    private String puntaje_acumulado;

    @ColumnInfo(name = "puntaje_semanal")
    @SerializedName("puntaje_semanal")
    private String puntaje_semanal;

    @ColumnInfo(name = "retos_enviados")
    @SerializedName("retos_enviados")
    private String retos_enviados;

    @ColumnInfo(name = "retos_recibidos")
    @SerializedName("retos_recibidos")
    private String retos_recibidos;

    @ColumnInfo(name = "retos_ganados")
    @SerializedName("retos_ganados")
    private String retos_ganados;

    @ColumnInfo(name = "retos_empatados")
    @SerializedName("retos_empatados")
    private String retos_empatados;

    @ColumnInfo(name = "retos_perdidos")
    @SerializedName("retos_perdidos")
    private String retos_perdidos;

    @ColumnInfo(name = "torneos")
    @SerializedName("torneos")
    private String torneos;

    public int getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(int equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public String getPuntaje_acumulado() {
        return puntaje_acumulado;
    }

    public void setPuntaje_acumulado(String puntaje_acumulado) {
        this.puntaje_acumulado = puntaje_acumulado;
    }

    public String getPuntaje_semanal() {
        return puntaje_semanal;
    }

    public void setPuntaje_semanal(String puntaje_semanal) {
        this.puntaje_semanal = puntaje_semanal;
    }

    public String getRetos_enviados() {
        return retos_enviados;
    }

    public void setRetos_enviados(String retos_enviados) {
        this.retos_enviados = retos_enviados;
    }

    public String getRetos_recibidos() {
        return retos_recibidos;
    }

    public void setRetos_recibidos(String retos_recibidos) {
        this.retos_recibidos = retos_recibidos;
    }

    public String getRetos_ganados() {
        return retos_ganados;
    }

    public void setRetos_ganados(String retos_ganados) {
        this.retos_ganados = retos_ganados;
    }

    public String getRetos_empatados() {
        return retos_empatados;
    }

    public void setRetos_empatados(String retos_empatados) {
        this.retos_empatados = retos_empatados;
    }

    public String getRetos_perdidos() {
        return retos_perdidos;
    }

    public void setRetos_perdidos(String retos_perdidos) {
        this.retos_perdidos = retos_perdidos;
    }

    public String getTorneos() {
        return torneos;
    }

    public void setTorneos(String torneos) {
        this.torneos = torneos;
    }
}
