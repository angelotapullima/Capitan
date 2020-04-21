package com.tec.bufeo.capitan.Activity.MisReservas.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "mis_reservas")
public class MisReservas
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "fecha_reserva")
    @SerializedName("fecha_reserva")
    private String fecha_reserva;

    @TypeConverters(DataConverterMisReservas.class)
    @ColumnInfo(name = "detalle_reservas")
    @SerializedName("detalle_reservas")
    private List<DetalleReservas> detalle_reservas;

    @NonNull
    public String getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(@NonNull String fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public List<DetalleReservas> getDetalle_reservas() {
        return detalle_reservas;
    }

    public void setDetalle_reservas(List<DetalleReservas> detalle_reservas) {
        this.detalle_reservas = detalle_reservas;
    }
}