package com.tec.bufeo.capitan.Activity.RegistroReserva;

public class DetalleColaboraciones {
    private String nombre_colaborador;
    private String colaboracion_monto_detalle;

    public DetalleColaboraciones(String nombre_colaborador,String colaboracion_monto_detalle){
        this.nombre_colaborador=nombre_colaborador;
        this.colaboracion_monto_detalle=colaboracion_monto_detalle;
    }

    public String getNombre_colaborador() {
        return nombre_colaborador;
    }

    public void setNombre_colaborador(String nombre_colaborador) {
        this.nombre_colaborador = nombre_colaborador;
    }

    public String getColaboracion_monto_detalle() {
        return colaboracion_monto_detalle;
    }

    public void setColaboracion_monto_detalle(String colaboracion_monto_detalle) {
        this.colaboracion_monto_detalle = colaboracion_monto_detalle;
    }
}
