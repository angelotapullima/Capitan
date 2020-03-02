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
    @ColumnInfo(name = "movimiento_id")
    @SerializedName("movimiento_id")
    private String movimiento_id;

    @NonNull
    public String getMovimiento_id() {
        return movimiento_id;
    }

    public void setMovimiento_id(@NonNull String movimiento_id) {
        this.movimiento_id = movimiento_id;
    }
}