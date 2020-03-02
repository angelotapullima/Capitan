<?php
require_once 'app/models/Foro.php';
require_once 'app/models/Torneo.php';
class ForoController{
    private $foro;
    private $nav;
    private $crypt;
    private $clean;
    private $log;
    private $validate;
    public function __construct(){
        $this->foro = new Foro();
        $this->torneo = new Torneo();
        $this->crypt = new Crypt();
        $this->clean = new Clean();
        $this->log = new Log();
        $this->validate = new Validate();
    }
    public function ver_publicaciones(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $foros = $this->foro->listar_publicaciones();
            $id_user =$this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_);
            for($i = 0;$i<count($foros);$i++){
                $foros[$i]->comentarios = $this->foro->listar_comentarios($foros[$i]->publicaciones_id);
                $foros[$i]->cant_likes = $this->foro->conteo_likes($foros[$i]->publicaciones_id);
                $foros[$i]->dio_like = $this->foro->dio_like($foros[$i]->publicaciones_id,$id_user);
            }
            $mis_equipos = $this->torneo->listar_equipos_por_id_usuario($id_user);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'foro/ver_publicaciones.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function registrar() {
        try {
            $ok_data = true;
            if (isset($_POST['usuario_id']) && isset($_POST['titulo']) && isset($_POST['descripcion'])&& isset($_POST['concepto'])&& isset($_POST['id_torneo'])) {
                $_POST['usuario_id'] = $this->clean->clean_post_int($_POST['usuario_id']);
                $_POST['titulo'] = $this->clean->clean_post_str($_POST['titulo']);
                $_POST['descripcion'] = $this->clean->clean_post_str($_POST['descripcion']);
                $_POST['concepto'] = $this->clean->clean_post_str($_POST['concepto']);
                $_POST['id_torneo'] = $this->clean->clean_post_int($_POST['id_torneo']);

                $ok_data = $this->clean->validate_post_int($_POST['usuario_id'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['titulo'], true, $ok_data, 300);
                $ok_data = $this->clean->validate_post_str($_POST['descripcion'], true, $ok_data, 1000);
                $ok_data = $this->clean->validate_post_str($_POST['concepto'], true, $ok_data, 50);
                $ok_data = $this->clean->validate_post_int($_POST['id_torneo'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if ($ok_data) {
                $usuario_id = $_POST['usuario_id'];
                $titulo = $_POST['titulo'];
                $descripcion = $_POST['descripcion'];
                $concepto = $_POST['concepto'];
                $id_torneo = $_POST['id_torneo'];
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
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }

        echo json_encode($result);
    }
    public function registrar_comentario() {
        try{
            $ok_data = true;
            $lista ="";
            if(isset($_POST['usuario_id']) && isset($_POST['publicacion_id']) &&isset($_POST['comentario'])){
                $_POST['usuario_id'] = $this->clean->clean_post_int($_POST['usuario_id']);
                $_POST['publicacion_id'] = $this->clean->clean_post_int($_POST['publicacion_id']);
                $_POST['comentario'] = $this->clean->clean_post_str($_POST['comentario']);

                $ok_data = $this->clean->validate_post_int($_POST['usuario_id'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['publicacion_id'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['comentario'], true, $ok_data, 200);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $usuario_id = $_POST['usuario_id'];
                $publicacion_id = $_POST['publicacion_id'];
                $comentario = $_POST['comentario'];
                $fecha = date('Y-m-d H:i:s');
                $result = $this->foro->registrar_comentario($publicacion_id,$usuario_id,$comentario,$fecha);
                if($result ==1){
                    $comentarios = $this->foro->listar_comentarios($publicacion_id);
                    foreach ($comentarios as $c){
                        $lista.="<div class=\"row\">
                                    <div class=\"col-md-2\">
                                        <img alt=\"user\" style=\"max-width: 50px\" class=\"img-thumbnail img-circle img-responsive\" src=\""._SERVER_ . $c->user_image ."\">
                                    </div>
                                    <div class=\"col-md-9\">
                                        <p style=\"text-align: left; color: green\">". $c->user_nickname."</p>
                                        <p style=\"text-align: left; word-break: break-all;background: lightgray; padding:10pt;border-radius: 10px\">". $c->comentario_coment."</p>
                                        <p style=\"text-align: right;font-size: 8pt;\">". $this->validate->time_between_dates($c->comentario_fecha,'hoy')."</p>
                                    </div>
                                </div>";
                    }
                }
            }else{
                $result = 6;
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $resources = array();
        $resources[0] = array("resultado"=>$result,"lista"=>$lista);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_publicaciones(){
        $id_usuario = $_POST['id_usuario'];
        $limite_sup = $_POST['limite_sup'];
        $limite_inf = $_POST['limite_inf'];
        if($limite_sup==0){
            $model = $this->foro->listar_publicaciones_all();
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
            ($dio_like->id_likePublicacion==null)? $dio_like_ = 0:$dio_like_ = 1;
            if($model[$i]->publicaciones_id_torneo != 0){
                $torneo_ = $this->torneo->listar_torneo_por_id($model[$i]->publicaciones_id_torneo);
                $torneo =$torneo_->torneo_nombre;
            }else{
                $torneo=" ";
            }
            $resources[$i] = array(
                "id_publicacion" => $model[$i]->publicaciones_id,
                "id_usuario" => $model[$i]->id_user,
                "usuario_nombre" => $model[$i]->user_nickname,
                "usuario_foto" => $model[$i]->user_image,
                "titulo" => $model[$i]->publicaciones_titulo,
                "descripcion" => $model[$i]->publicaciones_descripcion,
                "concepto" => $model[$i]->publicaciones_concepto,
                "estado" => $model[$i]->publicaciones_estado,
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
    public function listar_publicaciones_por_id_usuario(){
        $id_usuario = $_POST['id_usuario'];
        $limite_sup = $_POST['limite_sup'];
        $limite_inf = $_POST['limite_inf'];
        if($limite_sup==0){
            $model = $this->foro->listar_publicaciones_all_id_user($id_usuario);
            $ultima_noticia=$this->foro->listar_ultima_publicacion_id_user($id_usuario);
            $limite_sup=$ultima_noticia->publicaciones_id;
            $new = "0";
        }else{
            $model = $this->foro->listar_publicaciones_limite_id_user($limite_inf,$id_usuario);
            $nuevos = $this->foro->listar_publicaciones_limite_sup_id_user($limite_sup,$id_usuario);
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
            ($dio_like->id_likePublicacion==null)? $dio_like_ = 0:$dio_like_ = 1;
            if($model[$i]->publicaciones_id_torneo != 0){
                $torneo_ = $this->torneo->listar_torneo_por_id($model[$i]->publicaciones_id_torneo);
                $torneo =$torneo_->torneo_nombre;
            }else{
                $torneo=" ";
            }
            $resources[$i] = array(
                "id_publicacion" => $model[$i]->publicaciones_id,
                "id_usuario" => $model[$i]->id_user,
                "usuario_nombre" => $model[$i]->user_nickname,
                "usuario_foto" => $model[$i]->user_image,
                "titulo" => $model[$i]->publicaciones_titulo,
                "descripcion" => $model[$i]->publicaciones_descripcion,
                "concepto" => $model[$i]->publicaciones_concepto,
                "estado" => $model[$i]->publicaciones_estado,
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
                "usuario_nombre" => $model[$i]->user_nickname,
                "usuario_foto" => $model[$i]->user_image,
                "comentario" => $model[$i]->comentario_coment,
                "fecha" => $time
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function dar_like() {
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
        $usuario_id = $_POST['usuario_id'];
        $publicacion_id = $_POST['publicacion_id'];
        $result = $this->foro->quitar_like($publicacion_id,$usuario_id);
        $resources = array();
        $cant_likes = $this->foro->conteo_likes($publicacion_id);
        $resources[0] = array("resultado"=>$result,"likes"=>$cant_likes->conteo);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function eliminar_publicacion() {
        try{
            $ok_data = true;
            if(isset($_POST['publicacion_id'])){
                $_POST['publicacion_id'] = $this->clean->clean_post_int($_POST['publicacion_id']);
                $ok_data = $this->clean->validate_post_int($_POST['publicacion_id'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $publicacion_id = $_POST['publicacion_id'];
                /*$result = $this->foro->eliminar_comentarios_por_id_publicacion($publicacion_id);
                $pub = $this->foro->listar_publicacion($publicacion_id);
                $img = $pub->publicaciones_foto;
                if($result ==1){
                    $result = $this->foro->eliminar_likes($publicacion_id);
                    if($result ==1){
                        $result=$this->foro->eliminar_publicacion($publicacion_id);
                        if($result==1){
                            unlink(_SERVER_.$img);
                        }
                    }
                }*/
                $result=$this->foro->desactivar_publicacion($publicacion_id);
            }else{
                $result = 6;
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }
}