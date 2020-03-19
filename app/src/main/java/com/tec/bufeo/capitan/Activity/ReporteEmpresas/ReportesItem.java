package com.tec.bufeo.capitan.Activity.ReporteEmpresas;

import androidx.room.Entity;

import com.tec.bufeo.capitan.Activity.EstadisticasEmpresas.Models.DetalleEstadisticasEmpresa;

import java.util.List;

@Entity(tableName = "reportes")
public class ReportesItem {

    private String Fecha;
    private String montoFinal;
    private List<DetalleEstadisticasEmpresa> estadisticaSubItems;

}
