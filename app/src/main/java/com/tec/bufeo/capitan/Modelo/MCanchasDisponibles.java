package com.tec.bufeo.capitan.Modelo;

public class MCanchasDisponibles {


    public MCanchasDisponibles() {
    }

    private String mcd_id;
    private String mcd_id_empresa;
    private String mcd_nombre_empresa;
    private String mcd_direccion_empresa;
    private String mcd_telefono_empresa;
    private String mcd_precioD_cancha;
    private String mcd_precioN_cancha;

    public String getMcd_precioD_cancha() {
        return mcd_precioD_cancha;
    }

    public void setMcd_precioD_cancha(String mcd_precioD_cancha) {
        this.mcd_precioD_cancha = mcd_precioD_cancha;
    }

    public String getMcd_precioN_cancha() {
        return mcd_precioN_cancha;
    }

    public void setMcd_precioN_cancha(String mcd_precioN_cancha) {
        this.mcd_precioN_cancha = mcd_precioN_cancha;
    }

    public String getMcd_id_empresa() {
        return mcd_id_empresa;
    }

    public void setMcd_id_empresa(String mcd_id_empresa) {
        this.mcd_id_empresa = mcd_id_empresa;
    }

    public String getMcd_id() {
        return mcd_id;
    }

    public void setMcd_id(String mcd_id) {
        this.mcd_id = mcd_id;
    }

    public String getMcd_nombre_empresa() {
        return mcd_nombre_empresa;
    }

    public void setMcd_nombre_empresa(String mcd_nombre_empresa) {
        this.mcd_nombre_empresa = mcd_nombre_empresa;
    }

    public String getMcd_direccion_empresa() {
        return mcd_direccion_empresa;
    }

    public void setMcd_direccion_empresa(String mcd_direccion_empresa) {
        this.mcd_direccion_empresa = mcd_direccion_empresa;
    }

    public String getMcd_telefono_empresa() {
        return mcd_telefono_empresa;
    }

    public void setMcd_telefono_empresa(String mcd_telefono_empresa) {
        this.mcd_telefono_empresa = mcd_telefono_empresa;
    }


}
