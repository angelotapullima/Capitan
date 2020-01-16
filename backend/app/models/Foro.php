<?php
class Foro{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }
    public function registrar($usuario_id,$titulo,$descripcion,$concepto,$id_torneo,$file_path,$fecha){
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
                $usuario_id,$titulo,$descripcion,$concepto,$id_torneo,$file_path,$fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function registrar_comentario($publicacion_id,$usuario_id,$comentario,$fecha){
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
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_publicaciones(){
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join user u on u.id_user=p.usuario_id where p.publicaciones_estado=1 order by publicaciones_id desc");
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_ultima_publicacion(){
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 order by publicaciones_id desc limit 1");
            $stm->execute();
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_publicaciones_limite($limite_inf){
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 and publicaciones_id < ? order by publicaciones_id desc limit 10");
            $stm->execute([$limite_inf]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_publicaciones_limite_sup($limite_sup){
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 and publicaciones_id > ? order by publicaciones_id desc");
            $stm->execute([$limite_sup]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_comentarios($id){
        try {
            $stm = $this->pdo->prepare("select * from comentarios c inner join user u on u.id_user=c.id_usuario inner join publicaciones p on p.publicaciones_id = c.id_publicacion where c.id_publicacion =? order by comentario_fecha desc");
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function conteo_publicaciones($id){
        try {
            $stm = $this->pdo->prepare('select count(p.publicaciones_id) as conteo from user us inner join publicaciones p on us.id_user = p.usuario_id where us.id_user = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function dar_like($publicaciones_id,$usuario_id){
        try {
            $sql = 'insert into likePublicacion(publicaciones_id,usuario_id) values(?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $publicaciones_id,$usuario_id
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function quitar_like($publicaciones_id,$usuario_id){
        try {
            $sql = 'delete from likePublicacion where publicaciones_id=? and usuario_id = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $publicaciones_id,$usuario_id
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function conteo_likes($id){
        try {
            $stm = $this->pdo->prepare('select count(lp.publicaciones_id) as conteo from publicaciones p inner join likePublicacion lp on p.publicaciones_id=lp.publicaciones_id where lp.publicaciones_id = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function conteo_comentarios($id){
        try {
            $stm = $this->pdo->prepare('select count(c.id_publicacion) as conteo from publicaciones p inner join comentarios c on p.publicaciones_id=c.id_publicacion where c.id_publicacion = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function dio_like($id_publicacion,$id_usuario){
        try {
            $stm = $this->pdo->prepare('select * from likePublicacion where publicaciones_id = ? and usuario_id =?');
            $stm->execute([$id_publicacion,$id_usuario]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
}