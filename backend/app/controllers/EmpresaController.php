<?php
require_once 'app/models/Empresa.php';
require_once 'app/models/Usuario.php';
class EmpresaController{
    private $empresa;
    private $usuario;
    public function __construct(){
        $this->empresa = new Empresa();
        $this->usuario = new Usuario();
    }
    public function registrar() {
        $usuario_id = $_POST['usuario_id'];
        $ubigeo_id = $_POST['ubigeo_id'];
        $nombre = $_POST['nombre'];
        $direccion = $_POST['direccion'];
        $telefono = $_POST['telefono'];
        $horario = $_POST['horario'];
        $descripcion = $_POST['descripcion'];
        $file_path = "media/company/".$usuario_id."_".$nombre.".jpg";
        move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path);
        /*$existe = $this->usuario->existe_nombre($usuario);
        if($existe->id_usuario!=null){
            $result = 3;
        }else{
            $result = $this->usuario->registrar($nombre,$apellido,$email,$usuario,$contrasenha,$rol,$usu_supe);
        }*/
        $result = $this->empresa->registrar($usuario_id,$ubigeo_id,$nombre,$direccion,$telefono,$descripcion,$horario,$file_path);
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
        $empresa_id = $_POST['empresa_id'];
        $nombre = $_POST['nombre'];
        $dimensiones = $_POST['dimensiones'];
        $precioD = $_POST['precioD'];
        $precioN = $_POST['precioN'];
        $file_path = "media/company/cancha/".$empresa_id."_".$nombre.".jpg";
        move_uploaded_file($_FILES['imagen']['tmp_name'],$file_path);
        $result = $this->empresa->registrar_cancha($empresa_id,$nombre,$dimensiones,$precioD,$precioN,$file_path);
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
        $id_cancha = $_POST['id_cancha'];
        $nombre = $_POST['nombre'];
        $fecha = $_POST['fecha'];
        $hora = $_POST['hora'];
        $costo = $_POST['costo'];
        $result = $this->empresa->registrar_reserva($id_cancha,$nombre,$fecha,$hora,$costo);
        $resources = array();
        $resources[0] = array("valor"=>$result);
        $data = array("results" => $resources);
        echo json_encode($data);
    }
    public function listar_canchas_libres_por_hora(){
        /*$fecha = $_POST['fecha'];
        $hora_i = $_POST['hora_i'];
        $hora_f = $_POST['hora_f'];
        */
        date_default_timezone_set('America/Lima');
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