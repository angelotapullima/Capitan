package com.tec.bufeo.capitan.Activity.DetalleCanchas.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "reservas_cancha")
public class ReservasCancha {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "reserva_id")
    @SerializedName("reserva_id")
    private String reserva_id;

    @ColumnInfo(name = "fecha")
    @SerializedName("fecha")
    private String fecha;

    @ColumnInfo(name = "pago_id")
    @SerializedName("pago_id")
    private String pago_id;

    @ColumnInfo(name = "tipopago")
    @SerializedName("tipopago")
    private String tipopago;

    @ColumnInfo(name = "cancha_id")
    @SerializedName("cancha_id")
    private String cancha_id;


    @ColumnInfo(name = "nombre")
    @SerializedName("nombre")
    private String nombre;


    @ColumnInfo(name = "hora")
    @SerializedName("hora")
    private String hora;


    @ColumnInfo(name = "pago1")
    @SerializedName("pago1")
    private String pago1;


    @ColumnInfo(name = "pago1_date")
    @SerializedName("pago1_date")
    private String pago1_date;


    @ColumnInfo(name = "pago2")
    @SerializedName("pago2")
    private String pago2;


    @ColumnInfo(name = "pago2_date")
    @SerializedName("pago2_date")
    private String pago2_date;


    @ColumnInfo(name = "dia")
    @SerializedName("dia")
    private String dia;


    @ColumnInfo(name = "estado")
    @SerializedName("estado")
    private String estado;

    @NonNull
    public String getReserva_id() {
        return reserva_id;
    }

    public void setReserva_id(@NonNull String reserva_id) {
        this.reserva_id = reserva_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPago_id() {
        return pago_id;
    }

    public void setPago_id(String pago_id) {
        this.pago_id = pago_id;
    }

    public String getTipopago() {
        return tipopago;
    }

    public void setTipopago(String tipopago) {
        this.tipopago = tipopago;
    }

    public String getCancha_id() {
        return cancha_id;
    }

    public void setCancha_id(String cancha_id) {
        this.cancha_id = cancha_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPago1() {
        return pago1;
    }

    public void setPago1(String pago1) {
        this.pago1 = pago1;
    }

    public String getPago1_date() {
        return pago1_date;
    }

    public void setPago1_date(String pago1_date) {
        this.pago1_date = pago1_date;
    }

    public String getPago2() {
        return pago2;
    }

    public void setPago2(String pago2) {
        this.pago2 = pago2;
    }

    public String getPago2_date() {
        return pago2_date;
    }

    public void setPago2_date(String pago2_date) {
        this.pago2_date = pago2_date;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
