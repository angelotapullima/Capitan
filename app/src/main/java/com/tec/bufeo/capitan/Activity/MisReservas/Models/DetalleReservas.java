package com.tec.bufeo.capitan.Activity.MisReservas.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "detalles_reservas")
public class DetalleReservas {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_reserva")
    @SerializedName("id_reserva")
    private String id_reserva;

    @ColumnInfo(name = "cancha_id")
    @SerializedName("cancha_id")
    private String cancha_id;

    @ColumnInfo(name = "reserva_tipopago")
    @SerializedName("reserva_tipopago")
    private String reserva_tipopago;

    @ColumnInfo(name = "pago_id")
    @SerializedName("pago_id")
    private String pago_id;

    @ColumnInfo(name = "pago_comision")
    @SerializedName("pago_comision")
    private String pago_comision;

    @ColumnInfo(name = "transferencia_u_e_nro_operacion")
    @SerializedName("transferencia_u_e_nro_operacion")
    private String transferencia_u_e_nro_operacion;


    @ColumnInfo(name = "pago_total")
    @SerializedName("pago_total")
    private String pago_total;


    @ColumnInfo(name = "pago_tipo")
    @SerializedName("pago_tipo")
    private String pago_tipo;

    @ColumnInfo(name = "reserva_nombre")
    @SerializedName("reserva_nombre")
    private String reserva_nombre;


    @ColumnInfo(name = "reserva_fecha")
    @SerializedName("reserva_fecha")
    private String reserva_fecha;

    @ColumnInfo(name = "reserva_hora")
    @SerializedName("reserva_hora")
    private String reserva_hora;


    @ColumnInfo(name = "reserva_pago1")
    @SerializedName("reserva_pago1")
    private String reserva_pago1;

    @ColumnInfo(name = "reserva_pago1_date")
    @SerializedName("reserva_pago1_date")
    private String reserva_pago1_date;


    @ColumnInfo(name = "reserva_pago2")
    @SerializedName("reserva_pago2")
    private String reserva_pago2;

    @ColumnInfo(name = "reserva_pago2_date")
    @SerializedName("reserva_pago2_date")
    private String reserva_pago2_date;


    @ColumnInfo(name = "reserva_estado")
    @SerializedName("reserva_estado")
    private String reserva_estado;

    @ColumnInfo(name = "empresa_id")
    @SerializedName("empresa_id")
    private String empresa_id;

    @ColumnInfo(name = "cancha_nombre")
    @SerializedName("cancha_nombre")
    private String cancha_nombre;


    @ColumnInfo(name = "empresa_nombre")
    @SerializedName("empresa_nombre")
    private String empresa_nombre;

    @ColumnInfo(name = "empresa_direccion")
    @SerializedName("empresa_direccion")
    private String empresa_direccion;


    @ColumnInfo(name = "empresa_coord_x")
    @SerializedName("empresa_coord_x")
    private String empresa_coord_x;

    @ColumnInfo(name = "empresa_coord_y")
    @SerializedName("empresa_coord_y")
    private String empresa_coord_y;


    @ColumnInfo(name = "empresa_telefono_1")
    @SerializedName("empresa_telefono_1")
    private String empresa_telefono_1;

    @ColumnInfo(name = "empresa_telefono_2")
    @SerializedName("empresa_telefono_2")
    private String empresa_telefono_2;

    @ColumnInfo(name = "empresa_descripcion")
    @SerializedName("empresa_descripcion")
    private String empresa_descripcion;


    @ColumnInfo(name = "empresa_horario_ls")
    @SerializedName("empresa_horario_ls")
    private String empresa_horario_ls;

    @ColumnInfo(name = "empresa_horario_d")
    @SerializedName("empresa_horario_d")
    private String empresa_horario_d;

    @ColumnInfo(name = "empresa_valoracion")
    @SerializedName("empresa_valoracion")
    private String empresa_valoracion;

    @ColumnInfo(name = "empresa_foto")
    @SerializedName("empresa_foto")
    private String empresa_foto;


    @ColumnInfo(name = "empresa_estado")
    @SerializedName("empresa_estado")
    private String empresa_estado;

    @ColumnInfo(name = "pago_date")
    @SerializedName("pago_date")
    private String pago_date;

    @ColumnInfo(name = "nombre_user")
    @SerializedName("nombre_user")
    private String nombre_user;

    @ColumnInfo(name = "reserva_tipo")
    @SerializedName("reserva_tipo")
    private String reserva_tipo;

