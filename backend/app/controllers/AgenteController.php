<?php
require 'app/models/Agente.php';
require 'app/models/Empresa.php';
class AgenteController{
    private $crypt;
    private $clean;
    private $nav;
    private $log;
    private $agente;
    private $empresa;
    private $validate;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->clean = new Clean();
        $this->log = new Log();
        $this->agente = new Agente();
        $this->empresa = new Empresa();
        $this->validate = new Validate();
    }
    //Vistas
    public function agregar_agente(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $cuentaes=$this->empresa->listar_empresas_por_id_ciudad(1);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'agente/agregar_agente.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver_agentes(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $agentes = $this->agente->listar_agentes();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'agente/ver_agentes.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function save_agente(){
        try{
            $model = new Agente();
            $ok_data = true;
            if(isset($_POST['agente_cuentae']) && isset($_POST['agente_name']) && isset($_POST['agente_direccion']) && isset($_POST['agente_coord_x']) && isset($_POST['agente_coord_y'])){
                $_POST['agente_cuentae'] = $this->clean->clean_post_int($_POST['agente_cuentae']);
                $_POST['agente_name'] = $this->clean->clean_post_str($_POST['agente_name']);
                $_POST['agente_direccion'] = $this->clean->clean_post_str($_POST['agente_direccion']);
                $_POST['agente_coord_x'] = $this->clean->clean_post_float($_POST['agente_coord_x']);
                $_POST['agente_coord_y'] = $this->clean->clean_post_float($_POST['agente_coord_y']);

                $ok_data = $this->clean->validate_post_int($_POST['agente_cuentae'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['agente_name'], true, $ok_data, 200);
                $ok_data = $this->clean->validate_post_str($_POST['agente_direccion'], true, $ok_data, 250);
            }else{
                $ok_data = false;
            }
            if($ok_data){
                $model->id_cuenta = $_POST['agente_cuentae'];
                $model->agente_nombre = $_POST['agente_name'];
                $model->agente_direccion = $_POST['agente_direccion'];
                $model->agente_coord_x = $_POST['agente_coord_x'];
                $model->agente_coord_y = $_POST['agente_coord_y'];
                $result = $this->agente->save_agente($model);
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
    public function listar_agente_por_id(){
        try{
            $ok_data = true;
            $result = 2;
            $code = 2;
            if(isset($_POST['id_agente'])){
                $_POST['id_agente'] = $this->clean->clean_post_int($_POST['id_agente']);
                $ok_data = $this->clean->validate_post_int($_POST['id_agente'], true, $ok_data, 11);
            }
            if($ok_data){
                $id_agente = $_POST['id_agente'];
                $result = $this->agente->listar_agente_por_id($id_agente);
                (isset($result->id_agente))?$code=1:$code=2;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $data = array("code" =>$code,"result" => $result);
        echo json_encode($data);
    }
}