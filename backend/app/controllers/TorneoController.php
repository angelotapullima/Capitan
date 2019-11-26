<?php
require_once 'app/models/Torneo.php';
class TorneoController{
    private $torneo;
    public function __construct(){
        $this->torneo = new Torneo();
    }
    public function registrar_equipo() {
        $usuario_id = $_POST['usuario_id'];
        $nombre = $_POST['nombre'];
        $file_path = "media/team/default.png";
        $file_path = "media/team/".$usuario_id."_".$nombre.".jpg";
        move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path);
        /*$existe = $this->usuario->existe_nombre($usuario);
        if($existe->id_usuario!=null){
            $result = 3;
        }else{
            $result = $this->usuario->registrar($nombre,$apellido,$email,$usuario,$contrasenha,$rol,$usu_supe);
        }*/
        date_default_timezone_set('America/Lima');
        $join=date('Y-m-d H:i:s');
        $result = $this->torneo->registrar_equipo($usuario_id,$nombre,$file_path,$join);
        if($result==1){
            $temporada = 1;
            $semana = 1;
            $obtener = $this->torneo->obtener_ultimo_equipo_id();
            $registrar = $this->torneo->registrar_equipo_usuario($obtener->equipo_id,$usuario_id);
            $crear_estadistica = $this->torneo->crear_estadistica($obtener->equipo_id,$temporada,$semana);
        }
        echo json_encode($result);
    }
    public function registrar_torneo() {
        $usuario_id = $_POST['usuario_id'];
        $nombre = $_POST['nombre'];
        $descripcion = $_POST['descripcion'];
        $organizador = $_POST['organizador'];
        $tipo = $_POST['tipo'];
        $costo = $_POST['costo'];
        $fecha = $_POST['fecha'];
        $hora = $_POST['hora'];
        $lugar = $_POST['lugar'];
        $result = $this->torneo->registrar_torneo($usuario_id,$nombre,$descripcion,$fecha,$hora,$lugar,$organizador,$costo,$tipo);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_grupo() {
        $id_torneo = $_POST['id_torneo'];
        $grupo_nombre = $_POST['grupo_nombre'];
        $result = $this->torneo->registrar_grupo($id_torneo,$grupo_nombre);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_instancia() {
        $id_torneo = $_POST['id_torneo'];
        $instancia_nombre = $_POST['instancia_nombre'];
        $result = $this->torneo->registrar_instancia($id_torneo,$instancia_nombre);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_partido() {
        $id_torneo_instancia = $_POST['id_torneo_instancia'];
        $id_equipo_local = $_POST['id_equipo_local'];
        $id_equipo_visita = $_POST['id_equipo_visita'];
        $fecha = $_POST['fecha'];
        $hora = $_POST['hora'];
        $result = $this->torneo->registrar_partido($id_torneo_instancia,$id_equipo_local,$id_equipo_visita,$fecha,$hora);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_equipo_en_torneo() {
        $equipo_id = $_POST['id_equipo'];
        $id_torneo_grupo = $_POST['id_torneo_grupo'];
        $result = $this->torneo->registrar_equipo_en_torneo($id_torneo_grupo,$equipo_id);
        if($result==1){
            $estadisticas_1 = $this->torneo->listar_estadisticas_por_id_equipo($equipo_id);
            $valor_1 = $estadisticas_1->torneos;
            $valor_1++;
            $this->torneo->sumar_estadistica($equipo_id,"torneos",$valor_1);
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos_por_id_usuario(){
        $id_usuario = $_POST['id_usuario'];
        $model = $this->torneo->listar_equipos_por_id_usuario($id_usuario);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "equipo_id" => $model[$i]->equipo_id,
                "nombre" => $model[$i]->equipo_nombre,
                "foto" => $model[$i]->equipo_foto,
                "capitan" => $model[$i]->usuario_id
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_estadisticas(){
        //$id_usuario = $_POST['id_usuario'];
        $model = $this->torneo->listar_estadisticas();
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "equipo_id" => $model[$i]->equipo_id,
                "nombre" => $model[$i]->equipo_nombre,
                "foto" => $model[$i]->equipo_foto,
                "temporada" => $model[$i]->temporada,
                "semana" => $model[$i]->semana,
                "puntaje_acumulado" => $model[$i]->puntaje_acumulado,
                "puntaje_semanal" => $model[$i]->puntaje_semanal,
                "retos_enviados" => $model[$i]->retos_enviados,
                "retos_recibidos" => $model[$i]->retos_recibidos,
                "retos_ganados" => $model[$i]->retos_ganados,
                "retos_empatados" => $model[$i]->retos_empatados,
                "retos_perdidos" => $model[$i]->retos_perdidos,
                "torneos" => $model[$i]->torneos
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos(){
        $model = $this->torneo->listar_equipos();
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "equipo_id" => $model[$i]->equipo_id,
                "nombre" => $model[$i]->equipo_nombre,
                "foto" => $model[$i]->equipo_foto,
                "capitan" => $model[$i]->usuario_nombre
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos_por_torneo_not(){
        $id_torneo = $_POST["id_torneo"];
        $model = $this->torneo->listar_equipos_por_torneo_not($id_torneo);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "equipo_id" => $model[$i]->equipo_id,
                "nombre" => $model[$i]->equipo_nombre,
                "foto" => $model[$i]->equipo_foto,
                "capitan" => $model[$i]->usuario_nombre
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos_por_id_usuario_not(){
        $id_usuario = $_POST['id_usuario'];
        $model = $this->torneo->listar_equipos_por_id_usuario_not($id_usuario);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "equipo_id" => $model[$i]->equipo_id,
                "nombre" => $model[$i]->equipo_nombre,
                "foto" => $model[$i]->equipo_foto,
                "capitan" => $model[$i]->usuario_nombre,
                "capitan_id" => $model[$i]->usuario_id
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_detalle_equipo(){
        $id_equipo = $_POST['id_equipo'];
        $model = $this->torneo->listar_detalle_equipo($id_equipo);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "usuario_id" => $model[$i]->equipo_id,
                "nombre" => $model[$i]->usuario_nombre,
                "foto" => $model[$i]->usuario_foto,
                "posicion" => $model[$i]->usuario_posicion,
                "habilidad" => $model[$i]->usuario_habilidad,
                "num" => $model[$i]->usuario_num
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function buscar_jugadores_nuevos(){
        $id_equipo = $_POST['id_equipo'];
        $model = $this->torneo->buscar_jugadores_nuevos($id_equipo);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "usuario_id" => $model[$i]->usuario_id,
                "nombre" => $model[$i]->usuario_nombre,
                "foto" => $model[$i]->usuario_foto
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_equipo_usuario() {
        $equipo_id = $_POST['id_equipo'];
        $usuario_id = $_POST['id_usuario'];
        $result = $this->torneo->registrar_equipo_usuario($equipo_id,$usuario_id);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function retar_equipo() {
        $retador_id = $_POST['retador'];
        $retado_id = $_POST['retado'];
        $fecha = $_POST['fecha'];
        $hora = $_POST['hora'];
        $lugar = $_POST['lugar'];
        $reto_exists = $this->torneo->existe_reto_pendiente($retador_id,$retado_id);
        if($reto_exists->reto_id == null){
            $result = $this->torneo->retar_equipo($retador_id,$retado_id,$fecha,$hora,$lugar);
            if($result==1){
                $datos = $this->torneo->listar_equipo_por_id($retado_id);
                $datos2 = $this->torneo->listar_equipo_por_id($retador_id);
                $estadisticas_1 = $this->torneo->listar_estadisticas_por_id_equipo($retador_id);
                $valor_1 = $estadisticas_1->retos_enviados;
                $valor_1++;
                $estadisticas_2 = $this->torneo->listar_estadisticas_por_id_equipo($retado_id);
                $valor_2 = $estadisticas_2->retos_recibidos;
                $valor_2++;
                $this->torneo->sumar_estadistica($retador_id,"retos_enviados",$valor_1);
                $this->torneo->sumar_estadistica($retado_id,"retos_recibidos",$valor_2);
                $this->notificar($datos->usuario_token,"Retaron a tu equipo ","Tu equipo ".$datos->equipo_nombre." fue retado por el equipo ".$datos2->equipo_nombre,"Reto","Retaron a tu equipo");
            }
        }else{
            $result = 3;
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function responder_reto() {
        $respuesta = $_POST['respuesta'];
        $id_reto = $_POST['id_reto'];
        $result = $this->torneo->responder_reto($respuesta,$id_reto);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function dar_resultado() {
        $result= 2;
        $usuario_id = $_POST['usuario_id'];
        $reto_id = $_POST['reto_id'];
        $ganador_id = $_POST['ganador_id'];
        $reto_exists = $this->torneo->listar_reto_por_id($reto_id);
        $local = $reto_exists->retador_id;
        $visita = $reto_exists->retado_id;
        if($reto_exists->ganador_estado ==0){
            $result = $this->torneo->dar_resultado($ganador_id,$reto_id);
            if($result==1){
                $result = $this->torneo->cambiar_ganador_estado(1,$reto_id);
            }
        }elseif ($reto_exists->ganador_estado ==1){
            if($ganador_id == $reto_exists->ganador_id){
                $result = $this->torneo->cambiar_ganador_estado(2,$reto_id);
                if($ganador_id!=0){
                    if($local == $ganador_id){
                        $ganador = $local;
                        $perdedor = $visita;
                    }else{
                        $ganador = $visita;
                        $perdedor = $local;
                    }
                    $estadisticas_1 = $this->torneo->listar_estadisticas_por_id_equipo($ganador);
                    $valor_1 = $estadisticas_1->retos_ganados;
                    $valor_1++;
                    $p_a = $estadisticas_1->puntaje_acumulado;
                    $p_s = $estadisticas_1->puntaje_semanal;
                    $p_a = $p_a + 3;
                    $p_s = $p_s + 3;
                    $this->torneo->sumar_estadistica($ganador,"retos_ganados",$valor_1);
                    $this->torneo->cambiar_puntajes($ganador,$p_a,$p_s);
                    $estadisticas_2 = $this->torneo->listar_estadisticas_por_id_equipo($perdedor);
                    $valor_2 = $estadisticas_2->retos_perdidos;
                    $valor_2++;
                    $this->torneo->sumar_estadistica($perdedor,"retos_perdidos",$valor_2);
                }else{
                    $estadisticas_1 = $this->torneo->listar_estadisticas_por_id_equipo($local);
                    $valor_1 = $estadisticas_1->retos_empatados;
                    $valor_1++;
                    $p_a_1 = $estadisticas_1->puntaje_acumulado;
                    $p_s_1 = $estadisticas_1->puntaje_semanal;
                    $p_a_1 = $p_a_1 + 1;
                    $p_s_1 = $p_s_1 + 1;
                    $this->torneo->sumar_estadistica($local,"retos_empatados",$valor_1);
                    $this->torneo->cambiar_puntajes($local,$p_a_1,$p_s_1);
                    $estadisticas_2 = $this->torneo->listar_estadisticas_por_id_equipo($visita);
                    $valor_2 = $estadisticas_2->retos_empatados;
                    $valor_2++;
                    $p_a_2 = $estadisticas_2->puntaje_acumulado;
                    $p_s_2 = $estadisticas_2->puntaje_semanal;
                    $p_a_2 = $p_a_2 + 1;
                    $p_s_2 = $p_s_2 + 1;
                    $this->torneo->sumar_estadistica($visita,"retos_empatados",$valor_2);
                    $this->torneo->cambiar_puntajes($visita,$p_a_2,$p_s_2);
                }
            }else{
                $result = $this->torneo->dar_resultado($ganador_id,$reto_id);
            }
        }
        if($result==1){
            $datos = $this->torneo->listar_equipo_por_id($reto_exists->retado_id);
            $datos2 = $this->torneo->listar_equipo_por_id($reto_exists->retador_id);
            if($datos->usuario_id == $usuario_id){
                $this->notificar($datos2->usuario_token,"Confirmar resultado ","¿Como quedó tu duelo? Confirma el resultado.","Resultado","¿Como quedó tu duelo? Confirma el resultado.");
            }else{
                $this->notificar($datos->usuario_token,"Confirmar resultado ","¿Como quedó tu duelo? Confirma el resultado.","Resultado","¿Como quedó tu duelo? Confirma el resultado.");
            }
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
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
        define('GOOGLE_API_KEY', 'AIzaSyB_gvN5IfIxPHiZ-rFcqUowmh7DBfUaEv4');
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
    public function notificar_dar_resultado($token,$body,$title,$tipo,$contenido){
        $url = 'https://fcm.googleapis.com/fcm/send';
        $fields = array('to' => $token ,
            'notification' => array('body' => $body, 'title' => $title,
                'click_action' => 'dar_resultado'),
            'data' =>array('tipo'=> $tipo ,
                'Contenido' => $contenido
            ));
        define('GOOGLE_API_KEY', 'AIzaSyB_gvN5IfIxPHiZ-rFcqUowmh7DBfUaEv4');
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
    public function listar_mis_retos() {
        $usuario_id = $_POST['id_usuario'];
        $model = $this->torneo->listar_mis_retos($usuario_id);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            if($model[$i]->equipo_id_1==null){
                $equipo1 = $this->torneo->listar_equipo_por_id($model[$i]->retador_id);
                $id_e1 = $equipo1->equipo_id;
                $nombre_e1 = $equipo1->equipo_nombre;
                $foto_e1 = $equipo1->equipo_foto;
            }else{
                $id_e1 = $model[$i]->equipo_id_1;
                $nombre_e1 = $model[$i]->nombre_1;
                $foto_e1 = $model[$i]->foto_1;
            }
            if($model[$i]->equipo_id_2==null){
                $equipo2 = $this->torneo->listar_equipo_por_id($model[$i]->retado_id);
                $id_e2 = $equipo2->equipo_id;
                $nombre_e2 = $equipo2->equipo_nombre;
                $foto_e2 = $equipo2->equipo_foto;
            }else{
                $id_e2 = $model[$i]->equipo_id_2;
                $nombre_e2 = $model[$i]->nombre_2;
                $foto_e2 = $model[$i]->foto_2;
            }
            $date1 = new DateTime(date('Y-m-d'));
            $date2 = new DateTime($model[$i]->reto_fecha);
            $diff = $date1->diff($date2);
            ($diff->days<0)? $estado = 0:$estado = 1;
            $resources[$i] = array(
                "id_reto" => $model[$i]->reto_id,
                "equipo_id_1" => $id_e1,
                "equipo_id_2" => $id_e2,
                "nombre_1" => $nombre_e1,
                "nombre_2" => $nombre_e2,
                "foto_1" => $foto_e1,
                "foto_2" => $foto_e2,
                "fecha" => $model[$i]->reto_fecha,
                "hora" => $model[$i]->reto_hora,
                "lugar" => $model[$i]->reto_lugar,
                "respuesta" => $model[$i]->reto_respuesta,
                "ganador_id" => $model[$i]->ganador_id,
                "ganador_estado" => $model[$i]->ganador_estado,
                "estado" => $estado
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_mis_torneos() {
        $usuario_id = $_POST['id_usuario'];
        $model = $this->torneo->listar_torneos();
        $resources = array();
        $cont = 0;
        for ($i=0;$i<count($model);$i++) {
            $equipos_por_torneo = $this->torneo->listar_equipos_por_torneo($model[$i]->torneo_id);
            if($model[$i]->usuario_id==$usuario_id){
                $resources[$cont] = array(
                    "id_torneo" => $model[$i]->torneo_id,
                    "nombre" => $model[$i]->torneo_nombre,
                    "descripcion" => $model[$i]->torneo_descripcion,
                    "fecha" => $model[$i]->torneo_fecha,
                    "hora" => $model[$i]->torneo_hora,
                    "lugar" => $model[$i]->torneo_lugar,
                    "id_organizador" => $model[$i]->usuario_id,
                    "organizador" => $model[$i]->usuario_nombre,
                    "equipos" => count($equipos_por_torneo)
                );
                $cont++;
            }else{
                $equipos=[];
                for ($j=0;$j<count($equipos_por_torneo);$j++){
                    $equipo_usuario = $this->torneo->listar_usuario_en_equipo($usuario_id,$equipos_por_torneo[$j]->equipo_id);
                    if($equipo_usuario->equipo_id!==null){
                        $equipos[] = $equipo_usuario;
                    }
                }
                if(count($equipos)>0){
                    $resources[$cont] = array(
                        "id_torneo" => $model[$i]->torneo_id,
                        "nombre" => $model[$i]->torneo_nombre,
                        "descripcion" => $model[$i]->torneo_descripcion,
                        "fecha" => $model[$i]->torneo_fecha,
                        "hora" => $model[$i]->torneo_hora,
                        "lugar" => $model[$i]->torneo_lugar,
                        "id_organizador" => $model[$i]->usuario_id,
                        "organizador" => $model[$i]->usuario_nombre,
                        "equipos" => count($equipos)
                    );
                    $cont++;
                }
            }
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_torneos() {
        $model = $this->torneo->listar_torneos();
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $equipos_por_torneo = $this->torneo->listar_equipos_por_torneo($model[$i]->torneo_id);
            $resources[$i] = array(
                "id_torneo" => $model[$i]->torneo_id,
                "nombre" => $model[$i]->torneo_nombre,
                "descripcion" => $model[$i]->torneo_descripcion,
                "fecha" => $model[$i]->torneo_fecha,
                "hora" => $model[$i]->torneo_hora,
                "lugar" => $model[$i]->torneo_lugar,
                "id_organizador" => $model[$i]->usuario_id,
                "organizador" => $model[$i]->torneo_organizador,
                "costo" => $model[$i]->torneo_costo,
                "equipos" => count($equipos_por_torneo)
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos_en_torneo()
    {
        $id_torneo = $_POST['id_torneo'];
        $model = $this->torneo->listar_equipos_por_torneo($id_torneo);
        $resources = array();
        for ($i = 0; $i < count($model); $i++) {
            $resources[$i] = array(
                "equipo_id" => $model[$i]->equipo_id,
                "nombre" => $model[$i]->equipo_nombre,
                "foto" => $model[$i]->equipo_foto,
                "capitan" => $model[$i]->usuario_nombre
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
}