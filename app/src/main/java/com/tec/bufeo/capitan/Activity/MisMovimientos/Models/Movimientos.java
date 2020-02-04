package com.tec.bufeo.capitan.Activity.MisMovimientos.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movimientos")
public class Movimientos
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "movimiento_id")
    @SerializedName("movimiento_id")
    private String movimiento_id;

    @ColumnInfo(name = "movimiento_nombre")
    @SerializedName("movimiento_nombre")
    private String movimiento_nombre;

        @ColumnInfo(name = "movimiento_monto")
    @SerializedName("movimiento_monto")
    private String movimiento_monto;

    @ColumnInfo(name = "movimiento_estado")
    @SerializedName("movimiento_estado")
    private String movimiento_estado;

    @ColumnInfo(name = "movimiento_fecha")
    @SerializedName("movimiento_fecha")
    private String movimiento_fecha;

    @NonNull
    public String getMovimiento_id() {
        return movimiento_id;
    }

    public void setMovimiento_id(@NonNull String movimiento_id) {
        this.movimiento_id = movimiento_id;
    }

    public String getMovimiento_nombre() {
        return movimiento_nombre;
    }

    public void setMovimiento_nombre(String movimiento_nombre) {
        this.movimiento_nombre = movimiento_nombre;
    }

    public String getMovimiento_monto() {
        return movimiento_monto;
    }

    public void setMovimiento_monto(String movimiento_monto) {
        this.movimiento_monto = movimiento_monto;
    }

    public String getMovimiento_estado() {
        return movimiento_estado;
    }

    public void setMovimiento_estado(String movimiento_estado) {
        this.movimiento_estado = movimiento_estado;
    }

    public String getMovimiento_fecha() {
        return movimiento_fecha;
    }

    public void setMovimiento_fecha(String movimiento_fecha) {
        this.movimiento_fecha = movimiento_fecha;
    }
}