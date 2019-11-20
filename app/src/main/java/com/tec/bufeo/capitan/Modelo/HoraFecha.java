package com.tec.bufeo.capitan.Modelo;
public class HoraFecha {

    private String h_f_id;
    private String h_f_hora;
    private String h_f_fecha;


    public HoraFecha() {
    }

    public HoraFecha(String h_f_id, String h_f_hora, String h_f_fecha) {
        this.h_f_id = h_f_id;
        this.h_f_hora = h_f_hora;
        this.h_f_fecha = h_f_fecha;
    }

    public String getH_f_id() {
        return h_f_id;
    }

    public void setH_f_id(String h_f_id) {
        this.h_f_id = h_f_id;
    }

    public String getH_f_hora() {
        return h_f_hora;
    }

    public void setH_f_hora(String h_f_hora) {
        this.h_f_hora = h_f_hora;
    }

    public String getH_f_fecha() {
        return h_f_fecha;
    }

    public void setH_f_fecha(String h_f_fecha) {
        this.h_f_fecha = h_f_fecha;
    }
}
