package com.tec.bufeo.capitan.Activity.MisReservas.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mis_reservas")
public class MisReservas
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_reserva")
    @SerializedName("id_reserva")
    private String id_reserva;

    @ColumnInfo(name = "nombre_reserva")
    @SerializedName("nombre_reserva")
    private String nombre_reserva;


    @ColumnInfo(name = "nombre_empresa")
    @SerializedName("nombre_empresa")
    private String nombre_empresa;


    @ColumnInfo(name = "cancha_nombre")
    @SerializedName("cancha_nombre")
    private String cancha_nombre;

    @ColumnInfo(name = "monto_final")
    @SerializedName("monto_final")
    private String monto_final;

    @ColumnInfo(name = "fecha_reserva")
    @SerializedName("fecha_reserva")
    private String fecha_reserva;


    @ColumnInfo(name = "hora_reserva")
    @SerializedName("hora_reserva")
    private String hora_reserva;

    @ColumnInfo(name = "telefono1_reserva")
    @SerializedName("telefono1_reserva")
    private String telefono1_reserva;

    @ColumnInfo(name = "telefono2_reserva")
    @SerializedName("telefono2_reserva")
    private String telefono2_reserva;

    @ColumnInfo(name = "direccion_reserva")
    @SerializedName("direccion_reserva")
    private String direccion_reserva;

    @NonNull
    public String getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(@NonNull String id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getCancha_nombre() {
        return cancha_nombre;
    }

    public void setCancha_nombre(String cancha_nombre) {
        this.cancha_nombre = cancha_nombre;
    }

    public String getMonto_final() {
        return monto_final;
    }

    public void setMonto_final(String monto_final) {
        this.monto_final = monto_final;
    }

    public String getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(String fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public String getHora_reserva() {
        return hora_reserva;
    }

    public void setHora_reserva(String hora_reserva) {
        this.hora_reserva = hora_reserva;
    }

    public String getTelefono1_reserva() {
        return telefono1_reserva;
    }

    public void setTelefono1_reserva(String telefono1_reserva) {
        this.telefono1_reserva = telefono1_reserva;
    }

    public String getTelefono2_reserva() {
        return telefono2_reserva;
    }

    public void setTelefono2_reserva(String telefono2_reserva) {
        this.telefono2_reserva = telefono2_reserva;
    }

    public String getDireccion_reserva() {
        return direccion_reserva;
    }

    public void setDireccion_reserva(String direccion_reserva) {
        this.direccion_reserva = direccion_reserva;
    }

    public String getNombre_reserva() {
        return nombre_reserva;
    }

    public void setNombre_reserva(String nombre_reserva) {
        this.nombre_reserva = nombre_reserva;
    }
}