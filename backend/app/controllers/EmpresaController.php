<?php
require_once 'app/models/Empresa.php';
require_once 'app/models/Usuario.php';
require_once 'app/models/User.php';
class EmpresaController{
    private $empresa;
    private $usuario;
    private $user;
    private $nav;
    private $crypt;
    private $clean;
    private $log;
    private $validate;
    public function __construct(){
        $this->empresa = new Empresa();
        $this->usuario = new Usuario();
        $this->user = new User();
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
    public function ver_cancha(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
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
    public function listar_empresas_por_id_ciudad(){
        $id_ciudad = $_POST['id_ciudad'];
        $ciudad = $this->usuario->listar_ciudad_por_id_ubigeo($id_ciudad);
        $model = $this->empresa->listar_empresas_por_id_ciudad($ciudad->ciudad);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "id_empresa" => $model[$i]->empresa_id,
                "nombre" => $model[$i]->empresa_nombre,
                "direccion" => $model[$i]->empresa_direccion,
                "foto" => $model[$i]->empresa_foto,
                "horario" => $model[$i]->empresa_horario
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
            "telefono"=>$model->empresa_telefono,
            "descripcion"=>$model->empresa_descripcion,
            "valoracion"=>$mi_rating_final,
            "foto"=>$model->empresa_foto,
            "estado"=>$model->empresa_estado,
            "usuario"=>$model->usuario_nombre,
            "distrito"=>$model->ubigeo_distrito,
            "horario" => $model->empresa_horario,
            "fecha_actual" => date('Y-m-d'),
            "hora_actual" => date('H:i'),
            "promedio" => $promedio,
            "conteo" => $conteo
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
                "cancha_id" => $model[$i]->cancha_id,
                "nombre" => $model[$i]->reserva_nombre,
                "fecha" => $model[$i]->reserva_fecha,
                "hora" => $model[$i]->reserva_hora,
                "costo" => $model[$i]->reserva_costo,
                "estado" => $model[$i]->reserva_estado
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_canchas_por_id_empresa(){
        $id_empresa = $_POST['id_empresa'];
        $model = $this->empresa->listar_canchas_por_id_empresa($id_empresa);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
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
            if(isset($_POST['id_cancha']) && isset($_POST['nombre']) &&isset($_POST['fecha']) &&isset($_POST['hora']) &&isset($_POST['costo']) ){
                $_POST['id_cancha'] = $this->clean->clean_post_int($_POST['id_cancha']);
                $_POST['nombre'] = $this->clean->clean_post_str($_POST['nombre']);
                $_POST['fecha'] = $this->clean->clean_post_date($_POST['fecha']);
                $_POST['hora'] = $this->clean->clean_post_str($_POST['hora']);

                $ok_data = $this->clean->validate_post_int($_POST['id_cancha'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['nombre'], true, $ok_data, 100);
                $ok_data = $this->clean->validate_post_date($_POST['fecha'], true, $ok_data, 100,1);
                $ok_data = $this->clean->validate_post_str($_POST['hora'], true, $ok_data, 20);
            }
            if($ok_data){
                $id_cancha = $_POST['id_cancha'];
                $nombre = $_POST['nombre'];
                $fecha = $_POST['fecha'];
                $hora = $_POST['hora'];
                $costo = $_POST['costo'];
                $result = $this->empresa->registrar_reserva($id_cancha,$nombre,$fecha,$hora,$costo);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo json_encode($result);
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
        $model = $this->empresa->estadisticas_por_empresa($fecha_i,$fecha_f,$id_empresa);
        $resources = array();
        for ($i=0;$i<count($model);$i++) {
            $resources[$i] = array(
                "cancha_id" => $model[$i]->cancha_id,
                "reserva_id" => $model[$i]->reserva_id,
                "cancha_nombre" => $model[$i]->cancha_nombre,
                "reserva_nombre" => $model[$i]->reserva_nombre,
                "reserva_hora" => $model[$i]->reserva_hora,
                "reserva_costo" => $model[$i]->reserva_costo
            );
        }
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function valorar_empresa() {
        $usuario_id = $_POST['id_usuario'];
        $id_empresa = $_POST['id_empresa'];
        $valor = $_POST['valor'];
        $mi_rating = $this->empresa->listar_detalle_rating_empresa($id_empresa,$usuario_id);
        if($mi_rating->rating_empresa_valor!=null){
            $result = $this->empresa->actualizar_valorar_empresa($usuario_id,$id_empresa,$valor);
        }else{
            $result = $this->empresa->valorar_empresa($usuario_id,$id_empresa,$valor);
        }
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
}