package com.tec.bufeo.capitan.Modelo;

import java.util.List;

public class Empresas {

    private String empresas_id;
    private String usuario_id;
    private String empresas_nombre;
    private String empresas_foto;
    private String empresas_descripcion;
    private String empresas_direccion;
    private String empresas_valoracion;
    private String empresas_promedio;
    private String empresas_conteo;
    private String empresas_estado;
    private String ubigeo_id;
    private String empresas_horario;
    private String empresas_telefono_1;
    private String empresas_telefono_2;
    private String empresa_cancha_fecha_reporte;
    private String cancha_precioD;
    private String cancha_precioN;
    private String horario_ls;
    private String horario_d;
    private String precio;
    private String latitud;
    private String longitud;
    private String Hora_reserva;
    private List<ArrayRating> arrayRatingList;

    public Empresas() {

    }

    public Empresas( String usuario_id,String empresas_id, String empresas_valoracion) {
        this.usuario_id = usuario_id;
        this.empresas_id = empresas_id;
        this.empresas_valoracion = empresas_valoracion;
    }

   /*  public Empresas(String empresas_id, String empresa_cancha_fecha) {
        this.empresas_id = empresas_id;
        this.empresa_cancha_fecha = empresa_cancha_fecha;
    }*/

    public Empresas(String empresas_id) {
        this.empresas_id = empresas_id;
    }

    public Empresas(String empresas_id, String usuario_id) {
        this.empresas_id = empresas_id;
        this.usuario_id = usuario_id;
    }



    public List<ArrayRating> getArrayRatingList() {
        return arrayRatingList;
    }

    public void setArrayRatingList(List<ArrayRating> arrayRatingList) {
        this.arrayRatingList = arrayRatingList;
    }

    public String getEmpresas_id() {
        return empresas_id;
    }

    public void setEmpresas_id(String empresas_id) {
        this.empresas_id = empresas_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getEmpresas_nombre() {
        return empresas_nombre;
    }

    public void setEmpresas_nombre(String empresas_nombre) {
        this.empresas_nombre = empresas_nombre;
    }

    public String getEmpresas_foto() {
        return empresas_foto;
    }

    public void setEmpresas_foto(String empresas_foto) {
        this.empresas_foto = empresas_foto;
    }

    public String getEmpresas_descripcion() {
        return empresas_descripcion;
    }

    public void setEmpresas_descripcion(String empresas_descripcion) {
        this.empresas_descripcion = empresas_descripcion;
    }

    public String getEmpresas_direccion() {
        return empresas_direccion;
    }

    public void setEmpresas_direccion(String empresas_direccion) {
        this.empresas_direccion = empresas_direccion;
    }

    public String getEmpresas_valoracion() {
        return empresas_valoracion;
    }

    public void setEmpresas_valoracion(String empresas_valoracion) {
        this.empresas_valoracion = empresas_valoracion;
    }

    public String getEmpresas_estado() {
        return empresas_estado;
    }

    public void setEmpresas_estado(String empresas_estado) {
        this.empresas_estado = empresas_estado;
    }

    public String getUbigeo_id() {
        return ubigeo_id;
    }

    public void setUbigeo_id(String ubigeo_id) {
        this.ubigeo_id = ubigeo_id;
    }

    public String getEmpresas_horario() {
        return empresas_horario;
    }

    public void setEmpresas_horario(String empresas_horario) {
        this.empresas_horario = empresas_horario;
    }

    public String getEmpresas_telefono_1() {
        return empresas_telefono_1;
    }

    public void setEmpresas_telefono_1(String empresas_telefono_1) {
        this.empresas_telefono_1 = empresas_telefono_1;
    }

    public String getEmpresas_telefono_2() {
        return empresas_telefono_2;
    }

    public void setEmpresas_telefono_2(String empresas_telefono_2) {
        this.empresas_telefono_2 = empresas_telefono_2;
    }



    public String getEmpresa_cancha_fecha_reporte() {
        return empresa_cancha_fecha_reporte;
    }

    public void setEmpresa_cancha_fecha_reporte(String empresa_cancha_fecha_reporte) {
        this.empresa_cancha_fecha_reporte = empresa_cancha_fecha_reporte;
    }

    public String getEmpresas_promedio() {
        return empresas_promedio;
    }

    public void setEmpresas_promedio(String empresas_promedio) {
        this.empresas_promedio = empresas_promedio;
    }

    public String getEmpresas_conteo() {
        return empresas_conteo;
    }

    public void setEmpresas_conteo(String empresas_conteo) {
        this.empresas_conteo = empresas_conteo;
    }

    public String getCancha_precioD() {
        return cancha_precioD;
    }

    public void setCancha_precioD(String cancha_precioD) {
        this.cancha_precioD = cancha_precioD;
    }

    public String getCancha_precioN() {
        return cancha_precioN;
    }

    public void setCancha_precioN(String cancha_precioN) {
        this.cancha_precioN = cancha_precioN;
    }

    public String getHorario_ls() {
        return horario_ls;
    }

    public void setHorario_ls(String horario_ls) {
        this.horario_ls = horario_ls;
    }

    public String getHorario_d() {
        return horario_d;
    }

    public void setHorario_d(String horario_d) {
        this.horario_d = horario_d;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getHora_reserva() {
        return Hora_reserva;
    }

    public void setHora_reserva(String hora_reserva) {
        Hora_reserva = hora_reserva;
    }


    public static class ArrayRating{

        public ArrayRating() {
        }

        String conteo;
        String  ratingfloat;

        public String getConteo() {
            return conteo;
        }

        public void setConteo(String conteo) {
            this.conteo = conteo;
        }

        public String getRatingfloat() {
            return ratingfloat;
        }

        public void setRatingfloat(String ratingfloat) {
            this.ratingfloat = ratingfloat;
        }
    }



    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}



