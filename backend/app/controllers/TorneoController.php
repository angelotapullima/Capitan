<?php
require_once 'app/models/Torneo.php';
require_once 'app/models/Empresa.php';
require_once 'app/models/Foro.php';
require_once 'app/models/Cuenta.php';
require_once 'app/models/User.php';
class TorneoController{
    private $torneo;
    private $nav;
    private $foro;
    private $empresa;
    private $cuenta;
    private $user;
    private $crypt;
    private $clean;
    private $log;
    private $validate;
    public function __construct(){
        $this->torneo = new Torneo();
        $this->foro = new Foro();
        $this->empresa= new Empresa();
        $this->cuenta= new Cuenta();
        $this->user= new User();
        $this->crypt = new Crypt();
        $this->clean = new Clean();
        $this->log = new Log();
        $this->validate = new Validate();
    }
    public function index(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $usuario_id = $this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_);
            $torneos = $this->torneo->listar_torneos();
            $mis_equipos = $this->torneo->listar_equipos_por_id_usuario($usuario_id);
            $mis_retos = $this->torneo->listar_mis_retos($usuario_id);
            foreach ($mis_retos as $m){
                if($m->equipo_id_1 == null){
                    $equipo___ = $this->torneo->listar_equipo_por_id($m->retador_id);
                    $m->nombre_1 = $equipo___->equipo_nombre;
                    $m->equipo_id_1 = $equipo___->equipo_id;
                    $m->foto_1 = $equipo___->equipo_foto;
                }elseif($m->equipo_id_2 == null){
                    $equipo___ = $this->torneo->listar_equipo_por_id($m->retado_id);
                    $m->nombre_2 = $equipo___->equipo_nombre;
                    $m->equipo_id_2 = $equipo___->equipo_id;
                    $m->foto_2 = $equipo___->equipo_foto;
                }
            }
            $equipos2 = $this->torneo->listar_equipos_por_id_usuario_not($usuario_id);
            $equipos = $this->torneo->listar_equipos_por_id_usuario_not($usuario_id);
            $mis_torneos = array();
            $cont = 0;
            if(count($torneos)>0){
                for ($i=0;$i<count($torneos);$i++) {
                    $equipos_por_torneo = $this->torneo->listar_equipos_por_torneo($torneos[$i]->torneo_id);
                    $torneos[$i]->equipos = count($equipos_por_torneo);
                    if($torneos[$i]->usuario_id==$usuario_id){
                        $mis_torneos[$cont] = array(
                            "id_torneo" => $torneos[$i]->torneo_id,
                            "nombre" => $torneos[$i]->torneo_nombre,
                            "descripcion" => $torneos[$i]->torneo_descripcion,
                            "fecha" => $torneos[$i]->torneo_fecha,
                            "imagen" => $torneos[$i]->torneo_imagen,
                            "costo" => $torneos[$i]->torneo_costo,
                            "hora" => $torneos[$i]->torneo_hora,
                            "lugar" => $torneos[$i]->torneo_lugar,
                            "id_organizador" => $torneos[$i]->usuario_id,
                            "organizador" => $torneos[$i]->torneo_organizador,
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
                            $mis_torneos[$cont] = array(
                                "id_torneo" => $torneos[$i]->torneo_id,
                                "nombre" => $torneos[$i]->torneo_nombre,
                                "descripcion" => $torneos[$i]->torneo_descripcion,
                                "fecha" => $torneos[$i]->torneo_fecha,
                                "hora" => $torneos[$i]->torneo_hora,
                                "lugar" => $torneos[$i]->torneo_lugar,
                                "id_organizador" => $torneos[$i]->usuario_id,
                                "organizador" => $torneos[$i]->usuario_nombre,
                                "equipos" => count($equipos)
                            );
                            $cont++;
                        }
                    }
                }
            }
            $chats = $this->user->listar_chats_por_id_usuario($usuario_id);
            $estadisticas = $this->torneo->listar_estadisticas();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'torneo/index.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function retar(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $usuario_id = $this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_);
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $equipo =$this->torneo->listar_equipo_por_id($id);
            $mis_equipos =$this->torneo->listar_equipos_por_id_usuario($usuario_id);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'torneo/retar.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function crear_torneo(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'torneo/crear_torneo.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function crear_equipo(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'torneo/crear_equipo.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver_torneo(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }

            $torneo = $this->torneo->listar_torneo_por_id($id);
            $equipos = $this->torneo->listar_equipos_por_torneo($id);
            $equipos2 = $this->torneo->listar_equipos_por_torneo($id);
            $publicaciones = $this->torneo->listar_publicaciones($id);
            $grupos = $this->torneo->listar_grupos_por_id_torneo($id);
            $goleadores = $this->torneo->listar_goleadores_por_id_torneo($id);
            $equipos_en_torneo_not = $this->torneo->listar_equipos_por_torneo_not($id);
            $resources = [];
            for ($i=0;$i<count($grupos);$i++) {
                $model = [];
                $equipos = $this->torneo->listar_equipos_por_id_grupo($grupos[$i]->id_torneo_grupo);
                for($j=0;$j<count($equipos);$j++){
                    $model[] = array(
                        "equipo_id"=>$equipos[$j]->equipo_id,
                        "equipo_nombre"=>$equipos[$j]->equipo_nombre,
                        "equipo_foto"=>$equipos[$j]->equipo_foto
                    );
                }
                $resources[] = array(
                    "id_torneo_grupo"=>$grupos[$i]->id_torneo_grupo,
                    "nombre_grupo"=>$grupos[$i]->grupo_nombre,
                    "equipos" => $model
                );
            }
            $instancias = $this->torneo->listar_instancias_por_id_torneo($id);
            $i_p = [];
            for ($i=0;$i<count($instancias);$i++) {
                $model = [];
                $partidos = $this->torneo->listar_partidos_por_id_instancia($instancias[$i]->id_torneo_instancia);
                for($j=0;$j<count($partidos);$j++){
                    if($partidos[$j]->marcador_local >0 || $partidos[$j]->marcador_visita >0){
                        $goleadores__ = $this->torneo->listar_goleadores_por_partido($partidos[$j]->id_torneo_partido);
                        $goleadores_local="";
                        $goleadores_visita="";
                        foreach ($goleadores__ as $g){
                            if($g->id_equipo == $partidos[$j]->id_equipo_local){
                                $goleadores_local .= $g->user_nickname."<br>";
                            }elseif($g->id_equipo == $partidos[$j]->id_equipo_visita){
                                $goleadores_visita .=$g->user_nickname."<br>";
                            }
                        }
                    }

                    $model[] = array(
                        "id_torneo_partido"=>$partidos[$j]->id_torneo_partido,
                        "id_equipo_local"=>$partidos[$j]->id_equipo_local,
                        "nombre_equipo_local"=>$partidos[$j]->nombre_local,
                        "foto_equipo_local"=>$partidos[$j]->foto_local,
                        "id_equipo_visita"=>$partidos[$j]->id_equipo_visita,
                        "nombre_equipo_visita"=>$partidos[$j]->nombre_visita,
                        "foto_equipo_visita"=>$partidos[$j]->foto_visita,
                        "marcador_local"=>$partidos[$j]->marcador_local,
                        "marcador_visita"=>$partidos[$j]->marcador_visita,
                        "partido_fecha"=>$partidos[$j]->torneo_partido_fecha,
                        "partido_hora"=>$partidos[$j]->torneo_partido_hora,
                        "partido_estado"=>$partidos[$j]->torneo_partido_estado,
                        "goleadores_local"=>$goleadores_local,
                        "goleadores_visita"=>$goleadores_visita
                    );
                }
                $i_p[] = array(
                    "id_instancia"=>$instancias[$i]->id_torneo_instancia,
                    "nombre_instancia"=>$instancias[$i]->torneo_instancia_nombre,
                    "partidos" => $model
                );
            }
            $posiciones = [];
            for ($i=0;$i<count($grupos);$i++) {
                $model = [];
                $equipos = $this->torneo->listar_equipos_por_id_grupo($grupos[$i]->id_torneo_grupo);
                for($j=0;$j<count($equipos);$j++){
                    $partidos = $this->torneo->listar_partidos_terminados_equipo_fase1($equipos[$j]->equipo_id);
                    $part_j = count($partidos);
                    $part_g = 0;
                    $part_e = 0;
                    $part_p = 0;
                    $gf =0 ;
                    $gc = 0;
                    for ($k=0;$k<count($partidos);$k++){
                        if($equipos[$j]->equipo_id==$partidos[$k]->id_equipo_local){
                            if($partidos[$k]->marcador_local>$partidos[$k]->marcador_visita){
                                $part_g++;
                            }elseif ($partidos[$k]->marcador_local<$partidos[$k]->marcador_visita){
                                $part_p++;
                            }else{
                                $part_e++;
                            }
                            $gf = $gf+ $partidos[$k]->marcador_local;
                            $gc = $gc+ $partidos[$k]->marcador_visita;
                        }elseif ($equipos[$j]->equipo_id==$partidos[$k]->id_equipo_visita){
                            if($partidos[$k]->marcador_visita>$partidos[$k]->marcador_local){
                                $part_g++;
                            }elseif ($partidos[$k]->marcador_visita<$partidos[$k]->marcador_local){
                                $part_p++;
                            }else{
                                $part_e++;
                            }
                            $gf = $gf+ $partidos[$k]->marcador_visita;
                            $gc = $gc+ $partidos[$k]->marcador_local;
                        }
                    }
                    $total = (3 * $part_g) + $part_e;
                    $model[] = array(
                        "equipo_id"=>$equipos[$j]->equipo_id,
                        "equipo_nombre"=>$equipos[$j]->equipo_nombre,
                        "equipo_foto"=>$equipos[$j]->equipo_foto,
                        "part_j"=>$part_j,
                        "part_g"=>$part_g,
                        "part_e"=>$part_e,
                        "part_p"=>$part_p,
                        "gf"=>$gf,
                        "gc"=>$gc,
                        "total"=>$total
                    );
                }
                $posiciones[] = array(
                    "id_grupo"=>$grupos[$i]->id_torneo_grupo,
                    "nombre_grupo"=>$grupos[$i]->grupo_nombre,
                    "equipos" => $model
                );
            }
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'torneo/ver_torneo.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver_equipo(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $id_user=$this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_);
            $saldo_Actual = $this->empresa->obtener_saldo_actual($id_user);
            $equipo = $this->torneo->listar_equipo_por_id($id);
            $jugadores = $this->torneo->listar_detalle_equipo($id);
            $chanchas = $this->empresa->obtener_chanchas_disponibles_por_equipo($id);
            $torneos = $this->torneo->listar_torneos_por_equipo($id);
            $mis_retos = $this->torneo->listar_mis_retos($id_user);
            foreach ($mis_retos as $m){
                if($m->equipo_id_1 == null){
                    $equipo___ = $this->torneo->listar_equipo_por_id($m->retador_id);
                    $m->nombre_1 = $equipo___->equipo_nombre;
                    $m->equipo_id_1 = $equipo___->equipo_id;
                    $m->foto_1 = $equipo___->equipo_foto;
                }elseif($m->equipo_id_2 == null){
                    $equipo___ = $this->torneo->listar_equipo_por_id($m->retado_id);
                    $m->nombre_2 = $equipo___->equipo_nombre;
                    $m->equipo_id_2 = $equipo___->equipo_id;
                    $m->foto_2 = $equipo___->equipo_foto;
                }
            }
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'torneo/ver_equipo.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function registrar_equipo() {
        try{
            $ok_data = true;
            if(isset($_POST['usuario_id']) && isset($_POST['nombre']) &&isset($_FILES['imagen']['tmp_name']) ){
                $_POST['usuario_id'] = $this->clean->clean_post_int($_POST['usuario_id']);
                $_POST['nombre'] = $this->clean->clean_post_str($_POST['nombre']);

                $ok_data = $this->clean->validate_post_int($_POST['usuario_id'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['nombre'], true, $ok_data, 200);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $microtime = microtime(true);
                $usuario_id = $_POST['usuario_id'];
                $nombre = $_POST['nombre'];
                if($_FILES['imagen']['tmp_name']!=null){
                    $file_path = "media/team/".$usuario_id."_".$nombre.".jpg";
                    move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path);
                }else{
                    $file_path = "media/team/default.png";
                }
                date_default_timezone_set('America/Lima');
                $join=date('Y-m-d H:i:s');
                $result = $this->torneo->registrar_equipo($usuario_id,$nombre,$file_path,$join,$microtime);
                if($result==1){
                    $temporada = 1;
                    $semana = 1;
                    $obtener = $this->torneo->obtener_equipo_mt($microtime);
                    $registrar = $this->torneo->registrar_equipo_usuario($obtener->equipo_id,$usuario_id);
                    $crear_estadistica = $this->torneo->crear_estadistica($obtener->equipo_id,$temporada,$semana);
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $resources = array();
        $resources[0] = array("valor"=>$result,"id_equipo"=>$obtener->equipo_id);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_torneo() {
        try{
            $ok_data = true;
            if(isset($_POST['usuario_id']) && isset($_POST['nombre']) &&isset($_POST['descripcion']) &&isset($_POST['organizador']) &&isset($_POST['tipo']) &&isset($_POST['costo']) &&isset($_POST['fecha']) &&isset($_POST['hora'])&&isset($_POST['lugar']) ){
                $_POST['usuario_id'] = $this->clean->clean_post_int($_POST['usuario_id']);
                $_POST['nombre'] = $this->clean->clean_post_str($_POST['nombre']);
                $_POST['descripcion'] = $this->clean->clean_post_str($_POST['descripcion']);
                $_POST['organizador'] = $this->clean->clean_post_str($_POST['organizador']);
                $_POST['tipo'] = $this->clean->clean_post_str($_POST['tipo']);
                //$_POST['costo'] = $this->clean->clean_post_int($_POST['costo']);
                $_POST['fecha'] = $this->clean->clean_post_date($_POST['fecha']);
                $_POST['hora'] = $this->clean->clean_post_str($_POST['hora']);
                $_POST['lugar'] = $this->clean->clean_post_str($_POST['lugar']);

                $ok_data = $this->clean->validate_post_int($_POST['usuario_id'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['nombre'], true, $ok_data, 100);
                $ok_data = $this->clean->validate_post_str($_POST['descripcion'], true, $ok_data, 300);
                $ok_data = $this->clean->validate_post_str($_POST['organizador'], true, $ok_data, 200);
                $ok_data = $this->clean->validate_post_str($_POST['tipo'], true, $ok_data, 50);
                //$ok_data = $this->clean->validate_post_str($_POST['costo'], true, $ok_data, 50);
                $ok_data = $this->clean->validate_post_date($_POST['fecha'], true, $ok_data, 250,1);
                $ok_data = $this->clean->validate_post_str($_POST['hora'], true, $ok_data, 50);
                $ok_data = $this->clean->validate_post_str($_POST['lugar'], true, $ok_data, 100);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $usuario_id = $_POST['usuario_id'];
                $nombre = $_POST['nombre'];
                $descripcion = $_POST['descripcion'];
                $organizador = $_POST['organizador'];
                $tipo = $_POST['tipo'];
                $costo = $_POST['costo'];
                $fecha = $_POST['fecha'];
                $hora = $_POST['hora'];
                $lugar = $_POST['lugar'];
            /*    if($_FILES['imagen']['tmp_name']!=null){
                    $file_path = "media/torneo/".$usuario_id."_".$nombre.".jpg";
                    move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path);
                }else{
                    $file_path = "media/torneo/default.png";
                }*/
                $file_path = "media/torneo/default.png";
                $result = $this->torneo->registrar_torneo($usuario_id,$nombre,$descripcion,$fecha,$hora,$lugar,$organizador,$costo,$tipo,$file_path);
                if($result==1){
                    $last_id = $this->torneo->listar_ultimo_torneo();
                }else{
                    $last_id = 0;
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }

        $resources = array();
        $resources[0] = array("valor"=>$result,"id_torneo"=>$last_id->torneo_id);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_grupo() {
        try{
            $ok_data = true;
            if(isset($_POST['id_torneo']) && isset($_POST['grupo_nombre']) ){
                $_POST['id_torneo'] = $this->clean->clean_post_int($_POST['id_torneo']);
                $_POST['grupo_nombre'] = $this->clean->clean_post_str($_POST['grupo_nombre']);

                $ok_data = $this->clean->validate_post_int($_POST['id_torneo'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['grupo_nombre'], true, $ok_data, 50);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id_torneo = $_POST['id_torneo'];
                $grupo_nombre = $_POST['grupo_nombre'];
                $result = $this->torneo->registrar_grupo($id_torneo,$grupo_nombre);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }

    public function registrar_instancia() {
        try{
            $ok_data = true;
            if(isset($_POST['id_torneo']) && isset($_POST['instancia_nombre'])&& isset($_POST['instancia_tipo']) ){
                $_POST['id_torneo'] = $this->clean->clean_post_int($_POST['id_torneo']);
                $_POST['instancia_nombre'] = $this->clean->clean_post_str($_POST['instancia_nombre']);
                $_POST['instancia_tipo'] = $this->clean->clean_post_int($_POST['instancia_tipo']);

                $ok_data = $this->clean->validate_post_int($_POST['id_torneo'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['instancia_nombre'], true, $ok_data, 200);
                $ok_data = $this->clean->validate_post_int($_POST['instancia_tipo'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id_torneo = $_POST['id_torneo'];
                $instancia_nombre = $_POST['instancia_nombre'];
                $instancia_tipo = $_POST['instancia_tipo'];
                $exists_instancia = $this->torneo->existe_instancia($id_torneo,$instancia_nombre);
                if(isset($exists_instancia->id_torneo_instancia) && $exists_instancia->id_torneo_instancia!=""){
                    $result=3;
                }else{
                    $result = $this->torneo->registrar_instancia($id_torneo,$instancia_nombre,$instancia_tipo);
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_partido() {
        try{
            $ok_data = true;
            if(isset($_POST['id_torneo_instancia']) && isset($_POST['id_equipo_local'])&& isset($_POST['id_equipo_visita'])&& isset($_POST['fecha'])&& isset($_POST['hora']) ){
                $_POST['id_torneo_instancia'] = $this->clean->clean_post_int($_POST['id_torneo_instancia']);
                $_POST['id_equipo_local'] = $this->clean->clean_post_int($_POST['id_equipo_local']);
                $_POST['id_equipo_visita'] = $this->clean->clean_post_int($_POST['id_equipo_visita']);
                $_POST['fecha'] = $this->clean->clean_post_date($_POST['fecha']);
                $_POST['hora'] = $this->clean->clean_post_str($_POST['hora']);

                $ok_data = $this->clean->validate_post_int($_POST['id_torneo_instancia'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_equipo_local'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_equipo_visita'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_date($_POST['fecha'], true, $ok_data, 200,1);
                $ok_data = $this->clean->validate_post_str($_POST['hora'], true, $ok_data, 10);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id_torneo_instancia = $_POST['id_torneo_instancia'];
                $id_equipo_local = $_POST['id_equipo_local'];
                $id_equipo_visita = $_POST['id_equipo_visita'];
                $fecha = $_POST['fecha'];
                $hora = $_POST['hora'];
                $result = $this->torneo->registrar_partido($id_torneo_instancia,$id_equipo_local,$id_equipo_visita,$fecha,$hora);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }

        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_equipo_en_torneo() {
        try{
            $ok_data = true;
            if(isset($_POST['id_equipo']) && isset($_POST['id_torneo_grupo'])){
                $_POST['id_equipo'] = $this->clean->clean_post_int($_POST['id_equipo']);
                $_POST['id_torneo_grupo'] = $this->clean->clean_post_int($_POST['id_torneo_grupo']);

                $ok_data = $this->clean->validate_post_int($_POST['id_equipo'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_torneo_grupo'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $equipo_id = $_POST['id_equipo'];
                $id_torneo_grupo = $_POST['id_torneo_grupo'];
                $datos_exists=$this->torneo->listar_equipo_en_torneo($id_torneo_grupo,$equipo_id);
                if(isset($datos_exists->torneo_equipo_id)){
                    $result=3;
                }else{
                    $result = $this->torneo->registrar_equipo_en_torneo($id_torneo_grupo,$equipo_id);
                    if($result==1){
                        $estadisticas_1 = $this->torneo->listar_estadisticas_por_id_equipo($equipo_id);
                        $datos_torneo = $this->torneo->listar_torneo_por_equipo_grupo($id_torneo_grupo,$equipo_id);
                        $datos_equipo=$this->torneo->listar_equipo_por_id($equipo_id);
                        $valor_1 = $estadisticas_1->torneos;
                        $valor_1++;
                        $this->torneo->sumar_estadistica($equipo_id,"torneos",$valor_1);
                        $this->user->guardar_notificacion($datos_equipo->id_user,"Torneo",$datos_torneo->id_torneo,"Tu equipo fue agregado a un torneo",$datos_torneo->torneo_imagen);
                        $notificar = $this->notificar($datos_equipo->user_token,"Retaron a tu equipo ","Tu equipo fue agregado a un torneo","Torneo","Tu equipo fue agregado a un torneo");
                    }
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
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
                "capitan" => $model[$i]->user_nickname,
                "capitan_id" => $model[$i]->id_user
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function buscar_jugadores_por_id_partido(){
        $id_torneo_partido = $_POST['id'];
        $ind = $_POST['ind'];
        $i = $_POST['i'];
        $model = $this->torneo->listar_partido_por_id($id_torneo_partido);
        if($ind==0){
            $jugadores = $this->torneo->listar_detalle_equipo($model->id_equipo_local);
        }else{
            $jugadores = $this->torneo->listar_detalle_equipo($model->id_equipo_visita);
        }
        $lista="<table id='example4'><thead><th>Foto</th><th>Nombre</th><th>Elegir</th></thead><tbody>";
        foreach ($jugadores as $j){
            $lista.="<tr>
<td><img src='"._SERVER_. $j->user_image."' class='img-circle img-responsive img-thumbnail' style='max-width: 50px;'></td>
<td>".$j->user_nickname."</td>
<td><button class='btn btn-success' data-dismiss='modal' onclick=\"save_goleador(".$j->id_user.",'".$j->user_nickname."',".$i.",".$ind.")\" style='border-radius: 50%'>+</button></td></tr>";
        }
        $lista.="</tbody></table>";
        echo json_encode($lista);
    }
    public function listar_grupos_por_id_torneo() {
        $id_torneo = $_POST['id_torneo'];
        $model = $this->torneo->listar_grupos_por_id_torneo($id_torneo);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "id_torneo_grupo" => $model[$i]->id_torneo_grupo,
                "grupo_nombre" => $model[$i]->grupo_nombre
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_estadisticas(){
        try{
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
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_estadisticas_por_id_equipo(){
        try{
            $id_equipo = $_POST['id_equipo'];
            $model = $this->torneo->listar_estadisticas_por_id_equipo($id_equipo);
            $resources[] = array(
                "equipo_id" => $model->equipo_id,
                "nombre" => $model->equipo_nombre,
                "foto" => $model->equipo_foto,
                "temporada" => $model->temporada,
                "semana" => $model->semana,
                "puntaje_acumulado" => $model->puntaje_acumulado,
                "puntaje_semanal" => $model->puntaje_semanal,
                "retos_enviados" => $model->retos_enviados,
                "retos_recibidos" => $model->retos_recibidos,
                "retos_ganados" => $model->retos_ganados,
                "retos_empatados" => $model->retos_empatados,
                "retos_perdidos" => $model->retos_perdidos,
                "torneos" => $model->torneos
            );
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos(){
        try{
            $limite_sup = $_POST['limite_sup'];
            $limite_inf = $_POST['limite_inf'];
            if($limite_sup==0){
                $model = $this->torneo->listar_equipos_all();
                $ultima_noticia=$this->torneo->listar_ultimo_equipo_all();
                $limite_sup=$ultima_noticia->equipo_id;
                $new = "0";
            }else{
                $model = $this->torneo->listar_equipos_limite($limite_inf);
                $nuevos = $this->torneo->listar_equipos_limite_sup($limite_sup);
                (count($nuevos)>0)?$new = "1":$new="0";
            }
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "equipo_id" => $model[$i]->equipo_id,
                    "nombre" => $model[$i]->equipo_nombre,
                    "foto" => $model[$i]->equipo_foto,
                    "capitan" => $model[$i]->user_nickname,
                    "capitan_id" => $model[$i]->id_user
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos_por_torneo_not(){
        try{
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
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos_por_id_usuario_not(){
        try{
            $id_usuario = $_POST['id_usuario'];
            $model = $this->torneo->listar_equipos_por_id_usuario_not($id_usuario);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "equipo_id" => $model[$i]->equipo_id,
                    "nombre" => $model[$i]->equipo_nombre,
                    "foto" => $model[$i]->equipo_foto,
                    "capitan" => $model[$i]->user_nickname,
                    "capitan_id" => $model[$i]->id_user
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_detalle_equipo(){
        try{
            $id_equipo = $_POST['id_equipo'];
            $model = $this->torneo->listar_detalle_equipo($id_equipo);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "usuario_id" => $model[$i]->id_user,
                    "nombre" => $model[$i]->user_nickname,
                    "foto" => $model[$i]->user_image,
                    "posicion" => $model[$i]->user_posicion,
                    "habilidad" => $model[$i]->user_habilidad,
                    "num" => $model[$i]->user_num
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function buscar_jugadores_nuevos(){
        try{
            $id_equipo = $_POST['id_equipo'];
            $model = $this->torneo->buscar_jugadores_nuevos($id_equipo);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "usuario_id" => $model[$i]->id_user,
                    "nombre" => $model[$i]->user_nickname,
                    "foto" => $model[$i]->user_image,
                    "posicion" => $model[$i]->user_posicion,
                    "habilidad" => $model[$i]->user_habilidad,
                    "numero" => $model[$i]->user_num
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function buscar_jugadores_nickname(){
        try{
            $dato = $_POST['dato'];
            $id_equipo = $_POST['id_equipo'];
            $model = $this->torneo->buscar_jugadores_nickname($dato,$id_equipo);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "usuario_id" => $model[$i]->id_user,
                    "nombre" => $model[$i]->user_nickname,
                    "foto" => $model[$i]->user_image,
                    "posicion" => $model[$i]->user_posicion,
                    "habilidad" => $model[$i]->user_habilidad,
                    "numero" => $model[$i]->user_num
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function buscar_equipos_nombre(){
        try{
            $dato = $_POST['dato'];
            $id_torneo = $_POST['id_torneo'];
            $model = $this->torneo->buscar_equipos_nombre($id_torneo,$dato);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "equipo_id" => $model[$i]->equipo_id,
                    "nombre" => $model[$i]->equipo_nombre,
                    "foto" => $model[$i]->equipo_foto,
                    "capitan" => $model[$i]->user_nickname,
                    "capitan_id" => $model[$i]->id_user
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function buscar_equipos(){
        try{
            $dato = $_POST['dato'];
            $model = $this->torneo->buscar_equipos($dato);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "equipo_id" => $model[$i]->equipo_id,
                    "nombre" => $model[$i]->equipo_nombre,
                    "foto" => $model[$i]->equipo_foto,
                    "capitan" => $model[$i]->user_nickname,
                    "capitan_id" => $model[$i]->id_user
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_equipo_usuario() {
        try{
            $equipo_id = $_POST['id_equipo'];
            $usuario_id = $_POST['id_usuario'];
            $result = $this->torneo->registrar_equipo_usuario($equipo_id,$usuario_id);
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = "Code 2: General error";
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function eliminar_equipo_usuario() {
        try{
            $equipo_id = $_POST['id_equipo'];
            $usuario_id = $_POST['id_usuario'];
            $result = $this->torneo->eliminar_equipo_usuario($equipo_id,$usuario_id);
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = "Code 2: General error";
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function retar_equipo() {
        try{
            $ok_data = true;
            if(isset($_POST['retador']) && isset($_POST['retado'])&& isset($_POST['fecha'])&& isset($_POST['hora'])&& isset($_POST['lugar']) ){
                $_POST['retador'] = $this->clean->clean_post_int($_POST['retador']);
                $_POST['retado'] = $this->clean->clean_post_int($_POST['retado']);
                $_POST['fecha'] = $this->clean->clean_post_date($_POST['fecha']);
                $_POST['hora'] = $this->clean->clean_post_str($_POST['hora']);
                $_POST['lugar'] = $this->clean->clean_post_str($_POST['lugar']);

                $ok_data = $this->clean->validate_post_int($_POST['retador'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['retado'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_date($_POST['fecha'], true, $ok_data, 50,1);
                $ok_data = $this->clean->validate_post_str($_POST['hora'], true, $ok_data, 20);
                $ok_data = $this->clean->validate_post_str($_POST['lugar'], true, $ok_data, 100);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $retador_id = $_POST['retador'];
                $retado_id = $_POST['retado'];
                $fecha = $_POST['fecha'];
                $hora = $_POST['hora'];
                $lugar = $_POST['lugar'];
                $reto_exists = $this->torneo->existe_reto_pendiente($retador_id,$retado_id);
                if(!isset($reto_exists->reto_id)){
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
                        //chat
                        //ver si existe
                        $reto_exists2 = $this->torneo->existe_reto_pendiente($retador_id,$retado_id);
                        $datos_chat=$this->user->listar_chat_por_id_usuarios($datos2->id_user,$datos->id_user);
                        if(isset($datos_chat->chat_id) && $datos_chat->chat_id!=null){
                            $fechahora=date('Y-m-d H:i:s');
                            if($datos->user_token!=""){
                                $detalle_Chat = $this->user->listar_chat_por_id($datos_chat->chat_id);
                                $this->user->enviar_mensaje($detalle_Chat->chat_id,$datos->id_user,"Hola! He retado a tu equipo",$fechahora);
                                $this->user->guardar_notificacion($datos->id_user,"Reto",$reto_exists2->reto_id,"Tu equipo ".$datos->equipo_nombre." fue retado por el equipo ".$datos2->equipo_nombre,$datos2->equipo_foto);
                                $notificar = $this->notificar($datos->user_token,"Retaron a tu equipo ","Tu equipo ".$datos->equipo_nombre." fue retado por el equipo ".$datos2->equipo_nombre,"Reto","Retaron a tu equipo");
                            }
                        }else{
                            $microtime = microtime(true);
                            $fechahora=date('Y-m-d H:i:s');
                            $this->user->crear_chat($datos2->id_user,$datos->id_user,$fechahora,$microtime);
                            if($datos->user_token!=""){
                                $detalle_Chat = $this->user->listar_chat_por_microtime($microtime);
                                $this->user->enviar_mensaje($detalle_Chat->chat_id,$datos2->id_user,"Hola! He retado a tu equipo",$fechahora);
                                $this->user->guardar_notificacion($datos->id_user,"Reto",$reto_exists2->reto_id,"Tu equipo ".$datos->equipo_nombre." fue retado por el equipo ".$datos2->equipo_nombre,$datos2->equipo_foto);
                                $notificar = $this->notificar($datos->user_token,"Retaron a tu equipo ","Tu equipo ".$datos->equipo_nombre." fue retado por el equipo ".$datos2->equipo_nombre,"Reto","Retaron a tu equipo");
                            }
                        }
                        //fin chat
                    }
                }else{
                    $result = 3;
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function responder_reto() {
        try{
            $respuesta = $_POST['respuesta'];
            $id_reto = $_POST['id_reto'];
            $result = $this->torneo->responder_reto($respuesta,$id_reto);
            $resources = array();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
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
    public function dar_resultado_partido_torneo() {
        try{
            $ok_data = true;
            if(isset($_POST['id_torneo_partido']) && isset($_POST['marcador_local'])&& isset($_POST['marcador_visita'])){
                $_POST['id_torneo_partido'] = $this->clean->clean_post_int($_POST['id_torneo_partido']);
                $_POST['marcador_local'] = $this->clean->clean_post_int($_POST['marcador_local']);
                $_POST['marcador_visita'] = $this->clean->clean_post_int($_POST['marcador_visita']);

                $ok_data = $this->clean->validate_post_int($_POST['id_torneo_partido'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['marcador_local'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['marcador_visita'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id = $_POST['id_torneo_partido'];
                $marcador_local = $_POST['marcador_local'];
                $marcador_visita = $_POST['marcador_visita'];
                $result = $this->torneo->dar_resultado_partido_torneo($marcador_local,$marcador_visita,$id);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_goleador_partido_torneo() {
        try{
            $ok_data = true;
            if(isset($_POST['id_torneo_partido']) && isset($_POST['id_usuario'])&& isset($_POST['id_equipo'])){
                $_POST['id_torneo_partido'] = $this->clean->clean_post_int($_POST['id_torneo_partido']);
                $_POST['id_usuario'] = $this->clean->clean_post_int($_POST['id_usuario']);
                $_POST['id_equipo'] = $this->clean->clean_post_int($_POST['id_equipo']);

                $ok_data = $this->clean->validate_post_int($_POST['id_torneo_partido'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_usuario'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_equipo'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id = $_POST['id_torneo_partido'];
                $id_usuario = $_POST['id_usuario'];
                $id_equipo = $_POST['id_equipo'];
                $result = $this->torneo->registrar_goleador_partido_torneo($id,$id_usuario,$id_equipo);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
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
        define('GOOGLE_API_KEY', 'AAAAj5-Syog:APA91bFAMP0UmglvTddGLwEqtTJmfEtFTmVkSElOOEcmAI1rW-GaJ6uTfGuUvdzbwcMxyyLswqYUkM3ALdSvcUNM60rb9ryY-MIN2oLUHVIoT9SyKPE6uyo7omdwNQZjaVZtEDkYnxX7');
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
        define('GOOGLE_API_KEY', 'AAAAj5-Syog:APA91bFAMP0UmglvTddGLwEqtTJmfEtFTmVkSElOOEcmAI1rW-GaJ6uTfGuUvdzbwcMxyyLswqYUkM3ALdSvcUNM60rb9ryY-MIN2oLUHVIoT9SyKPE6uyo7omdwNQZjaVZtEDkYnxX7');
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
        $cont=0;
        for ($i=0;$i<count($model);$i++) {
            if($model[$i]->equipo_id_1!=null || $model[$i]->equipo_id_2!=null){
                $user_respuesta=0;
                if($model[$i]->equipo_id_1==null){
                    $equipo1 = $this->torneo->listar_equipo_por_id($model[$i]->retador_id);
                    $id_e1 = $equipo1->equipo_id;
                    $nombre_e1 = $equipo1->equipo_nombre;
                    $foto_e1 = $equipo1->equipo_foto;
                }else{
                    $id_e1 = $model[$i]->equipo_id_1;
                    $nombre_e1 = $model[$i]->nombre_1;
                    $foto_e1 = $model[$i]->foto_1;
                }if($model[$i]->equipo_id_2==null){
                    $equipo2 = $this->torneo->listar_equipo_por_id($model[$i]->retado_id);
                    $id_e2 = $equipo2->equipo_id;
                    $nombre_e2 = $equipo2->equipo_nombre;
                    $foto_e2 = $equipo2->equipo_foto;
                    $user_respuesta = $equipo2->usuario_id;
                }else{
                    $id_e2 = $model[$i]->equipo_id_2;
                    $nombre_e2 = $model[$i]->nombre_2;
                    $foto_e2 = $model[$i]->foto_2;
                }
                $date1 = new DateTime(date('Y-m-d'));
                $date2 = new DateTime($model[$i]->reto_fecha);
                $diff = $date1->diff($date2);
                ($diff->days<0)? $estado = 0:$estado = 1;
                $resources[$cont] = array(
                    "id_reto" => $model[$i]->reto_id,
                    "equipo_id_1" => $id_e1,
                    "equipo_id_2" => $id_e2,
                    "user_respuesta" => $user_respuesta,
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
                $cont++;
            }
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_reto_por_id() {
        try{
            $usuario_id = $_POST['id_reto'];
            $model = $this->torneo->listar_reto_por_id($usuario_id);
            if(isset($model->retador_id)){
                $equipo1 = $this->torneo->listar_equipo_por_id($model->retador_id);
                $id_e1 = $equipo1->equipo_id;
                $nombre_e1 = $equipo1->equipo_nombre;
                $foto_e1 = $equipo1->equipo_foto;
                $equipo2 = $this->torneo->listar_equipo_por_id($model->retado_id);
                $id_e2 = $equipo2->equipo_id;
                $nombre_e2 = $equipo2->equipo_nombre;
                $foto_e2 = $equipo2->equipo_foto;
                $user_respuesta = $equipo2->usuario_id;
                $date1 = new DateTime(date('Y-m-d'));
                $date2 = new DateTime($model->reto_fecha);
                $diff = $date1->diff($date2);
                ($diff->days<0)? $estado = 0:$estado = 1;
                $resources = array(
                "id_reto" => $model->reto_id,
                "equipo_id_1" => $id_e1,
                "equipo_id_2" => $id_e2,
                "user_respuesta" => $user_respuesta,
                "nombre_1" => $nombre_e1,
                "nombre_2" => $nombre_e2,
                "foto_1" => $foto_e1,
                "foto_2" => $foto_e2,
                "fecha" => $model->reto_fecha,
                "hora" => $model->reto_hora,
                "lugar" => $model->reto_lugar,
                "respuesta" => $model->reto_respuesta,
                "ganador_id" => $model->ganador_id,
                "ganador_estado" => $model->ganador_estado,
                "estado" => $estado
            );
        }
    } catch (Exception $e){
        $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
        $result = 2;
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
                    "foto" => $model[$i]->torneo_imagen,
                    "descripcion" => $model[$i]->torneo_descripcion,
                    "fecha" => $model[$i]->torneo_fecha,
                    "hora" => $model[$i]->torneo_hora,
                    "lugar" => $model[$i]->torneo_lugar,
                    "id_organizador" => $model[$i]->id_user,
                    "organizador" => $model[$i]->user_nickname,
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
                "foto" => $model[$i]->torneo_imagen,
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
    public function listar_torneo_por_id() {
        try{
            $model = $this->torneo->listar_torneo_por_id($_POST['id_torneo']);
            if(isset($model->torneo_id)){
                $equipos_por_torneo = $this->torneo->listar_equipos_por_torneo($model->torneo_id);
                $resources = array(
                    "id_torneo" => $model->torneo_id,
                    "nombre" => $model->torneo_nombre,
                    "foto" => $model->torneo_imagen,
                    "descripcion" => $model->torneo_descripcion,
                    "fecha" => $model->torneo_fecha,
                    "hora" => $model->torneo_hora,
                    "lugar" => $model->torneo_lugar,
                    "id_organizador" => $model->usuario_id,
                    "organizador" => $model->torneo_organizador,
                    "costo" => $model->torneo_costo,
                    "equipos" => count($equipos_por_torneo)
                );
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function buscar_torneos() {
        $model = $this->torneo->buscar_torneos($_POST['dato']);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $equipos_por_torneo = $this->torneo->listar_equipos_por_torneo($model[$i]->torneo_id);
            $resources[$i] = array(
                "id_torneo" => $model[$i]->torneo_id,
                "nombre" => $model[$i]->torneo_nombre,
                "foto" => $model[$i]->torneo_imagen,
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
                "id_torneo" => $id_torneo,
                "nombre" => $model[$i]->equipo_nombre,
                "foto" => $model[$i]->equipo_foto,
                "capitan" => $model[$i]->usuario_nombre
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_torneos_en_equipo()
    {
        $id_equipo = $_POST['id_equipo'];
        $model = $this->torneo->listar_torneos_por_equipo($id_equipo);
        $resources = array();
        for ($i = 0; $i < count($model); $i++) {
            $resources[$i] = array(
                "id_torneo" => $model[$i]->torneo_id,
                "equipo_id" => $id_equipo,
                "nombre" => $model[$i]->torneo_nombre,
                "foto" => $model[$i]->torneo_imagen,
                "lugar" => $model[$i]->torneo_lugar,
                "fecha" => $model[$i]->torneo_fecha,
                "organizador" => $model[$i]->torneo_organizador
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_publicaciones_por_id_torneo(){
        try {
            $id_usuario = $_POST['id_usuario'];
            $id_torneo = $_POST['id_torneo'];
            $limite_sup = $_POST['limite_sup'];
            $limite_inf = $_POST['limite_inf'];
            if ($limite_sup == 0) {
                $model = $this->torneo->listar_publicaciones($id_torneo);
                $ultima_noticia = $this->torneo->listar_ultima_publicacion($id_torneo);
                $limite_sup = $ultima_noticia->publicaciones_id;
                $new = "0";
            } else {
                $model = $this->torneo->listar_publicaciones_limite($id_torneo, $limite_inf);
                $nuevos = $this->torneo->listar_publicaciones_limite_sup($id_torneo, $limite_sup);
                (count($nuevos) > 0) ? $new = "1" : $new = "0";
            }
            $resources = array();
            for ($i = 0; $i < count($model); $i++) {
                $date1 = new DateTime($model[$i]->publicaciones_fecha);
                $date2 = new DateTime("now");
                $diff = $date1->diff($date2);
                if ($diff->y !== 0) {
                    $time = ($diff->y > 1) ? $diff->y . ' años ' : $diff->y . ' año ';
                } elseif ($diff->m !== 0) {
                    $time = ($diff->m > 1) ? $diff->m . ' meses ' : $diff->m . ' mes ';
                } elseif ($diff->d !== 0) {
                    $time = ($diff->d > 1) ? $diff->d . ' días ' : $diff->d . ' día ';
                } elseif ($diff->h !== 0) {
                    $time = ($diff->h > 1) ? $diff->h . ' horas ' : $diff->h . ' hora ';
                } elseif ($diff->i !== 0) {
                    $time = (($diff->days * 24) * 60) + ($diff->i) . ' minutos';
                } else {
                    $time = "0 min";
                }
                $likes = $this->foro->conteo_likes($model[$i]->publicaciones_id);
                $comentarios = $this->foro->conteo_comentarios($model[$i]->publicaciones_id);
                $dio_like = $this->foro->dio_like($model[$i]->publicaciones_id, $id_usuario);
                ($dio_like->id_like_pueblo == null) ? $dio_like_ = 0 : $dio_like_ = 1;
                if ($model[$i]->publicaciones_id_torneo != 0) {
                    $torneo_ = $this->torneo->listar_torneo_por_id($model[$i]->publicaciones_id_torneo);
                    $torneo = $torneo_->torneo_nombre;
                    $torneo_imagen = $torneo_->torneo_imagen;
                } else {
                    $torneo = " ";
                    $torneo_imagen = " ";
                }
                $resources[$i] = array(
                    "id_publicacion" => $model[$i]->publicaciones_id,
                    "id_usuario" => $model[$i]->id_user,
                    "usuario_nombre" => $model[$i]->usuario_nombre,
                    "usuario_foto" => $model[$i]->usuario_foto,
                    "titulo" => $model[$i]->publicaciones_titulo,
                    "descripcion" => $model[$i]->publicaciones_descripcion,
                    "concepto" => $model[$i]->publicaciones_concepto,
                    "id_torneo" => $model[$i]->publicaciones_id_torneo,
                    "torneo" => $torneo,
                    "torneo_imagen" => $torneo_imagen,
                    "foto" => $model[$i]->publicaciones_foto,
                    "fecha" => $time,
                    "tipo" => $model[$i]->publicaciones_tipo,
                    "cant_likes" => $likes->conteo,
                    "cant_comentarios" => $comentarios->conteo,
                    "dio_like" => $dio_like_
                );
                $limite_inf = $model[$i]->publicaciones_id;
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources,"limite_sup" => $limite_sup,"limite_inf" => $limite_inf,"nuevos"=>$new);
        echo json_encode($data);
    }
    public function listar_tabla_por_id_torneo(){
        try{
            $id_torneo = $_POST['id_torneo'];
            $grupos = $this->torneo->listar_grupos_por_id_torneo($id_torneo);
            $resources = [];
            for ($i=0;$i<count($grupos);$i++) {
                $model = [];
                $equipos = $this->torneo->listar_equipos_por_id_grupo($grupos[$i]->id_torneo_grupo);
                for($j=0;$j<count($equipos);$j++){
                    $partidos = $this->torneo->listar_partidos_terminados_equipo_fase1($equipos[$j]->equipo_id);
                    $part_j = count($partidos);
                    $part_g = 0;
                    $part_e = 0;
                    $part_p = 0;
                    $gf =0 ;
                    $gc = 0;
                    for ($k=0;$k<count($partidos);$k++){
                        if($equipos[$j]->equipo_id==$partidos[$k]->id_equipo_local){
                            if($partidos[$k]->marcador_local>$partidos[$k]->marcador_visita){
                                $part_g++;
                            }elseif ($partidos[$k]->marcador_local<$partidos[$k]->marcador_visita){
                                $part_p++;
                            }else{
                                $part_e++;
                            }
                            $gf = $gf+ $partidos[$k]->marcador_local;
                            $gc = $gc+ $partidos[$k]->marcador_visita;
                        }elseif ($equipos[$j]->equipo_id==$partidos[$k]->id_equipo_visita){
                            if($partidos[$k]->marcador_visita>$partidos[$k]->marcador_local){
                                $part_g++;
                            }elseif ($partidos[$k]->marcador_visita<$partidos[$k]->marcador_local){
                                $part_p++;
                            }else{
                                $part_e++;
                            }
                            $gf = $gf+ $partidos[$k]->marcador_visita;
                            $gc = $gc+ $partidos[$k]->marcador_local;
                        }
                    }
                    $total = (3 * $part_g) + $part_e;
                    $model[] = array(
                        "equipo_id"=>$equipos[$j]->equipo_id,
                        "equipo_nombre"=>$equipos[$j]->equipo_nombre,
                        "equipo_foto"=>$equipos[$j]->equipo_foto,
                        "part_j"=>$part_j,
                        "part_g"=>$part_g,
                        "part_e"=>$part_e,
                        "part_p"=>$part_p,
                        "gf"=>$gf,
                        "gc"=>$gc,
                        "total"=>$total
                    );
                }
                $resources[] = array(
                    "id_grupo"=>$grupos[$i]->id_torneo_grupo,
                    "nombre_grupo"=>$grupos[$i]->grupo_nombre,
                    "equipos" => $model
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_equipos_por_grupo(){
        try{
            $id_torneo = $_POST['id_torneo'];
            $grupos = $this->torneo->listar_grupos_por_id_torneo($id_torneo);
            $resources = [];
            for ($i=0;$i<count($grupos);$i++) {
                $model = [];
                $equipos = $this->torneo->listar_equipos_por_id_grupo($grupos[$i]->id_torneo_grupo);
                for($j=0;$j<count($equipos);$j++){
                    $model[] = array(
                        "equipo_id"=>$equipos[$j]->equipo_id,
                        "equipo_nombre"=>$equipos[$j]->equipo_nombre,
                        "equipo_foto"=>$equipos[$j]->equipo_foto
                    );
                }
                $resources[] = array(
                    "nombre_grupo"=>$grupos[$i]->grupo_nombre,
                    "equipos" => $model
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_instancias_partidos_por_id_torneo(){
        try{
            $id_torneo = $_POST['id_torneo'];
            $instancias = $this->torneo->listar_instancias_por_id_torneo($id_torneo);
            $resources = [];
            for ($i=0;$i<count($instancias);$i++) {
                $model = [];
                $partidos = $this->torneo->listar_partidos_por_id_instancia($instancias[$i]->id_torneo_instancia);
                for($j=0;$j<count($partidos);$j++){
                    $model[] = array(
                        "id_torneo_partido"=>$partidos[$j]->id_torneo_partido,
                        "id_equipo_local"=>$partidos[$j]->id_equipo_local,
                        "nombre_equipo_local"=>$partidos[$j]->nombre_local,
                        "foto_equipo_local"=>$partidos[$j]->foto_local,
                        "id_equipo_visita"=>$partidos[$j]->id_equipo_visita,
                        "nombre_equipo_visita"=>$partidos[$j]->nombre_visita,
                        "foto_equipo_visita"=>$partidos[$j]->foto_visita,
                        "partido_fecha"=>$partidos[$j]->torneo_partido_fecha,
                        "partido_hora"=>$partidos[$j]->torneo_partido_hora,
                        "partido_estado"=>$partidos[$j]->torneo_partido_estado
                    );
                }
                $resources[] = array(
                    "id_instancia"=>$instancias[$i]->id_torneo_instancia,
                    "nombre_instancia"=>$instancias[$i]->torneo_instancia_nombre,
                    "partidos" => $model
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }


    public function listar_goleadores_por_id_torneo(){
        try{
            $id_torneo = $_POST['id_torneo'];
            $model = $this->torneo->listar_goleadores_por_id_torneo($id_torneo);
            for($i=0;$i<count($model);$i++){
                $resources[$i] = array(
                    "user_image" => $model[$i]->user_image,
                    "user_nickname" => $model[$i]->user_nickname,
                    "equipo_foto" => $model[$i]->equipo_foto,
                    "equipo_nombre" => $model[$i]->equipo_nombre,
                    "conteo" => $model[$i]->conteo
                );
            }
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $resources = "Code 2: General error";
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function agregar_colaboracion() {
        try{
            $ok_data = true;
            if(isset($_POST['id_colab']) && isset($_POST['monto']) ){
                $_POST['id_colab'] = $this->clean->clean_post_int($_POST['id_colab']);
                $ok_data = $this->clean->validate_post_int($_POST['id_colab'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id_user = $this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_);
                $id_colab = $_POST['id_colab'];
                $monto = $_POST['monto'];
                $fecha = date('Y-m-d H:i:s');
                $result = $this->torneo->agregar_colaboracion($id_colab,$id_user,$monto,$fecha);
                if($result==1){
                    $modelando = new Cuenta();
                    $datos_cuenta=$this->user->listar_cuenta_por_id_user($id_user);
                    $modelando->receptor = $datos_cuenta->id_cuenta;
                    $modelando->monto = $monto * (-1);
                    $this->cuenta->sumar_saldo($modelando);
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }
    public function eliminar_colaboracion() {
        try{
            $id_colab = $_POST['id_detalle_colaboracion'];
            $id_user = $_POST['id_user'];
            $monto = $_POST['monto'];
            $result = $this->torneo->eliminar_detalle_colaboracion($id_colab);
            if($result==1){
                $modelando = new Cuenta();
                $datos_cuenta=$this->user->listar_cuenta_por_id_user($id_user);
                $modelando->receptor = $datos_cuenta->id_cuenta;
                $modelando->monto = $monto * (1);
                $this->cuenta->sumar_saldo($modelando);
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }
    public function agregar_chancha() {
        try{
            $ok_data = true;
            if(isset($_POST['nombre']) && isset($_POST['monto'])&& isset($_POST['id_equipo']) ){
                $_POST['id_equipo'] = $this->clean->clean_post_int($_POST['id_equipo']);
                $_POST['nombre'] = $this->clean->clean_post_str($_POST['nombre']);
                $ok_data = $this->clean->validate_post_int($_POST['id_equipo'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['nombre'], true, $ok_data, 100);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id_equipo = $_POST['id_equipo'];
                $nombre = $_POST['nombre'];
                $monto = $_POST['monto'];
                $fecha = date('Y-m-d H:i:s');
                $result = $this->torneo->agregar_chancha($id_equipo,$nombre,$monto,$fecha);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }
}