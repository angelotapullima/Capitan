<?php
use Exception;
require_once 'core/Database.php';
class Usuario{
    private $pdo;
    public function __construct(){
        $this->pdo = Database::getConnection();
    }
    public function loguear($usuario, $contrasenha){
        $result = new Usuario;
        try {
            $stm = $this->pdo->prepare('select * from usuario u inner join rol r on u.rol_id = r.rol_id where u.usuario_usuario = ? and u.usuario_clave = ?');
            $stm->execute([$usuario, $contrasenha]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function registrar($rol_id,$ubigeo_id,$nombre,$dni,$nacimiento,$sexo,$email,$telefono,$usuario,$clave,$posicion,$habilidad,$num,$foto){
        $result = 2;
        try {
            $sql = 'insert into usuario(
                    rol_id,
                    ubigeo_id,
                    usuario_nombre,
                    usuario_dni,
                    usuario_nacimiento,
                    usuario_sexo,
                    usuario_email,
                    usuario_telefono,
                    usuario_usuario,
                    usuario_clave,
                    usuario_posicion,
                    usuario_habilidad,
                    usuario_num,
                    usuario_foto,
                    usuario_estado
                    ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $rol_id,$ubigeo_id,$nombre,$dni,$nacimiento,$sexo,$email,$telefono,$usuario,$clave,$posicion,$habilidad,$num,$foto
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function crear_chat($id_1,$id_2,$fecha){
        $result = 2;
        try {
            $sql = 'insert into chat(
                    id_usuario_1,
                    id_usuario_2,
                    chat_fecha
                    ) values(?,?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id_1,$id_2,$fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function enviar_mensaje($chat_id,$id_usuario,$mensaje,$fecha){
        $result = 2;
        try {
            $sql = 'insert into detalle_chat(
                    chat_id,
                    id_usuario,
                    detalle_chat_mensaje,
                    detalle_chat_fecha,
                    detalle_chat_estado
                    ) values(?,?,?,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $chat_id,$id_usuario,$mensaje,$fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function listar_ciudades(){
        $result = [];
        try {
            $stm = $this->pdo->prepare("SELECT DISTINCT u.ubigeo_ciudad FROM ubigeo u");
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_ultimo_mensaje_de_chat($id_chat){
        $result = new Usuario;
        try {
            $stm = $this->pdo->prepare('select * from detalle_chat where chat_id = ? order by detalle_chat_id desc limit 1');
            $stm->execute([$id_chat]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_distritos_por_ciudad($ciudad){
        $result = [];
        try {
            $stm = $this->pdo->prepare("SELECT DISTINCT u.ubigeo_distrito,u.ubigeo_id from ubigeo u where u.ubigeo_ciudad=?");
            $stm->execute([$ciudad]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_chats_por_id_usuario($id_usuario){
        $result = [];
        try {
            $stm = $this->pdo->prepare("SELECT chat_id,(SELECT us.usuario_nombre from usuario us where us.usuario_id = c.id_usuario_1) as usuario_1,(SELECT us.usuario_nombre from usuario us where us.usuario_id = c.id_usuario_2) as usuario_2,chat_fecha from chat c where c.id_usuario_1=? or c.id_usuario_2=?");
            $stm->execute([$id_usuario,$id_usuario]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_mensajes_por_chat($id_chat){
        $result = [];
        try {
            $stm = $this->pdo->prepare("SELECT * from detalle_chat where chat_id=?");
            $stm->execute([$id_chat]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function actualizar_token($token,$id){
        $result = 2;
        try {
            $sql = 'update usuario set usuario_token = ? where usuario_id= ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $token,$id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function listar_ciudad_por_id_ubigeo($id){
        $result = new Usuario;
        try {
            $stm = $this->pdo->prepare('SELECT DISTINCT u.ubigeo_ciudad as ciudad FROM ubigeo u where u.ubigeo_id= ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function obtener_por_id($id){
        $result = new Usuario;
        try {
            $stm = $this->pdo->prepare('select * from usuario us inner join ubigeo ub on us.ubigeo_id=ub.ubigeo_id where usuario_id = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_chat_por_id($id){
        $result = new Usuario;
        try {
            $stm = $this->pdo->prepare('select * from chat where chat_id = ?');
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function actualizar_perfil($foto,$id){
        $result = 2;
        try {
            $sql = 'update usuario set usuario_foto = ? where usuario_id= ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $foto,$id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
}