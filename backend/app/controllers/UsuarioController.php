<?php
require_once 'app/models/Usuario.php';
require_once 'app/models/Torneo.php';
class UsuarioController{
    private $usuario;
    private $torneo;
    public function __construct(){
        $this->usuario = new Usuario();
        $this->torneo = new Torneo();
    }
    public function loguearse(){
        $usuario = $_POST['usuario'];
        $contrasenha = $_POST['clave'];
        $model = $this->usuario->loguear($usuario, $contrasenha);
        $resources = array();
        if(isset($model->usuario_id)){
            if($model->usuario_estado == 1){
                $resources[0] = array(
                    "valor"=>"1",
                    "idusuario"=>$model->usuario_id,
                    "rol"=>$model->rol_nombre,
                    "usuario_nombre"=>$model->usuario_nombre,
                    "usuario_foto"=>$model->usuario_foto,
                    "ubigeo_id"=>$model->ubigeo_id,
                    "posicion"=>$model->usuario_posicion,
                    "email"=>$model->usuario_email,
                    "telefono"=>$model->usuario_telefono,
                    "dni"=>$model->usuario_dni,
                    "numero"=>$model->usuario_num,
                    "habilidad"=>$model->usuario_habilidad,
                    "token_firebase"=>$model->usuario_token
                );
                $data = array("results" => $resources);
                echo json_encode($data);
            }else{
                $resources[0] = array("valor"=>"3","idusuario"=>"","rol"=>"","usuario_nombre"=>"","usuario_foto"=>"","ubigeo_id"=>"",
                "posicion"=>"",
                    "email"=>"",
                    "telefono"=>"",
                    "dni"=>"",
                    "numero"=>"",
                    "habilidad"=>""
                );
                $data = array("results" => $resources);
                echo json_encode($data);
            }
        } else {
            $resources[0] = array("valor"=>"2","idusuario"=>"","rol"=>"","usuario_nombre"=>"","usuario_foto"=>"","ubigeo_id"=>"",
                "posicion"=>"",
                "email"=>"",
                "telefono"=>"",
                "dni"=>"",
                "numero"=>"",
                "habilidad"=>"");
            $data = array("results" => $resources);
            echo json_encode($data);
        }
    }
    public function registrar() {
        $nombre = $_POST['nombre'];
        $nacimiento = $_POST['nacimiento'];
        $foto = 'media/user/default.png';
        $sexo = $_POST['sexo'];
        $usuario = $_POST['usuario'];
        $clave = $_POST['clave'];
        $posicion = $_POST['posicion'];
        $habilidad = $_POST['habilidad'];
        $num = $_POST['num'];
        $email = $_POST['email'];
        $dni = $_POST['dni'];
        $telefono = $_POST['telefono'];
        $rol_id = $_POST['rol_id'];
        $ubigeo_id = $_POST['ubigeo_id'];
        /*$nombre = 'nombre';
        $nacimiento = '1996-06-14';
        $foto = 'foto';
        $sexo = 'm';
        $usuario = 'usuario';
        $clave = 'clave';
        $posicion = 'posicion';
        $habilidad ='habilidad';
        $num = '10';
        $email = 'email';
        $dni = '12346000';
        $telefono = 'telefono';
        $rol_id = 1;
        $ubigeo_id = 1;*/
        /*$existe = $this->usuario->existe_nombre($usuario);
        if($existe->id_usuario!=null){
            $result = 3;
        }else{
            $result = $this->usuario->registrar($nombre,$apellido,$email,$usuario,$contrasenha,$rol,$usu_supe);
        }*/
        $result = $this->usuario->registrar($rol_id,$ubigeo_id,$nombre,$dni,$nacimiento,$sexo,$email,$telefono,$usuario,$clave,$posicion,$habilidad,$num,$foto);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function crear_chat() {
        $id_1 = $_POST['id_1'];
        $id_2 = $_POST['id_2'];
        $fecha = date('Y-m-d H:i:s');
        $result = $this->usuario->crear_chat($id_1,$id_2,$fecha);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function enviar_mensaje() {
        $chat_id = $_POST['id_chat'];
        $id_usuario = $_POST['id_usuario'];
        $mensaje = $_POST['mensaje'];
        $fecha_ = date('Y-m-d H:i:s');
        $fecha = date('Y-m-d');
        $hora = date('h:i a');
        $result = $this->usuario->enviar_mensaje($chat_id,$id_usuario,$mensaje,$fecha_);
        if($result==1){
            $datos = $this->usuario->listar_chat_por_id($chat_id);
            if($datos->id_usuario_1 == $id_usuario){
                $user=$this->usuario->obtener_por_id($datos->id_usuario_2);
                $user2=$this->usuario->obtener_por_id($datos->id_usuario_1);
            }else{
                $user=$this->usuario->obtener_por_id($datos->id_usuario_1);
                $user2=$this->usuario->obtener_por_id($datos->id_usuario_2);
            }
            $this->notificar_chat($user->usuario_token,$mensaje,$user2->usuario_nombre,"Mensaje",$mensaje,$chat_id,$id_usuario,$hora,$fecha);
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function subir_foto(){
        $file_path = "media/user/".basename($_FILES['imagen']['name']);
        if(move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path)){
            echo "success";
        }else{
            echo "failed";
        }
    }
    public function actualizar_perfil(){
        $usuario_id = $_POST['usuario_id'];
        $datos = $this->usuario->obtener_por_id($usuario_id);
        $file_path = "media/user/".$usuario_id.".jpg";
        if($datos->usuario_foto == $file_path){
            unlink($file_path);
        }
        move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path);
        $result = $this->usuario->actualizar_perfil($file_path,$usuario_id);
        echo json_encode($file_path,JSON_UNESCAPED_SLASHES);
    }
    public function fecha_hora_actual(){
        date_default_timezone_set('America/Lima');
        $fecha = date('Y-m-d');
        $hora = date('H:i:s');
        $resources = array();
        $resources[0] = array("fecha"=>$fecha,"hora"=>$hora);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_ciudades(){
        $model = $this->usuario->listar_ciudades();
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "ubigeo_ciudad" => $model[$i]->ubigeo_ciudad
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_chats_por_id_usuario(){
        $id_usuario = $_POST['id_usuario'];
        $model = $this->usuario->listar_chats_por_id_usuario($id_usuario);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $mensaje = $this->usuario->listar_ultimo_mensaje_de_chat($model[$i]->chat_id);
            $fecha = explode(' ',$mensaje->detalle_chat_fecha);
            $resources[$i] = array(
                "chat_id" => $model[$i]->chat_id,
                "usuario_1" => $model[$i]->usuario_1,
                "usuario_2" => $model[$i]->usuario_2,
                "chat_fecha" => $model[$i]->chat_fecha,
                "ultimo_msj" => $mensaje->detalle_chat_mensaje,
                "ultimo_msj_id" => $mensaje->detalle_chat_id,
                "ultimo_msj_fecha" => $fecha[0],
                "ultimo_msj_hora" => $fecha[1],
                "ultimo_msj_usuario" => $mensaje->id_usuario
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function notificar($token,$body,$title,$tipo,$contenido){
        $url = 'https://fcm.googleapis.com/fcm/send';
        $fields = array('to' => $token ,
            'notification' => array('body' => $body, 'title' => $title),
            'data' =>array('tipo'=> $tipo ,
                'Contenido' => $contenido
            ));
        define('GOOGLE_API_KEY', 'AIzaSyAbpEpTfha5E7CQJMLvNC3SMhF2wQnPGVc');
        $headers = array(
            'Authorization:key='.GOOGLE_API_KEY,
            'Content-Type: application/json');
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);
        if($result === false){
            die('Curl failed' . curl_error());}
        curl_close($ch);
        return $result;
    }
    public function notificar_chat($token,$body,$title,$tipo,$mensaje,$id_chat,$id_usuario,$hora,$fecha){
        $url = 'https://fcm.googleapis.com/fcm/send';
        $fields = array('to' => $token ,
            'notification' => array('body' => $body, 'title' => $title),
            'data' =>array('tipo'=> $tipo ,
                'mensaje' => $mensaje,
                'id_chat' => $id_chat,
                'id_usuario' => $id_usuario,
                'hora' => $hora,
                'fecha' => $fecha
            ));
        define('GOOGLE_API_KEY', 'AIzaSyAbpEpTfha5E7CQJMLvNC3SMhF2wQnPGVc');
        $headers = array(
            'Authorization:key='.GOOGLE_API_KEY,
            'Content-Type: application/json');
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);
        if($result === false){
            die('Curl failed' . curl_error());}
        curl_close($ch);
        return $result;
    }
    public function listar_mensajes_por_chat(){
        $id_chat = $_POST['id_chat'];
        $model = $this->usuario->listar_mensajes_por_chat($id_chat);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $fecha = explode(' ',$model[$i]->detalle_chat_fecha);
            $resources[$i] = array(
                "chat_id" => $id_chat,
                "detalle_chat_id" => $model[$i]->detalle_chat_id,
                "id_usuario" => $model[$i]->id_usuario,
                "mensaje" => $model[$i]->detalle_chat_mensaje,
                "fecha" => $fecha[0],
                "hora" => $fecha[1],
                "estado" => $model[$i]->detalle_chat_estado
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_distritos_por_ciudad(){
        $ciudad = $_POST['ciudad'];
        $model = $this->usuario->listar_distritos_por_ciudad($ciudad);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "ubigeo_id" => $model[$i]->ubigeo_id,
                "distrito" => $model[$i]->ubigeo_distrito
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function actualizar_token(){
        $usuario_id = $_POST['idusuario'];
        $token = $_POST['token'];
        $result = $this->usuario->actualizar_token($token,$usuario_id);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
}