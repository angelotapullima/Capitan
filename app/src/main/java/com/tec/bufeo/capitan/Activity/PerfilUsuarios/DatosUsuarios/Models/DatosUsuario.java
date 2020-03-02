package com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "datos_usuario")
public class DatosUsuario
{

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id_user")
    @SerializedName("id_user")
    private String id_user;

    @ColumnInfo(name = "id_person")
    @SerializedName("id_person")
    private String id_person;

    @ColumnInfo(name = "nombre")
    @SerializedName("nombre")
    private String nombre;

    @ColumnInfo(name = "dni")
    @SerializedName("dni")
    private String dni;

    @ColumnInfo(name = "celular")
    @SerializedName("celular")
    private String celular;

    @ColumnInfo(name = "sexo")
    @SerializedName("sexo")
    private String sexo;

    @ColumnInfo(name = "birthday")
    @SerializedName("birthday")
    private String birthday;

    @ColumnInfo(name = "direccion")
    @SerializedName("direccion")
    private String direccion;

    @ColumnInfo(name = "nickname")
    @SerializedName("nickname")
    private String nickname;

    @ColumnInfo(name = "posicion")
    @SerializedName("posicion")
    private String posicion;

    @ColumnInfo(name = "habilidad")
    @SerializedName("habilidad")
    private String habilidad;

    @ColumnInfo(name = "num")
    @SerializedName("num")
    private String num;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    private String email;

    @ColumnInfo(name = "img")
    @SerializedName("img")
    private String img;

    @ColumnInfo(name = "estado")
    @SerializedName("estado")
    private String estado;

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    private String created_at;

    @NonNull
    public String getId_user() {
        return id_user;
    }

    public void setId_user(@NonNull String id_user) {
        this.id_user = id_user;
    }

    public String getId_person() {
        return id_person;
    }

    public void setId_person(String id_person) {
        this.id_person = id_person;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(String habilidad) {
        this.habilidad = habilidad;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}