package com.tec.bufeo.capitan.Activity.DetallesTorneo.InfoDtorneo.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "detalle_torneo")
public class DetalleTorneo {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_torneo")
    @SerializedName("id_torneo")
    private String id_torneo;


    @ColumnInfo(name = "nombre_torneo")
    @SerializedName("nombre_torneo")
    private String nombre_torneo;

    @ColumnInfo(name = "foto_torneo")
    @SerializedName("foto_torneo")
    private String foto_torneo;

    @ColumnInfo(name = "descripcion_torneo")
    @SerializedName("descripcion_torneo")
    private String descripcion_torneo;

    @ColumnInfo(name = "fecha_torneo")
    @SerializedName("fecha_torneo")
    private String fecha_torneo;

    @ColumnInfo(name = "hora_torneo")
    @SerializedName("hora_torneo")
    private String hora_torneo;

    @ColumnInfo(name = "lugar_torneo")
    @SerializedName("lugar_torneo")
    private String lugar_torneo;

    @ColumnInfo(name = "id_organizador_torneo")
    @SerializedName("id_organizador_torneo")
    private String id_organizador_torneo;

    @ColumnInfo(name = "organizador_torneo")
    @SerializedName("organizador_torneo")
    private String organizador_torneo;

    @ColumnInfo(name = "costo_torneo")
    @SerializedName("costo_torneo")
    private String costo_torneo;

    @ColumnInfo(name = "equipos_torneo")
    @SerializedName("equipos_torneo")
    private String equipos_torneo;


    @NonNull
    public String getId_torneo() {
        return id_torneo;
    }

    public void setId_torneo(@NonNull String id_torneo) {
        this.id_torneo = id_torneo;
    }

    public String getNombre_torneo() {
        return nombre_torneo;
    }

    public void setNombre_torneo(String nombre_torneo) {
        this.nombre_torneo = nombre_torneo;
    }

    public String getFoto_torneo() {
        return foto_torneo;
    }

    public void setFoto_torneo(String foto_torneo) {
        this.foto_torneo = foto_torneo;
    }

    public String getDescripcion_torneo() {
        return descripcion_torneo;
    }

    public void setDescripcion_torneo(String descripcion_torneo) {
        this.descripcion_torneo = descripcion_torneo;
    }

    public String getFecha_torneo() {
        return fecha_torneo;
    }

    public void setFecha_torneo(String fecha_torneo) {
        this.fecha_torneo = fecha_torneo;
    }

    public String getHora_torneo() {
        return hora_torneo;
    }

    public void setHora_torneo(String hora_torneo) {
        this.hora_torneo = hora_torneo;
    }

    public String getLugar_torneo() {
        return lugar_torneo;
    }

    public void setLugar_torneo(String lugar_torneo) {
        this.lugar_torneo = lugar_torneo;
    }

    public String getId_organizador_torneo() {
        return id_organizador_torneo;
    }

    public void setId_organizador_torneo(String id_organizador_torneo) {
        this.id_organizador_torneo = id_organizador_torneo;
    }

    public String getOrganizador_torneo() {
        return organizador_torneo;
    }

    public void setOrganizador_torneo(String organizador_torneo) {
        this.organizador_torneo = organizador_torneo;
    }

    public String getCosto_torneo() {
        return costo_torneo;
    }

    public void setCosto_torneo(String costo_torneo) {
        this.costo_torneo = costo_torneo;
    }

    public String getEquipos_torneo() {
        return equipos_torneo;
    }

    public void setEquipos_torneo(String equipos_torneo) {
        this.equipos_torneo = equipos_torneo;
    }
}
