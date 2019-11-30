<?php
require_once 'app/models/Foro.php';
require_once 'app/models/Torneo.php';
class ForoController{
    private $foro;
    public function __construct(){
        $this->foro = new Foro();
        $this->torneo = new Torneo();
    }
    public function registrar() {
        $usuario_id = $_POST['usuario_id'];
        $titulo = $_POST['titulo'];
        $descripcion = $_POST['descripcion'];
        $concepto = $_POST['concepto'];
        $id_torneo = $_POST['id_torneo'];
        date_default_timezone_set('America/Lima');
        $fecha = date('Y-m-d H:i:s');
        if($_FILES['imagen']['tmp_name'] !=="") {
            $conteo_ = $this->foro->conteo_publicaciones($usuario_id);
            $conteo = $conteo_->conteo;
            $conteo++;
            if ($conteo < 10) {
                $conteo = "0" . $conteo;
            }
            $file_path = "media/foro/" . $usuario_id . "_" . $conteo . ".jpg";
            move_uploaded_file($_FILES['imagen']['tmp_name'], $file_path);
        }else{
            $file_path = 0;
        }
        $result = $this->foro->registrar($usuario_id,$titulo,$descripcion,$concepto,$id_torneo,$file_path,$fecha);
        echo json_encode($result);
    }
    public function registrar_comentario() {
        $usuario_id = $_POST['usuario_id'];
        $publicacion_id = $_POST['publicacion_id'];
        $comentario = $_POST['comentario'];
        date_default_timezone_set('America/Lima');
        $fecha = date('Y-m-d H:i:s');
        $result = $this->foro->registrar_comentario($publicacion_id,$usuario_id,$comentario,$fecha);
        echo json_encode($result);
    }
    public function listar_publicaciones(){
        $id_usuario = $_POST['id_usuario'];
        $limite_sup = $_POST['limite_sup'];
        $limite_inf = $_POST['limite_inf'];
        if($limite_sup==0){
            $model = $this->foro->listar_publicaciones();
            $ultima_noticia=$this->foro->listar_ultima_publicacion();
            $limite_sup=$ultima_noticia->publicaciones_id;
            $new = "0";
        }else{
            $model = $this->foro->listar_publicaciones_limite($limite_inf);
            $nuevos = $this->foro->listar_publicaciones_limite_sup($limite_sup);
            (count($nuevos)>0)?$new = "1":$new="0";
        }
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $date1 = new DateTime($model[$i]->publicaciones_fecha);
            $date2 = new DateTime("now");
            $diff = $date1->diff($date2);
            if($diff->y !==0){
                $time=($diff->y > 1) ? $diff->y . ' años ' : $diff->y . ' año ';
            }elseif ($diff->m !==0){
                $time=($diff->m > 1) ? $diff->m . ' meses ' : $diff->m . ' mes ';
            }elseif ($diff->d !==0){
                $time=($diff->d > 1) ? $diff->d . ' días ' : $diff->d . ' día ';
            }elseif ($diff->h !==0){
                $time=($diff->h > 1) ? $diff->h . ' horas ' : $diff->h . ' hora ';
            }elseif ($diff->i!==0){
                $time = ( ($diff->days * 24 ) * 60 ) + ( $diff->i ) . ' minutos';
            }else{
                $time = "0 min";
            }
            $likes = $this->foro->conteo_likes($model[$i]->publicaciones_id);
            $comentarios = $this->foro->conteo_comentarios($model[$i]->publicaciones_id);
            $dio_like = $this->foro->dio_like($model[$i]->publicaciones_id,$id_usuario);
            ($dio_like->id_like_pueblo==null)? $dio_like_ = 0:$dio_like_ = 1;
            if($model[$i]->publicaciones_id_torneo != 0){
                $torneo_ = $this->torneo->listar_torneo_por_id($model[$i]->publicaciones_id_torneo);
                $torneo =$torneo_->torneo_nombre;
            }else{
                $torneo=" ";
            }
            $resources[$i] = array(
                "id_publicacion" => $model[$i]->publicaciones_id,
                "usuario_nombre" => $model[$i]->usuario_nombre,
                "usuario_foto" => $model[$i]->usuario_foto,
                "titulo" => $model[$i]->publicaciones_titulo,
                "descripcion" => $model[$i]->publicaciones_descripcion,
                "concepto" => $model[$i]->publicaciones_concepto,
                "id_torneo" => $model[$i]->publicaciones_id_torneo,
                "torneo" => $torneo,
                "foto" => $model[$i]->publicaciones_foto,
                "fecha" => $time,
                "tipo" => $model[$i]->publicaciones_tipo,
                "cant_likes" => $likes->conteo,
                "cant_comentarios" => $comentarios->conteo,
                "dio_like" => $dio_like_
            );
            $limite_inf = $model[$i]->publicaciones_id;
        }
        $data = array("results" => $resources,"limite_sup" => $limite_sup,"limite_inf" => $limite_inf,"nuevos"=>$new);
        echo json_encode($data);
    }
    public function listar_comentarios(){
        $id = $_POST['publicacion_id'];
        $model = $this->foro->listar_comentarios($id);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $date1 = new DateTime($model[$i]->comentario_fecha);
            $date2 = new DateTime("now");
            $diff = $date1->diff($date2);
            if($diff->y !==0){
                $time=($diff->y > 1) ? $diff->y . ' años ' : $diff->y . ' año ';
            }elseif ($diff->m !==0){
                $time=($diff->m > 1) ? $diff->m . ' meses ' : $diff->m . ' mes ';
            }elseif ($diff->d !==0){
                $time=($diff->d > 1) ? $diff->d . ' días ' : $diff->d . ' día ';
            }elseif ($diff->h !==0){
                $time=($diff->h > 1) ? $diff->h . ' horas ' : $diff->h . ' hora ';
            }elseif ($diff->i!==0){
                $time = ( ($diff->days * 24 ) * 60 ) + ( $diff->i ) . ' minutos';
            }else{
                $time = "0 min";
            }
            $resources[$i] = array(
                "id_comentario" => $model[$i]->id_comentario,
                "id_publicacion" => $model[$i]->publicaciones_id,
                "usuario_nombre" => $model[$i]->usuario_nombre,
                "usuario_foto" => $model[$i]->usuario_foto,
                "comentario" => $model[$i]->comentario_coment,
                "fecha" => $time
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function dar_like() {
        $model = new Foro();
        $usuario_id = $_POST['usuario_id'];
        $publicacion_id = $_POST['publicacion_id'];
        $result = $this->foro->dar_like($publicacion_id,$usuario_id);
        $resources = array();
        $cant_likes = $this->foro->conteo_likes($publicacion_id);
        $resources[0] = array("resultado"=>$result,"likes"=>$cant_likes->conteo);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function quitar_like() {
        $model = new Foro();
        $usuario_id = $_POST['usuario_id'];
        $publicacion_id = $_POST['publicacion_id'];
        $result = $this->foro->quitar_like($publicacion_id,$usuario_id);
        $resources = array();
        $cant_likes = $this->foro->conteo_likes($publicacion_id);
        $resources[0] = array("resultado"=>$result,"likes"=>$cant_likes->conteo);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
}