package com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models;

import java.util.List;

public class DetalleEstadisticasEmpresa {

    private String cancha_id;
    private String reserva_nombre;
    private String reserva_hora;
    private String reserva_pago1;
    private String cancha_nombre;
    private String monto_cancha;
    private String reserva_tipopago;
    private List<DetalleEstadisticasEmpresa> detalleEstadisticasEmpresas;


    public DetalleEstadisticasEmpresa(String reserva_nombre, String reserva_hora, String reserva_pago1,String cancha_nombre,String reserva_tipopago) {

        this.reserva_nombre = reserva_nombre;
        this.reserva_hora = reserva_hora;
        this.reserva_pago1 = reserva_pago1;
        this.cancha_nombre = cancha_nombre;
        this.reserva_tipopago = reserva_tipopago;
    }

    public DetalleEstadisticasEmpresa(String monto_cancha, String cancha_nombre, List<DetalleEstadisticasEmpresa> detalleEstadisticasEmpresas) {
        this.monto_cancha = monto_cancha;
        this.cancha_nombre = cancha_nombre;
        this.detalleEstadisticasEmpresas = detalleEstadisticasEmpresas;
    }

    public String getMonto_cancha() {
        return monto_cancha;
    }

    public void setMonto_cancha(String monto_cancha) {
        this.monto_cancha = monto_cancha;
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

    public List<DetalleEstadisticasEmpresa> getDetalleEstadisticasEmpresas() {
        return detalleEstadisticasEmpresas;
    }

    public void setDetalleEstadisticasEmpresas(List<DetalleEstadisticasEmpresa> detalleEstadisticasEmpresas) {
        this.detalleEstadisticasEmpresas = detalleEstadisticasEmpresas;
    }

    public String getCancha_nombre() {
        return cancha_nombre;
    }

    public void setCancha_nombre(String cancha_nombre) {
        this.cancha_nombre = cancha_nombre;
    }

    public String getReserva_tipopago() {
        return reserva_tipopago;
    }

    public void setReserva_tipopago(String reserva_tipopago) {
        this.reserva_tipopago = reserva_tipopago;
    }
}
