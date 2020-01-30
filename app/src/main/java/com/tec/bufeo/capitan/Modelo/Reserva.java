package com.tec.bufeo.capitan.Modelo;

public class Reserva {
    public Reserva() {
    }

    public Reserva(String cancha_id) {
        this.cancha_id = cancha_id;
    }

    public Reserva(String empresa_id, String fecha_reporte) {
        this.empresa_id = empresa_id;
        this.fecha_reporte = fecha_reporte;
    }

    public Reserva(String empresa_id, String fecha_i_reporte, String fecha_f_reporte) {
        this.empresa_id = empresa_id;
        this.fecha_i_reporte = fecha_i_reporte;
        this.fecha_f_reporte = fecha_f_reporte;
    }

    /* public Reserva(String reserva_id, String cancha_id, String cancha_nombre, String empresa_id, String reserva_nombre, String reserva_fecha, String reserva_hora, String reserva_costo, String reserva_color, String reserva_estado, String reserva_precioCancha, String reserva_hora_cancha, String fecha_reporte) {
        this.reserva_id = reserva_id;
        this.cancha_id = cancha_id;
        this.cancha_nombre = cancha_nombre;
        this.empresa_id = empresa_id;
        this.reserva_nombre = reserva_nombre;
        this.reserva_fecha = reserva_fecha;
        this.reserva_hora = reserva_hora;
        this.reserva_costo = reserva_costo;
        this.reserva_color = reserva_color;
        this.reserva_estado = reserva_estado;
        this.reserva_precioCancha = reserva_precioCancha;
        this.reserva_hora_cancha = reserva_hora_cancha;
        this.fecha_reporte = fecha_reporte;
    }*/

    public Reserva(String reserva_id, String cancha_id, String reserva_nombre, String reserva_fecha, String reserva_hora, String reserva_costo, String reserva_color, String reserva_estado, String reserva_precioCancha, String reserva_hora_cancha) {
        this.reserva_id = reserva_id;
        this.cancha_id = cancha_id;
        this.reserva_nombre = reserva_nombre;
        this.reserva_fecha = reserva_fecha;
        this.reserva_hora = reserva_hora;
        this.reserva_costo = reserva_costo;
        this.reserva_color = reserva_color;
        this.reserva_estado = reserva_estado;
        this.reserva_precioCancha = reserva_precioCancha;
        this.reserva_hora_cancha = reserva_hora_cancha;
    }

    private String reserva_id;
    private String cancha_id;
    private String cancha_nombre;
    private String empresa_id;
    private String reserva_nombre;
    private String reserva_fecha;
    private String reserva_hora;
    private String reserva_costo;
    private String reserva_color;
    private String reserva_estado;
    private String reserva_precioCancha;
    private String reserva_hora_cancha;
    private String fecha_reporte;
    private String fecha_i_reporte;
    private String fecha_f_reporte;
    private String pago_id;
    private String tipopago;
    private String pago1;
    private String pago1_date;
    private String pago2;
    private String pago2_date;

    public String getReserva_id() {
        return reserva_id;
    }

    public void setReserva_id(String reserva_id) {
        this.reserva_id = reserva_id;
    }

    public String getCancha_id() {
        return cancha_id;
    }

    public void setCancha_id(String cancha_id) {
        this.cancha_id = cancha_id;
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

    public String getReserva_costo() {
        return reserva_costo;
    }

    public void setReserva_costo(String reserva_costo) {
        this.reserva_costo = reserva_costo;
    }

    public String getReserva_color() {
        return reserva_color;
    }

    public void setReserva_color(String reserva_color) {
        this.reserva_color = reserva_color;
    }

    public String getReserva_estado() {
        return reserva_estado;
    }

    public void setReserva_estado(String reserva_estado) {
        this.reserva_estado = reserva_estado;
    }

    public String getReserva_precioCancha() {
        return reserva_precioCancha;
    }

    public void setReserva_precioCancha(String reserva_precioCancha) {
        this.reserva_precioCancha = reserva_precioCancha;
    }

    public String getReserva_hora_cancha() {
        return reserva_hora_cancha;
    }

    public void setReserva_hora_cancha(String reserva_hora_cancha) {
        this.reserva_hora_cancha = reserva_hora_cancha;
    }

    public String getCancha_nombre() {
        return cancha_nombre;
    }

    public void setCancha_nombre(String cancha_nombre) {
        this.cancha_nombre = cancha_nombre;
    }

    public String getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(String empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getFecha_reporte() {
        return fecha_reporte;
    }

    public void setFecha_reporte(String fecha_reporte) {
        this.fecha_reporte = fecha_reporte;
    }

    public String getFecha_i_reporte() {
        return fecha_i_reporte;
    }

    public void setFecha_i_reporte(String fecha_i_reporte) {
        this.fecha_i_reporte = fecha_i_reporte;
    }

    public String getFecha_f_reporte() {
        return fecha_f_reporte;
    }

    public void setFecha_f_reporte(String fecha_f_reporte) {
        this.fecha_f_reporte = fecha_f_reporte;
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
}
