package com.tec.bufeo.capitan.Activity.MisMovimientos.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "movimientos")
public class Movimientos
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "fecha")
    @SerializedName("fecha")
    private String fecha;

    @TypeConverters(DataConverterMovimientos.class)
    @ColumnInfo(name = "detalle_movimientos")
    @SerializedName("detalle_movimientos")
    private List<DetalleMovimientos> detalle_movimientos;

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull String fecha) {
        this.fecha = fecha;
    }

    public List<DetalleMovimientos> getDetalle_movimientos() {
        return detalle_movimientos;
    }

    public void setDetalle_movimientos(List<DetalleMovimientos> detalle_movimientos) {
        this.detalle_movimientos = detalle_movimientos;
    }
}