    @NonNull
    public String getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(@NonNull String id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getCancha_id() {
        return cancha_id;
    }

    public void setCancha_id(String cancha_id) {
        this.cancha_id = cancha_id;
    }

    public String getReserva_tipopago() {
        return reserva_tipopago;
    }

    public void setReserva_tipopago(String reserva_tipopago) {
        this.reserva_tipopago = reserva_tipopago;
    }

    public String getPago_id() {
        return pago_id;
    }

    public void setPago_id(String pago_id) {
        this.pago_id = pago_id;
    }

    public String getReserva_nombre() {
        return reserva_nombre;
    }

    public void setReserva_nombre(String reserva_nombre) {
        this.reserva_nombre = reserva_nombre;
    }

    public String getReserva_fecha() {
        return reserva_fecha;
    }

    public void setReserva_fecha(String reserva_fecha) {
        this.reserva_fecha = reserva_fecha;
    }

    public String getReserva_hora() {
        return reserva_hora;
    }

    public void setReserva_hora(String reserva_hora) {
        this.reserva_hora = reserva_hora;
    }

    public String getReserva_pago1() {
        return reserva_pago1;
    }

    public void setReserva_pago1(String reserva_pago1) {
        this.reserva_pago1 = reserva_pago1;
    }

    public String getReserva_pago1_date() {
        return reserva_pago1_date;
    }

    public void setReserva_pago1_date(String reserva_pago1_date) {
        this.reserva_pago1_date = reserva_pago1_date;
    }

    public String getReserva_pago2() {
        return reserva_pago2;
    }

    public void setReserva_pago2(String reserva_pago2) {
        this.reserva_pago2 = reserva_pago2;
    }

    public String getReserva_pago2_date() {
        return reserva_pago2_date;
    }

    public void setReserva_pago2_date(String reserva_pago2_date) {
        this.reserva_pago2_date = reserva_pago2_date;
    }

    public String getReserva_estado() {
        return reserva_estado;
    }

    public void setReserva_estado(String reserva_estado) {
        this.reserva_estado = reserva_estado;
    }

    public String getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(String empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getCancha_nombre() {
        return cancha_nombre;
    }

    public void setCancha_nombre(String cancha_nombre) {
        this.cancha_nombre = cancha_nombre;
    }

    public String getEmpresa_nombre() {
        return empresa_nombre;
    }

    public void setEmpresa_nombre(String empresa_nombre) {
        this.empresa_nombre = empresa_nombre;
    }

    public String getEmpresa_direccion() {
        return empresa_direccion;
    }

    public void setEmpresa_direccion(String empresa_direccion) {
        this.empresa_direccion = empresa_direccion;
    }

    public String getEmpresa_coord_x() {
        return empresa_coord_x;
    }

    public void setEmpresa_coord_x(String empresa_coord_x) {
        this.empresa_coord_x = empresa_coord_x;
    }

    public String getEmpresa_coord_y() {
        return empresa_coord_y;
    }

    public void setEmpresa_coord_y(String empresa_coord_y) {
        this.empresa_coord_y = empresa_coord_y;
    }

    public String getEmpresa_telefono_1() {
        return empresa_telefono_1;
    }

    public void setEmpresa_telefono_1(String empresa_telefono_1) {
        this.empresa_telefono_1 = empresa_telefono_1;
    }

    public String getEmpresa_telefono_2() {
        return empresa_telefono_2;
    }

    public void setEmpresa_telefono_2(String empresa_telefono_2) {
        this.empresa_telefono_2 = empresa_telefono_2;
    }

    public String getEmpresa_descripcion() {
        return empresa_descripcion;
    }

    public void setEmpresa_descripcion(String empresa_descripcion) {
        this.empresa_descripcion = empresa_descripcion;
    }

    public String getEmpresa_horario_ls() {
        return empresa_horario_ls;
    }

    public void setEmpresa_horario_ls(String empresa_horario_ls) {
        this.empresa_horario_ls = empresa_horario_ls;
    }

    public String getEmpresa_horario_d() {
        return empresa_horario_d;
    }

    public void setEmpresa_horario_d(String empresa_horario_d) {
        this.empresa_horario_d = empresa_horario_d;
    }

    public String getEmpresa_valoracion() {
        return empresa_valoracion;
    }

    public void setEmpresa_valoracion(String empresa_valoracion) {
        this.empresa_valoracion = empresa_valoracion;
    }

    public String getEmpresa_foto() {
        return empresa_foto;
    }

    public void setEmpresa_foto(String empresa_foto) {
        this.empresa_foto = empresa_foto;
    }

    public String getEmpresa_estado() {
        return empresa_estado;
    }

    public void setEmpresa_estado(String empresa_estado) {
        this.empresa_estado = empresa_estado;
    }

    public String getNombre_user() {
        return nombre_user;
    }

    public void setNombre_user(String nombre_user) {
        this.nombre_user = nombre_user;
    }

    public String getReserva_tipo() {
        return reserva_tipo;
    }

    public void setReserva_tipo(String reserva_tipo) {
        this.reserva_tipo = reserva_tipo;
    }

    public String getPago_total() {
        return pago_total;
    }

    public void setPago_total(String pago_total) {
        this.pago_total = pago_total;
    }

    public String getPago_tipo() {
        return pago_tipo;
    }

    public void setPago_tipo(String pago_tipo) {
        this.pago_tipo = pago_tipo;
    }

    public String getPago_date() {
        return pago_date;
    }

    public void setPago_date(String pago_date) {
        this.pago_date = pago_date;
    }

    public String getPago_comision() {
        return pago_comision;
    }

    public void setPago_comision(String pago_comision) {
        this.pago_comision = pago_comision;
    }

    public String getTransferencia_u_e_nro_operacion() {
        return transferencia_u_e_nro_operacion;
    }

    public void setTransferencia_u_e_nro_operacion(String transferencia_u_e_nro_operacion) {
        this.transferencia_u_e_nro_operacion = transferencia_u_e_nro_operacion;
    }
}
