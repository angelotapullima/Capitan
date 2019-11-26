<?php
use Exception;
require_once 'core/Database.php';
class Foro{
    private $pdo;
    public function __construct(){
        $this->pdo = Database::getConnection();
    }
    public function registrar($usuario_id,$titulo,$descripcion,$concepto,$id_torneo,$file_path,$fecha){
        $result = 2;
        try {
            $sql = 'insert into publicaciones(
                    usuario_id,
                    publicaciones_titulo,
                    publicaciones_descripcion,
                    publicaciones_concepto,
                    publicaciones_id_torneo,
                    publicaciones_foto,
                    publicaciones_fecha,
                    publicaciones_tipo,
                    publicaciones_estado
                    ) values(?,?,?,?,?,?,?,1,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $usuario_id,$titulo,$descripcion,$file_path,$fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function registrar_comentario($publicacion_id,$usuario_id,$comentario,$fecha){
        $result = 2;
        try {
            $sql = 'insert into comentarios(
                    id_publicacion,
                    id_usuario,
                    comentario_coment,
                    comentario_fecha
                    ) values(?,?,?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $publicacion_id,$usuario_id,$comentario,$fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function listar_publicaciones(){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 order by publicaciones_id desc");
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_ultima_publicacion(){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 order by publicaciones_id desc limit 1");
            $stm->execute();
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_publicaciones_limite($limite_inf){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 and publicaciones_id < ? order by publicaciones_id desc limit 10");
            $stm->execute([$limite_inf]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_publicaciones_limite_sup($limite_sup){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 and publicaciones_id > ? order by publicaciones_id desc");
            $stm->execute([$limite_sup]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_comentarios($id){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from comentarios c inner join usuario u on u.usuario_id=c.id_usuario inner join publicaciones p on p.publicaciones_id = c.id_publicacion where c.id_publicacion =? order by comentario_fecha desc");
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function conteo_publicaciones($id){
        $result = new Foro;
        try {
            $stm = $this->pdo->prepare('select count(p.publicaciones_id) as conteo from usuario us inner join publicaciones p on us.usuario_id = p.usuario_id where us.usuario_id = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function dar_like($publicaciones_id,$usuario_id){
        $result = 2;
        try {
            $sql = 'insert into likePublicacion(publicaciones_id,usuario_id) values(?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $publicaciones_id,$usuario_id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function quitar_like($publicaciones_id,$usuario_id){
        $result = 2;
        try {
            $sql = 'delete from likePublicacion where publicaciones_id=? and usuario_id = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $publicaciones_id,$usuario_id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function conteo_likes($id){
        $result = new Foro;
        try {
            $stm = $this->pdo->prepare('select count(lp.publicaciones_id) as conteo from publicaciones p inner join likePublicacion lp on p.publicaciones_id=lp.publicaciones_id where lp.publicaciones_id = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function conteo_comentarios($id){
        $result = new Foro;
        try {
            $stm = $this->pdo->prepare('select count(c.id_publicacion) as conteo from publicaciones p inner join comentarios c on p.publicaciones_id=c.id_publicacion where c.id_publicacion = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function dio_like($id_pueblo,$id_usuario){
        $result = new Foro;
        try {
            $stm = $this->pdo->prepare('select * from likePublicacion where publicaciones_id = ? and usuario_id =?');
            $stm->execute([$id_pueblo,$id_usuario]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
}