package com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tec.bufeo.capitan.Activity.PerfilUsuarios.DatosUsuarios.Models.DatosUsuario;
import com.tec.bufeo.capitan.Activity.PerfilUsuarios.PublicacionesUsuario.Models.PublicacionesUsuario;

import java.util.List;

@Dao
public interface PublicacionesUsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PublicacionesUsuario publicacionesUsuario);

    @Query("SELECT * from publicaciones_usuario where estado =1  and foro_foto <> 0 and usuario_id=:id_usuario ORDER BY orden DESC")
    LiveData<List<PublicacionesUsuario>> getAllPosts(String id_usuario);



    @Query("DELETE FROM publicaciones_usuario")
    void deleteAll();

    @Query("DELETE FROM publicaciones_usuario WHERE  publicacion_id = :foro_id")
    void deleteOneFeed(String foro_id);

   /* @Query("SELECT * FROM publicaciones_usuario WHERE foro_titulo LIKE '%' || :query || '%' OR foro_descripcion LIKE '%' || :query || '%'  OR usuario_nombre LIKE '%' || :query || '%'" )
    LiveData<List<PublicacionesUsuario>> searchFeeds(String query );*/


    /*@Query("SELECT publicacion_id,limite_inf,limite_sup from feedTorneo")
    LiveData<List<FeedTorneo>> getAllIdPosts();*/

    @Query("Update publicaciones_usuario set limite_sup = :sup")
    void actualizarSup(String sup);

    @Query("Update publicaciones_usuario set limite_inf = :sup")
    void actualizarInf(String sup);




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<PublicacionesUsuario> feedTorneo);

}
