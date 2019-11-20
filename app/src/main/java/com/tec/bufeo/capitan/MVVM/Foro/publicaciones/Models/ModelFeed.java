package com.tec.bufeo.capitan.MVVM.Foro.publicaciones.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "feed")
public class ModelFeed {

    /*@PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "foro_id")
    @SerializedName("foro_id")
    private int foro_id;*/


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "publicacion_id")
    @SerializedName("publicacion_id")
    private String publicacion_id;

    @ColumnInfo(name = "foro_foto")
    @SerializedName("foro_foto")
    private String foro_foto;


    @ColumnInfo(name = "usuario_nombre")
    @SerializedName("usuario_nombre")
    private String usuario_nombre;


    @ColumnInfo(name = "usuario_id")
    @SerializedName("usuario_id")
    private String usuario_id;


    @ColumnInfo(name = "foro_titulo")
    @SerializedName("foro_titulo")
    private String foro_titulo;


    @ColumnInfo(name = "foro_descripcion")
    @SerializedName("foro_descripcion")
    private String foro_descripcion;


    @ColumnInfo(name = "foro_feccha")
    @SerializedName("foro_feccha")
    private String foro_feccha;


    @ColumnInfo(name = "foro_conteo")
    @SerializedName("foro_conteo")
    private String foro_conteo;


    @ColumnInfo(name = "foro_tipo")
    @SerializedName("foro_tipo")
    private String foro_tipo;

    @ColumnInfo(name = "cant_likes")
    @SerializedName("cant_likes")
    private String cant_likes;

    @ColumnInfo(name = "cant_Comentarios")
    @SerializedName("cant_Comentarios")
    private String cant_Comentarios;

    @ColumnInfo(name = "dio_like")
    @SerializedName("dio_like")
    private String dio_like;

    @ColumnInfo(name = "usuario_foto")
    @SerializedName("usuario_foto")
    private String usuario_foto;

    @ColumnInfo(name = "orden")
    @SerializedName("orden")
    private String orden;


    @NonNull
    public String getPublicacion_id() {
        return publicacion_id;
    }

    public void setPublicacion_id(@NonNull String publicacion_id) {
        this.publicacion_id = publicacion_id;
    }

    public String getForo_foto() {
        return foro_foto;
    }

    public void setForo_foto(String foro_foto) {
        this.foro_foto = foro_foto;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getForo_titulo() {
        return foro_titulo;
    }

    public void setForo_titulo(String foro_titulo) {
        this.foro_titulo = foro_titulo;
    }

    public String getForo_descripcion() {
        return foro_descripcion;
    }

    public void setForo_descripcion(String foro_descripcion) {
        this.foro_descripcion = foro_descripcion;
    }

    public String getForo_feccha() {
        return foro_feccha;
    }

    public void setForo_feccha(String foro_feccha) {
        this.foro_feccha = foro_feccha;
    }

    public String getForo_conteo() {
        return foro_conteo;
    }

    public void setForo_conteo(String foro_conteo) {
        this.foro_conteo = foro_conteo;
    }

    public String getForo_tipo() {
        return foro_tipo;
    }

    public void setForo_tipo(String foro_tipo) {
        this.foro_tipo = foro_tipo;
    }

    public String getCant_likes() {
        return cant_likes;
    }

    public void setCant_likes(String cant_likes) {
        this.cant_likes = cant_likes;
    }

    public String getCant_Comentarios() {
        return cant_Comentarios;
    }

    public void setCant_Comentarios(String cant_Comentarios) {
        this.cant_Comentarios = cant_Comentarios;
    }

    public String getDio_like() {
        return dio_like;
    }

    public void setDio_like(String dio_like) {
        this.dio_like = dio_like;
    }

    public String getUsuario_foto() {
        return usuario_foto;
    }

    public void setUsuario_foto(String usuario_foto) {
        this.usuario_foto = usuario_foto;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "ModelFeed{" +
                "foro_publicacion_id='" + publicacion_id + '\'' +
                ", foro_foto='" + foro_foto + '\'' +
                ", usuario_nombre='" + usuario_nombre + '\'' +
                ", usuario_id='" + usuario_id + '\'' +
                ", foro_titulo='" + foro_titulo + '\'' +
                ", foro_descripcion='" + foro_descripcion + '\'' +
                ", foro_feccha='" + foro_feccha + '\'' +
                ", foro_conteo='" + foro_conteo + '\'' +
                ", foro_tipo='" + foro_tipo + '\'' +
                ", cant_likes='" + cant_likes + '\'' +
                ", cant_Comentarios='" + cant_Comentarios + '\'' +
                ", dio_like='" + dio_like + '\'' +
                ", usuario_foto='" + usuario_foto + '\'' +
                ", orden='" + orden + '\'' +
                '}';
    }


    /*@Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", body = "+body+", title = "+title+"]";
    }*/
}

/*
@Entity(tableName = "post_info")


return "ClassPojo [id = "+id+", body = "+body+", title = "+title+"]";

 public class ModelFeed implements Parcelable{
        @NonNull
        @ColumnInfo(name = "userId")
        @SerializedName("userId")
        @Expose
        private Integer userId;

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        @SerializedName("id")
        @Expose
        private Integer id;

        @NonNull
        @ColumnInfo(name = "title")
        @SerializedName("title")
        @Expose
        private String title;

        @NonNull
        @ColumnInfo(name = "body")
        @SerializedName("body")
        @Expose
        private String body;

        public final Creator<ModelFeed> CREATOR = new Creator<ModelFeed>() {

            @SuppressWarnings({
                    "unchecked"
            })
            public ModelFeed createFromParcel(Parcel in) {
                ModelFeed instance = new ModelFeed();

                instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));

                instance.title = ((String) in.readValue((String.class.getClassLoader())));

                instance.body = ((String) in.readValue((String.class.getClassLoader())));

                return instance;
            }

            public ModelFeed[] newArray(int size) {
                return (new ModelFeed[size]);
            }

        };



        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }



        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }



        public void writeToParcel(Parcel dest, int flags) {

            dest.writeValue(id);

            dest.writeValue(title);

            dest.writeValue(body);

        }

        public int describeContents() {
            return 0;
        }
    }

*/
