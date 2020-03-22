<?php
require_once 'app/models/Empresa.php';
require_once 'app/models/Usuario.php';
require_once 'app/models/Torneo.php';
require_once 'app/models/User.php';
require_once 'app/models/Cuenta.php';
require_once 'app/models/Transferencia.php';
class EmpresaController{
    private $empresa;
    private $usuario;
    private $user;
    private $cuenta;
    private $transferencia;
    private $torneo;
    private $nav;
    private $crypt;
    private $clean;
    private $log;
    private $validate;
    public function __construct(){
        $this->empresa = new Empresa();
        $this->usuario = new Usuario();
        $this->user = new User();
        $this->cuenta = new Cuenta();
        $this->torneo = new Torneo();
        $this->transferencia = new Transferencia();
        $this->crypt = new Crypt();
        $this->clean = new Clean();
        $this->log = new Log();
        $this->validate = new Validate();
    }
    public function index(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $empresas = $this->empresa->listar_empresas_por_id_ciudad('Iquitos');
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'empresa/index.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $empresa = $this->empresa->listar_detalle_empresa($id);
            $canchas = $this->empresa->listar_canchas_por_id_empresa($id);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'empresa/ver.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function reporte(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'empresa/reporte.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver_cancha(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $usuario_id = $this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_);
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $fecha_l = date('N');
            $fecha = date('Y-m-d');
            $manhana =date('Y-m-d',strtotime($fecha." + 1 days"));
            $manhana_l =date('N',strtotime($fecha." + 1 days"));
            $pasado = date('Y-m-d',strtotime($fecha." + 2 days"));
            $pasado_l = date('N',strtotime($fecha." + 2 days"));
            $resumen_hoy = $this->empresa->listar_reservados_por_cancha_por_fecha($id,$fecha);
            $resumen_m = $this->empresa->listar_reservados_por_cancha_por_fecha($id,$manhana);
            $resumen_p = $this->empresa->listar_reservados_por_cancha_por_fecha($id,$pasado);
            $cancha = $this->empresa->listar_cancha_por_id($id);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'empresa/ver_cancha.php';
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function agregar_empresa(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $usuarios = $this->user->readAll();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'empresa/agregar_empresa.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function agregar_cancha(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $id = $_GET['id'] ?? 0;
            if($id == 0){
                throw new Exception('ID Sin Declarar');
            }
            $empresa = $this->empresa->listar_detalle_empresa($id);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'empresa/agregar_cancha.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function reservar_cancha(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $id_usuario = $this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_);
            $mis_equipos = $this->torneo->listar_equipos_por_id_usuario($id_usuario);
            $comision = "3.00";
            if(isset($_POST["fecha"]) && isset($_POST['hora'])){
                $fecha = $_POST["fecha"];
                $hora = $_POST["hora"];
                $ab_hora_=explode(':',$hora);
                $ab_hora = $ab_hora_[0];
                if(isset($_POST['empresa'])){
                    $empresa = $_POST['empresa'];
                    $datos_empresa = $this->empresa->listar_detalle_empresa($empresa);
                    $cancha="";
                    $canchas = $this->empresa->listar_canchas_por_id_empresa($empresa);
                    $precioCancha = "";
                    $class = "display:none;";
                }else if(isset($_POST['cancha'])){
                    $cancha = $_POST['cancha'];
                    $datos_cancha = $this->empresa->listar_cancha_por_id($cancha);
                    $empresa=$datos_cancha->empresa_id;
                    $class="";
                    ($ab_hora>=18)?$precioCancha = $datos_cancha->cancha_precioN:$precioCancha = $datos_cancha->cancha_precioD;
                    $datos_empresa = $this->empresa->listar_detalle_empresa($empresa);
                    $canchas = $this->empresa->listar_canchas_por_id_empresa($empresa);
                }else{
                    echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
                    echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
                }
                require _VIEW_PATH_ . 'header.php';
                require _VIEW_PATH_ . 'navbar.php';
                require _VIEW_PATH_ . 'empresa/reservar_cancha.php';
            }else{
                echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
                echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
            }
        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function registrar() {
        try{
            $ok_data = true;
            if(isset($_POST['empresa_name']) && isset($_POST['usuario_id']) &&isset($_POST['ubigeo_id']) &&isset($_POST['direccion']) &&isset($_POST['coord_x']) &&isset($_POST['coord_y']) &&isset($_POST['telefono_1']) &&isset($_POST['telefono_2'])&&isset($_POST['horario_1'])&&isset($_POST['horario_2'])&&isset($_POST['descripcion'])&&isset($_FILES['imagen']['tmp_name']) ){
                $_POST['empresa_name'] = $this->clean->clean_post_str($_POST['empresa_name']);
                $_POST['direccion'] = $this->clean->clean_post_str($_POST['direccion']);
                $_POST['telefono_1'] = $this->clean->clean_post_str($_POST['telefono_1']);
                $_POST['telefono_2'] = $this->clean->clean_post_str($_POST['telefono_2']);
                $_POST['horario_1'] = $this->clean->clean_post_str($_POST['horario_1']);
                $_POST['horario_2'] = $this->clean->clean_post_str($_POST['horario_2']);
                $_POST['descripcion'] = $this->clean->clean_post_str($_POST['descripcion']);
                $_POST['usuario_id'] = $this->clean->clean_post_int($_POST['usuario_id']);
                $_POST['ubigeo_id'] = $this->clean->clean_post_int($_POST['ubigeo_id']);

                $ok_data = $this->clean->validate_post_str($_POST['empresa_name'], true, $ok_data, 75);
                $ok_data = $this->clean->validate_post_str($_POST['direccion'], true, $ok_data, 100);
                $ok_data = $this->clean->validate_post_str($_POST['telefono_1'], true, $ok_data, 30);
                $ok_data = $this->clean->validate_post_str($_POST['telefono_2'], true, $ok_data, 30);
                $ok_data = $this->clean->validate_post_str($_POST['horario_1'], true, $ok_data, 50);
                $ok_data = $this->clean->validate_post_str($_POST['horario_2'], true, $ok_data, 50);
                $ok_data = $this->clean->validate_post_str($_POST['descripcion'], true, $ok_data, 250);
                $ok_data = $this->clean->validate_post_int($_POST['usuario_id'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['ubigeo_id'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $nombre = $_POST['empresa_name'];
                $usuario_id = $_POST['usuario_id'];
                $ubigeo_id = $_POST['ubigeo_id'];
                $direccion = $_POST['direccion'];
                $coord_x = $_POST['coord_x'];
                $coord_y = $_POST['coord_y'];
                $telefono_1 = $_POST['telefono_1'];
                $telefono_2 = $_POST['telefono_2'];
                $horario_1 = $_POST['horario_1'];
                $horario_2 = $_POST['horario_2'];
                $descripcion = $_POST['descripcion'];
                $file_path = "media/company/".$usuario_id."_".$nombre.".jpg";
                move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path);
                $result = $this->empresa->registrar($usuario_id,$ubigeo_id,$nombre,$direccion,$coord_x,$coord_y,$telefono_1,$telefono_2,$descripcion,$horario_1,$horario_2,$file_path);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }
    public function pagar_restante() {
        try{
            $ok_data = true;
            if(isset($_POST['pago2']) && isset($_POST['id']) ){
                $_POST['id'] = $this->clean->clean_post_int($_POST['id']);
                $ok_data = $this->clean->validate_post_int($_POST['id'], true, $ok_data, 11);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $pago2 = $_POST['pago2'];
                $id = $_POST['id'];
                $fecha = date('Y-m-d H:i:s');
                $result = $this->empresa->pagar_restante($pago2,$fecha,$id);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }

    public function listar_cancha_por_id() {
        try{
            $id = $_POST["id"];
            $result = $this->empresa->listar_cancha_por_id($id);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }
    public function obtener_saldo_actual() {
        try{
            if(isset($_SESSION['id_user'])){
                $id = $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_);
            }else{
                $id = $_POST['id'];
            }
            $model = $this->empresa->obtener_saldo_actual($id);
            $saldo = $model->cuenta_saldo;
            if($saldo==null){$saldo=0;}
            $comision = "3.00";
            $resources[] = array(
                "cuenta_saldo" => $saldo,
                "comision" => $comision
            );
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function obtener_chanchas_disponibles() {
        try{
            if(isset($_SESSION['id_user'])){
                $id = $this->crypt->decrypt($_SESSION['id_user'], _FULL_KEY_);
            }else{
                $id = $_POST['id'];
            }
            $model = $this->empresa->obtener_chanchas_disponibles($id);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $detalle=$this->empresa->obtener_detalle_chancha($model[$i]->colaboracion_id);
                $resources[$i] = array(
                    "id" => $model[$i]->colaboracion_id,
                    "id_equipo" => $model[$i]->equipo_id,
                    "equipo" => $model[$i]->equipo_nombre,
                    "nombre" => $model[$i]->colaboracion_nombre,
                    "monto" => $model[$i]->colaboracion_monto,
                    "fecha" => $model[$i]->colaboracion_date,
                    "detalle" => $detalle
                );
            }
            $data = array("results" => $resources);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $data = 2;
        }
        echo json_encode($data);
    }
    public function obtener_chanchas_disponibles_por_equipo() {
        try{
            $id = $_POST['id_equipo'];
            $model = $this->empresa->obtener_chanchas_disponibles_por_equipo($id);
            $resources = array();
            for ($i=0;$i<count($model);$i++) {
                $detalle=$this->empresa->obtener_detalle_chancha($model[$i]->colaboracion_id);
                $resources[$i] = array(
                    "id" => $model[$i]->colaboracion_id,
                    "id_equipo" => $model[$i]->equipo_id,
                    "equipo" => $model[$i]->equipo_nombre,
                    "nombre" => $model[$i]->colaboracion_nombre,
                    "monto" => $model[$i]->colaboracion_monto,
                    "fecha" => $model[$i]->colaboracion_date,
                    "detalle" => $detalle
                );
            }
            $data = array("results" => $resources);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $data = 2;
        }
        echo json_encode($data);
    }
    public function listar_empresas_por_id_ciudad(){
        $id_ciudad = $_POST['id_ciudad'];
        $id_usuario = $_POST['id_usuario'];
        $ciudad = $this->usuario->listar_ciudad_por_id_ubigeo($id_ciudad);
        $model2 = $this->empresa->listar_empresas_por_id_ciudad($ciudad->ciudad);
        $resources = array();
        for ($i=0;$i<count($model2);$i++) {
            $id_empresa=$model2[$i]->empresa_id;
            $model = $this->empresa->listar_detalle_empresa($id_empresa);
            $listado = $this->empresa->listar_rating_empresa($id_empresa);
            $mi_rating = $this->empresa->listar_detalle_rating_empresa($id_empresa,$id_usuario);
            $mi_rating_final=null;
            if(isset($mi_rating->rating_empresa_valor)){
                $mi_rating_final = $mi_rating->rating_empresa_valor;
            }
            if($mi_rating_final==null){
                $mi_rating_final= 0;
            }else{
                $mi_rating_final= $mi_rating_final * 1;
            }
            $soy_admin=null;
            $soy_admin_ = $this->empresa->listar_mi_negocio_reserva($id_usuario,$id_empresa);
            if(isset($soy_admin_->id_empresa_usuario)){
                $soy_admin = $soy_admin_->id_empresa_usuario;
            }
            if($soy_admin==null){
                $soy_admin= 0;
            }else{
                $soy_admin= 1;
            }
            $detalles = $this->empresa->listar_rating_empresa_agrupado($id_empresa);
            $conteo = $listado->conteo;
            if($conteo=="0"){$conteo=0;}
            $suma = $listado->suma;
            if($suma==null){$suma=0;}
            ($suma==0 && $conteo==0) ? $prom = 0 : $prom = $suma / $conteo;
            $promedio = round($prom,2);
            $resources[] = array(
                "nombre"=>$model->empresa_nombre,
                "id_empresa"=>$id_empresa,
                "direccion"=>$model->empresa_direccion,
                "telefono_1"=>$model->empresa_telefono_1,
                "telefono_2"=>$model->empresa_telefono_2,
                "descripcion"=>$model->empresa_descripcion,
                "valoracion"=>$mi_rating_final,
                "foto"=>$model->empresa_foto,
                "estado"=>$model->empresa_estado,
                "usuario"=>$model->user_nickname,
                "distrito"=>$model->ubigeo_distrito,
                "horario_ls" => $model->empresa_horario_ls,
                "horario_d" => $model->empresa_horario_d,
                "fecha_actual" => date('Y-m-d'),
                "hora_actual" => date('H:i'),
                "dia" => date('N'),
                "promedio" => $promedio,
                "conteo" => $conteo,
                "rating" => $detalles,
                "soy_admin" => $soy_admin
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_detalle_empresa(){
        date_default_timezone_set('America/Lima');
        $id_empresa = $_POST['id_empresa'];
        $id_usuario = $_POST['id_usuario'];
        $model = $this->empresa->listar_detalle_empresa($id_empresa);
        $listado = $this->empresa->listar_rating_empresa($id_empresa);
        $mi_rating = $this->empresa->listar_detalle_rating_empresa($id_empresa,$id_usuario);
        $mi_rating_final = $mi_rating->rating_empresa_valor;
        if($mi_rating_final==null){
            $mi_rating_final= 0;
        }else{
            $mi_rating_final= $mi_rating_final * 1;
        }
        $detalles = $this->empresa->listar_rating_empresa_agrupado($id_empresa);
        $conteo = $listado->conteo;
        if($conteo=="0"){$conteo=0;}
        $suma = $listado->suma;
        if($suma==null){$suma=0;}
        ($suma==0 && $conteo==0) ? $prom = 0 : $prom = $suma / $conteo;
        $promedio = round($prom,2);
        $resources = array();
        $resources[0] = array(
            "nombre"=>$model->empresa_nombre,
            "direccion"=>$model->empresa_direccion,
            "telefono_1"=>$model->empresa_telefono_1,
            "telefono_2"=>$model->empresa_telefono_2,
            "descripcion"=>$model->empresa_descripcion,
            "valoracion"=>$mi_rating_final,
            "foto"=>$model->empresa_foto,
            "estado"=>$model->empresa_estado,
            "usuario"=>$model->user_nickname,
            "distrito"=>$model->ubigeo_distrito,
            "horario_ls" => $model->empresa_horario_ls,
            "horario_d" => $model->empresa_horario_d,
            "fecha_actual" => date('Y-m-d'),
            "hora_actual" => date('H:i'),
            "dia" => date('N'),
            "promedio" => $promedio,
            "conteo" => $conteo,
            "rating" => $detalles
        );
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_cancha() {
        try{
            $ok_data = true;
            if(isset($_POST['empresa_id']) && isset($_POST['nombre']) &&isset($_POST['dimensiones']) &&isset($_POST['precioD']) &&isset($_POST['precioN']) &&isset($_FILES['imagen']['tmp_name']) ){
                $_POST['empresa_id'] = $this->clean->clean_post_int($_POST['empresa_id']);
                $_POST['nombre'] = $this->clean->clean_post_str($_POST['nombre']);
                $_POST['dimensiones'] = $this->clean->clean_post_str($_POST['dimensiones']);

                $ok_data = $this->clean->validate_post_int($_POST['empresa_id'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['nombre'], true, $ok_data, 100);
                $ok_data = $this->clean->validate_post_str($_POST['dimensiones'], true, $ok_data, 100);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $empresa_id = $_POST['empresa_id'];
                $nombre = $_POST['nombre'];
                $dimensiones = $_POST['dimensiones'];
                $precioD = $_POST['precioD'];
                $precioN = $_POST['precioN'];
                $file_path = "media/company/cancha/".$empresa_id."_".$nombre.".jpg";
                move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path);
                $result = $this->empresa->registrar_cancha($empresa_id,$nombre,$dimensiones,$precioD,$precioN,$file_path);
            }else{
                $result = 6;
            }
        } catch (Exception $e) {
            $this->log->insert($e->getMessage(), get_class($this) . '|' . __FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
    }
    public function listar_reservados_por_cancha_por_fecha(){
        $id_cancha = $_POST['id_cancha'];
        $fecha = $_POST['fecha'];
        $model = $this->empresa->listar_reservados_por_cancha_por_fecha($id_cancha,$fecha);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "reserva_id" => $model[$i]->reserva_id,
                "pago_id" => $model[$i]->pago_id,
                "tipopago" => $model[$i]->reserva_tipopago,
                "cancha_id" => $model[$i]->cancha_id,
                "nombre" => $model[$i]->reserva_nombre,
                "fecha" => $model[$i]->reserva_fecha,
                "hora" => $model[$i]->reserva_hora,
                "pago1" => $model[$i]->reserva_pago1,
                "pago1_date" => $model[$i]->reserva_pago1_date,
                "pago2" => $model[$i]->reserva_pago2,
                "pago2_date" => $model[$i]->reserva_pago2_date,
                "dia" => date('N'),
                "estado" => $model[$i]->reserva_estado
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_reservas_por_usuario(){
        try{
            $id_usuario = $_POST['id_usuario'];
            $model = $this->empresa->listar_reservas_por_usuario($id_usuario);
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $model = 2;
        }
        $data = array("results" => $model);
        echo json_encode($data);
    }
    public function listar_reserva_por_id(){
        try{
            $id_reserva = $_POST['id_reserva'];
            $model = $this->empresa->listar_reserva_por_id($id_reserva);
            $nombre_user = $this->user->list($model->id_user);
            $model->nombre_user = $nombre_user->person_name." ".$nombre_user->person_surname;
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $model = 2;
        }
        $data = array("results" => $model);
        echo json_encode($data);
    }
    public function listar_canchas_por_id_empresa(){
        $id_empresa = $_POST['id_empresa'];
        $model = $this->empresa->listar_canchas_por_id_empresa($id_empresa);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "id_empresa" => $id_empresa,
                "cancha_id" => $model[$i]->cancha_id,
                "nombre" => $model[$i]->cancha_nombre,
                "dimensiones" => $model[$i]->cancha_dimensiones,
                "precioD" => $model[$i]->cancha_precioD,
                "precioN" => $model[$i]->cancha_precioN,
                "foto" => $model[$i]->cancha_foto
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function registrar_reserva() {
        try{
            $ok_data = true;
            if(isset($_POST['id_cancha']) && isset($_POST['nombre']) &&isset($_POST['fecha']) &&isset($_POST['hora']) &&isset($_POST['pago1']) ){
                $_POST['id_cancha'] = $this->clean->clean_post_int($_POST['id_cancha']);
                $_POST['nombre'] = $this->clean->clean_post_str($_POST['nombre']);
                $_POST['fecha'] = $this->clean->clean_post_date($_POST['fecha']);
                $_POST['hora'] = $this->clean->clean_post_str($_POST['hora']);

                $ok_data = $this->clean->validate_post_int($_POST['id_cancha'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['nombre'], true, $ok_data, 100);
                $ok_data = $this->clean->validate_post_date($_POST['fecha'], true, $ok_data, 100,1);
                $ok_data = $this->clean->validate_post_str($_POST['hora'], true, $ok_data, 20);
            }else{
                $ok_data=false;
            }
            if($ok_data){
                $id_cancha = $_POST['id_cancha'];
                $nombre = $_POST['nombre'];
                $tipopago = $_POST['tipopago'];
                $pago_date = date('Y-m-d H:i:s');
                $exists_reserva = $this->empresa->existe_reserva($_POST['id_cancha'],$_POST['fecha'],$_POST['hora']);
                if(isset($exists_reserva->reserva_id) && $exists_reserva->reserva_id!=""){
                    $result=3;
                }else{
                    if($tipopago==0){
                        $pago_id=0;
                    }
                    else{
                        $pago_id_user = $_POST['pago_id_user'];
                        $pago_equipo_id = $_POST['pago_equipo_id'];
                        $pago_tipo = $_POST['pago_tipo'];
                        $id_colaboracion = $_POST['id_colaboracion'];
                        $pago_monto = $_POST['pago1'];
                        $pago_comision = $_POST['pago_comision'];
                        $pago_total = $pago_monto * 1 + $pago_comision * 1;
                        $pago_microtime = microtime(true);
                        $result = $this->empresa->registrar_pago($pago_id_user,$pago_equipo_id,$id_colaboracion,$pago_tipo,$pago_monto,$pago_comision,$pago_total,$pago_date,$pago_microtime);
                        if($result==1){
                            $pago_detalle = $this->empresa->listar_pago_por_mt($pago_microtime);
                            $pago_id=$pago_detalle->pago_id;
                            if($pago_tipo==1){
                                //actualizar saldo
                                $modelando = new Cuenta();
                                $datos_cuenta=$this->user->listar_cuenta_por_id_user($pago_id_user);
                                $modelando->receptor = $datos_cuenta->id_cuenta;
                                $modelando->monto = $pago_total * (-1);
                                $this->cuenta->sumar_saldo($modelando);
                                //fin actualizar saldo
                                //guardar transferencia
                                $modelando_T = new Transferencia();
                                $datos_empresa = $this->empresa->listar_cancha_por_id($id_cancha);
                                $modelando_T->nro_operacion = $datos_empresa->empresa_id."-1111111";
                                $modelando_T->id_user = $pago_id_user;
                                $modelando_T->id_empresa = $datos_empresa->empresa_id;
                                $modelando_T->id_pago = $pago_id;
                                $modelando_T->monto = $pago_monto;
                                $modelando_T->concepto = "Reserva Cancha ".$datos_empresa->empresa_nombre;
                                $this->transferencia->save_transferencia_u_e($modelando_T);
                                //fin guardar transferencia
                            }else{
                                $this->empresa->actualizar_colaboracion_estado_0($id_colaboracion);
                                $detalle_colaboracion = $this->empresa->obtener_detalle_chancha($id_colaboracion);
                                foreach ($detalle_colaboracion as $dc){
                                    //actualizar saldo
                                    $modelando = new Cuenta();
                                    $datos_cuenta=$this->user->listar_cuenta_por_id_user($dc->id_user);
                                    $modelando->receptor = $datos_cuenta->id_cuenta;
                                    $modelando->monto = $dc->detalle_colaboracion_monto * (-1);
                                    $this->cuenta->sumar_saldo($modelando);
                                    //fin actualizar saldo
                                }
                                //guardar transferencia
                                $modelando_T = new Transferencia();
                                $datos_empresa = $this->empresa->listar_cancha_por_id($id_cancha);
                                $modelando_T->nro_operacion = $datos_empresa->empresa_id."-1111111";
                                $modelando_T->id_user = $pago_id_user;
                                $modelando_T->id_empresa = $datos_empresa->empresa_id;
                                $modelando_T->id_pago = $pago_id;
                                $modelando_T->monto = $pago_monto;
                                $modelando_T->concepto = "Reserva Cancha ".$datos_empresa->empresa_nombre;
                                $this->transferencia->save_transferencia_u_e($modelando_T);
                                //fin guardar transferencia
                                $this->empresa->actualizar_detalle_colaboracion_estado_0($id_colaboracion);
                            }
                        }else{
                            throw new Exception('Ocurrió un error al registrar pago.');
                        }
                    }
                    $fecha = $_POST['fecha'];
                    $hora = $_POST['hora'];
                    $pago1 = $_POST['pago1'];
                    $id_user = $_POST['id_user'];
                    $pago1_date = $pago_date;
                    $pago2 = "0";
                    $pago2_date = "";
                    $estado = $_POST['estado'];
                    $microtime = microtime(true);
                    $result = $this->empresa->registrar_reserva($id_cancha,$id_user,$tipopago,$pago_id,$nombre,$fecha,$hora,$pago1,$pago1_date,$pago2,$pago2_date,$estado,$microtime);
                    if($result==1){
                        $datos_reserva = $this->empresa->listar_reserva_por_microtime($microtime);
                        $datos_cancha = $this->empresa->listar_usuarios_por_id_cancha($id_cancha);
                        foreach ($datos_cancha as $dd){
                            $this->user->guardar_notificacion($dd->id_user,"Reserva",$datos_reserva->id_reserva,"Reserva de ".$dd->cancha_nombre." el ".$fecha." a las " . $hora);
                            $notificar = $this->notificar($dd->user_token,"Reserva de ".$dd->cancha_nombre." el ".$fecha." a las " . $hora,"Reserva lista!","Reserva","Reserva de ".$dd->cancha_nombre." el ".$fecha." a las " . $hora);
                        }
                    }else{
                        if($tipopago!=0){
                            $this->empresa->delete_pago_por_microtime($pago_microtime);
                            if($pago_tipo==1){
                                $modelando = new Cuenta();
                                $datos_cuenta=$this->user->listar_cuenta_por_id_user($pago_id_user);
                                $modelando->receptor = $datos_cuenta->id_cuenta;
                                $modelando->monto = $pago_total * (1);
                                $this->cuenta->sumar_saldo($modelando);
                                //fin actualizar saldo
                                //guardar transferencia
                                $this->transferencia->delete_transferencia_u_e_por_pago_id($pago_id);
                                //fin guardar transferencia
                            }else{
                                $this->empresa->actualizar_colaboracion_estado_1($id_colaboracion);
                                $this->empresa->actualizar_detalle_colaboracion_estado_0($id_colaboracion);
                                $detalle_colaboracion = $this->empresa->obtener_detalle_chancha($id_colaboracion);
                                foreach ($detalle_colaboracion as $dc){
                                    //actualizar saldo
                                    $modelando = new Cuenta();
                                    $datos_cuenta=$this->user->listar_cuenta_por_id_user($dc->id_user);
                                    $modelando->receptor = $datos_cuenta->id_cuenta;
                                    $modelando->monto = $dc->detalle_colaboracion_monto * (1);
                                    $this->cuenta->sumar_saldo($modelando);
                                    //fin actualizar saldo
                                }
                                //guardar transferencia
                                $this->transferencia->delete_transferencia_u_e_por_pago_id($pago_id);
                                //fin guardar transferencia

                            }
                        }

                    }
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
    public function listar_canchas_libres_por_hora(){
        /*$fecha = $_POST['fecha'];
        $hora_i = $_POST['hora_i'];
        $hora_f = $_POST['hora_f'];
        */
        $fecha = date("Y-m-d");
        $hora = date("H");
        $resources = [];
        for ($i=$hora;$i<25;$i++) {
            $model = $this->empresa->listar_canchas_libres_por_hora($fecha,$i);
            $resources[] = array(
                $i => $model
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function busqueda_avanzada(){
        $fecha = $_POST['fecha'];
        $hora = $_POST['hora'];
        $negocio = $_POST['negocio'];
        $resources = [];
        if($negocio!="Todos" && $hora!="Todos"){
            $resources = $this->empresa->busqueda_avanzada_tres_params($fecha,$hora,$negocio);
        }elseif ($negocio=="Todos" && $hora!="Todos"){
            $resources = $this->empresa->listar_canchas_libres_por_hora($fecha,$hora);
        }elseif ($negocio!="Todos" && $hora=="Todos"){
            for ($i=10;$i<25;$i++) {
                $model = $this->empresa->busqueda_avanzada_tres_params($fecha,$i,$negocio);
                $resources[] = array(
                    $i => $model
                );
            }
        }elseif ($negocio=="Todos" && $hora=="Todos"){
            for ($i=10;$i<25;$i++) {
                $model = $this->empresa->listar_canchas_libres_por_hora($fecha,$i);
                $resources[] = array(
                    $i => $model
                );
            }
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function estadisticas_por_empresa(){
        $fecha_i = $_POST['fecha_i'];
        $fecha_f = $_POST['fecha_f'];
        $id_empresa = $_POST['id_empresa'];

        /*$fecha_i = '2020-01-22';
        $fecha_f = '2020-02-04';
        $id_empresa = 4;*/
        $resources = [];
        $fecha_inicio = strtotime($fecha_i);
        $fecha_final = strtotime($fecha_f);
        for ($j=$fecha_inicio;$j<=$fecha_final;$j+=86400) {
            $fecha_d = date('Y-m-d',$j);
            $model = $this->empresa->estadisticas_por_empresa_fecha($fecha_d,$id_empresa);
            $resources[] = array(
                $fecha_d => $model
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function valorar_empresa() {
        try{
            $usuario_id = $_POST['id_usuario'];
            $id_empresa = $_POST['id_empresa'];
            $valor = $_POST['valor'];
            $comment = $_POST['comentario'];
            $mi_rating = $this->empresa->listar_detalle_rating_empresa($id_empresa,$usuario_id);
            if($mi_rating->rating_empresa_valor!=null){
                $result = $this->empresa->actualizar_valorar_empresa($usuario_id,$id_empresa,$valor,$comment);
            }else{
                $result = $this->empresa->valorar_empresa($usuario_id,$id_empresa,$valor,$comment);
            }
        }catch (Exception $e){
                $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
                $result = 2;
        }
        echo json_encode($result);
    }
    public function listar_valoraciones() {
        try{
            $id_empresa = $_POST['id_empresa'];
            $result = $this->empresa->listar_valoraciones($id_empresa);
        }catch (Exception $e){
                $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
                $result = 2;
        }
        echo json_encode($result);
    }
    public function listar_mis_negocios_reserva() {
        try{
            $usuario_id = $_POST['id_usuario'];
            $model = $this->empresa->listar_mis_negocios_reserva($usuario_id);
            for ($i=0;$i<count($model);$i++) {
                $resources[$i] = array(
                    "id_empresa" => $model[$i]->empresa_id,
                    "nombre" => $model[$i]->empresa_nombre,
                    "direccion" => $model[$i]->empresa_direccion,
                    "foto" => $model[$i]->empresa_foto,
                    "horario_ls" => $model[$i]->empresa_horario_ls,
                    "horario_d" => $model[$i]->empresa_horario_d
                );
            }
            $data = array("results" => $resources);
        }catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $data = 2;
        }
        echo json_encode($data);
    }
}