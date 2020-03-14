package com.tec.bufeo.capitan.Activity.Negocios.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "negocios")
public class Negocios {


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_empresa")
    @SerializedName("id_empresa")
    private String id_empresa;

    @ColumnInfo(name = "nombre_empresa")
    @SerializedName("nombre_empresa")
    private String nombre_empresa;

    @ColumnInfo(name = "direccion_empresa")
    @SerializedName("direccion_empresa")
    private String direccion_empresa;

    @ColumnInfo(name = "telefono_1_empresa")
    @SerializedName("telefono_1_empresa")
    private String telefono_1_empresa;

    @ColumnInfo(name = "telefono_2_empresa")
    @SerializedName("telefono_2_empresa")
    private String telefono_2_empresa;

    @ColumnInfo(name = "descripcion_empresa")
    @SerializedName("descripcion_empresa")
    private String descripcion_empresa;

    @ColumnInfo(name = "valoracion_empresa")
    @SerializedName("valoracion_empresa")
    private String valoracion_empresa;

    @ColumnInfo(name = "foto_empresa")
    @SerializedName("foto_empresa")
    private String foto_empresa;

    @ColumnInfo(name = "estado_empresa")
    @SerializedName("estado_empresa")
    private String estado_empresa;

    @ColumnInfo(name = "usuario_empresa")
    @SerializedName("usuario_empresa")
    private String usuario_empresa;

    @ColumnInfo(name = "distrito_empresa")
    @SerializedName("distrito_empresa")
    private String distrito_empresa;

    @ColumnInfo(name = "horario_ls_empresa")
    @SerializedName("horario_ls_empresa")
    private String horario_ls_empresa;

    @ColumnInfo(name = "horario_d_empresa")
    @SerializedName("horario_d_empresa")
    private String horario_d_empresa;

    @ColumnInfo(name = "promedio_empresa")
    @SerializedName("promedio_empresa")
    private String promedio_empresa;

    @ColumnInfo(name = "conteo_empresa")
    @SerializedName("conteo_empresa")
    private String conteo_empresa;


    @ColumnInfo(name = "rating_empresa_valor")
    @SerializedName("rating_empresa_valor")
    private String rating_empresa_valor;

    @ColumnInfo(name = "rating_conteo")
    @SerializedName("rating_conteo")
    private String rating_conteo;

    @ColumnInfo(name = "soy_admin")
    @SerializedName("soy_admin")
    private String soy_admin;

    @ColumnInfo(name = "fecha_actual")
    @SerializedName("fecha_actual")
    private String fecha_actual;

    @ColumnInfo(name = "hora_actual")
    @SerializedName("hora_actual")
    private String hora_actual;


    @ColumnInfo(name = "dia_actual")
    @SerializedName("dia_actual")
    private String dia_actual;



    @NonNull
    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(@NonNull String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getDireccion_empresa() {
        return direccion_empresa;
    }

    public void setDireccion_empresa(String direccion_empresa) {
        this.direccion_empresa = direccion_empresa;
    }

    public String getTelefono_1_empresa() {
        return telefono_1_empresa;
    }

    public void setTelefono_1_empresa(String telefono_1_empresa) {
        this.telefono_1_empresa = telefono_1_empresa;
    }

    public String getTelefono_2_empresa() {
        return telefono_2_empresa;
    }

    public void setTelefono_2_empresa(String telefono_2_empresa) {
        this.telefono_2_empresa = telefono_2_empresa;
    }

    public String getDescripcion_empresa() {
        return descripcion_empresa;
    }

    public void setDescripcion_empresa(String descripcion_empresa) {
        this.descripcion_empresa = descripcion_empresa;
    }

    public String getValoracion_empresa() {
        return valoracion_empresa;
    }

    public void setValoracion_empresa(String valoracion_empresa) {
        this.valoracion_empresa = valoracion_empresa;
    }

    public String getFoto_empresa() {
        return foto_empresa;
    }

    public void setFoto_empresa(String foto_empresa) {
        this.foto_empresa = foto_empresa;
    }

    public String getEstado_empresa() {
        return estado_empresa;
    }

    public void setEstado_empresa(String estado_empresa) {
        this.estado_empresa = estado_empresa;
    }

    public String getUsuario_empresa() {
        return usuario_empresa;
    }

    public void setUsuario_empresa(String usuario_empresa) {
        this.usuario_empresa = usuario_empresa;
    }

    public String getDistrito_empresa() {
        return distrito_empresa;
    }

    public void setDistrito_empresa(String distrito_empresa) {
        this.distrito_empresa = distrito_empresa;
    }

    public String getHorario_ls_empresa() {
        return horario_ls_empresa;
    }

    public void setHorario_ls_empresa(String horario_ls_empresa) {
        this.horario_ls_empresa = horario_ls_empresa;
    }

    public String getPromedio_empresa() {
        return promedio_empresa;
    }

    public void setPromedio_empresa(String promedio_empresa) {
        this.promedio_empresa = promedio_empresa;
    }

    public String getConteo_empresa() {
        return conteo_empresa;
    }

    public void setConteo_empresa(String conteo_empresa) {
        this.conteo_empresa = conteo_empresa;
    }

    public String getRating_empresa_valor() {
        return rating_empresa_valor;
    }

    public void setRating_empresa_valor(String rating_empresa_valor) {
        this.rating_empresa_valor = rating_empresa_valor;
    }

    public String getRating_conteo() {
        return rating_conteo;
    }

    public void setRating_conteo(String rating_conteo) {
        this.rating_conteo = rating_conteo;
    }

    public String getSoy_admin() {
        return soy_admin;
    }

    public void setSoy_admin(String soy_admin) {
        this.soy_admin = soy_admin;
    }

    public String getHorario_d_empresa() {
        return horario_d_empresa;
    }

    public void setHorario_d_empresa(String horario_d_empresa) {
        this.horario_d_empresa = horario_d_empresa;
    }

    public String getFecha_actual() {
        return fecha_actual;
    }

    public void setFecha_actual(String fecha_actual) {
        this.fecha_actual = fecha_actual;
    }

    public String getHora_actual() {
        return hora_actual;
    }

    public void setHora_actual(String hora_actual) {
        this.hora_actual = hora_actual;
    }

    public String getDia_actual() {
        return dia_actual;
    }

    public void setDia_actual(String dia_actual) {
        this.dia_actual = dia_actual;
    }
}
