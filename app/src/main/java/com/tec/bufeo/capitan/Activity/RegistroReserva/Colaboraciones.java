package com.tec.bufeo.capitan.Activity.RegistroReserva;

import java.util.List;

public class Colaboraciones {

    private String colaboracion_id;
    private String equipo_id;
    private String equipo_nombre;
    private String colaboracion_nombre;
    private String colaboracion_monto;
    private String colaboracion_date;
    private String monto_final;
    private List<DetalleColaboraciones> detalleColaboracionesList;

    public Colaboraciones(String colaboracion_id,String equipo_id ,String equipo_nombre,String colaboracion_nombre,String colaboracion_date,String monto_final, List<DetalleColaboraciones> detalleColaboracionesList) {
        this.colaboracion_id = colaboracion_id;
        this.equipo_id = equipo_id;
        this.equipo_nombre = equipo_nombre;
        this.colaboracion_nombre = colaboracion_nombre;
        this.colaboracion_date = colaboracion_date;
        this.monto_final = monto_final;
        this.detalleColaboracionesList = detalleColaboracionesList;
    }

    public List<DetalleColaboraciones> getDetalleColaboracionesList() {
        return detalleColaboracionesList;
    }

    public void setDetalleColaboracionesList(List<DetalleColaboraciones> detalleColaboracionesList) {
        this.detalleColaboracionesList = detalleColaboracionesList;
    }

    public String getColaboracion_id() {
        return colaboracion_id;
    }

    public void setColaboracion_id(String colaboracion_id) {
        this.colaboracion_id = colaboracion_id;
    }

    public String getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(String equipo_id) {
        this.equipo_id = equipo_id;
    }

    public String getEquipo_nombre() {
        return equipo_nombre;
    }

    public void setEquipo_nombre(String equipo_nombre) {
        this.equipo_nombre = equipo_nombre;
    }

    public String getColaboracion_nombre() {
        return colaboracion_nombre;
    }

    public void setColaboracion_nombre(String colaboracion_nombre) {
        this.colaboracion_nombre = colaboracion_nombre;
    }

    public String getColaboracion_monto() {
        return colaboracion_monto;
    }

    public void setColaboracion_monto(String colaboracion_monto) {
        this.colaboracion_monto = colaboracion_monto;
    }

    public String getColaboracion_date() {
        return colaboracion_date;
    }

    public void setColaboracion_date(String colaboracion_date) {
        this.colaboracion_date = colaboracion_date;
    }

    public String getMonto_final() {
        return monto_final;
    }

    public void setMonto_final(String monto_final) {
        this.monto_final = monto_final;
    }
}
