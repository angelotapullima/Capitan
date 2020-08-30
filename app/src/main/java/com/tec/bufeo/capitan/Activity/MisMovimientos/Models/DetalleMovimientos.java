package com.tec.bufeo.capitan.Activity.MisMovimientos.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "Detalle_movimientos")
public class DetalleMovimientos {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "nro_operacion")
    @SerializedName("nro_operacion")
    private String nro_operacion;

    @ColumnInfo(name = "concepto")
    @SerializedName("concepto")
    private String concepto;

    @ColumnInfo(name = "tipo_pago")
    @SerializedName("tipo_pago")
    private String tipo_pago;

    @ColumnInfo(name = "monto")
    @SerializedName("monto")
    private String monto;

    @ColumnInfo(name = "fecha")
    @SerializedName("fecha")
    private String fecha;


    @ColumnInfo(name = "solo_fecha")
    @SerializedName("solo_fecha")
    private String solo_fecha;

    @ColumnInfo(name = "cliente")
    @SerializedName("cliente")
    private String cliente;

    @ColumnInfo(name = "comision")
    @SerializedName("comision")
    private String comision;


    // ind = 0 indica que es egreso ?  ind =1 indica que es ingreso
    @ColumnInfo(name = "ind")
    @SerializedName("ind")
    private int  ind;


    @NonNull
    public String getNro_operacion() {
        return nro_operacion;
    }

    public void setNro_operacion(@NonNull String nro_operacion) {
        this.nro_operacion = nro_operacion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSolo_fecha() {
        return solo_fecha;
    }

    public void setSolo_fecha(String solo_fecha) {
        this.solo_fecha = solo_fecha;
    }

    public int getInd() {
        return ind;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }
}
