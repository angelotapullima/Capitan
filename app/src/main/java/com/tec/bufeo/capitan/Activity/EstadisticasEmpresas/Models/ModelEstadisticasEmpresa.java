package com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models;

import java.util.List;

public class ModelEstadisticasEmpresa {

    private String Fecha;
    private String montoFinal;
    private List<DetalleEstadisticasEmpresa> estadisticaSubItems;

    public ModelEstadisticasEmpresa(String montoFinal,String fecha, List<DetalleEstadisticasEmpresa> estadisticaSubItems) {
        this.montoFinal = montoFinal;
        this.Fecha = fecha;
        this.estadisticaSubItems = estadisticaSubItems;
    }

    public String getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(String montoFinal) {
        this.montoFinal = montoFinal;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public List<DetalleEstadisticasEmpresa> getEstadisticaSubItems() {
        return estadisticaSubItems;
    }

    public void setEstadisticaSubItems(List<DetalleEstadisticasEmpresa> estadisticaSubItems) {
        this.estadisticaSubItems = estadisticaSubItems;
    }
}
