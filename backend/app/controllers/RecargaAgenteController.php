<?php
require 'app/models/RecargaAgente.php';
require 'app/models/Agente.php';
require 'app/models/Empresa.php';
require 'app/models/CuentaEmpresa.php';
class RecargaAgenteController{
    private $crypt;
    private $clean;
    private $nav;
    private $log;
    private $recargaagente;
    private $agente;
    private $empresa;
    private $cuentaempresa;
    private $validate;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->clean = new Clean();
        $this->log = new Log();
        $this->recargaagente = new RecargaAgente();
        $this->agente = new Agente();
        $this->empresa = new Empresa();
        $this->cuentaempresa = new CuentaEmpresa();
        $this->validate = new Validate();
    }
    //Vistas
    public function recargar_agente(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $agentes=$this->agente->listar_agentes();
            $cuentaes=$this->empresa->listar_empresas_activas();
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'recarga_agente/recargar_agente.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver_recargas_agente(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $recargas_agente = $this->recargaagente->listar_recargas_agente();

            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'recarga_agente/ver_recargas_agente.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function save_recarga_agente(){
        try{
            $model = new RecargaAgente();
            $ok_data=true;
            if(isset($_POST['id_emisor']) && isset($_POST['id_agente']) && isset($_POST['monto']) && isset($_POST['nro_operacion']) && isset($_POST['concepto'])){
                $_POST['id_emisor'] = $this->clean->clean_post_int($_POST['id_emisor']);
                $_POST['id_agente'] = $this->clean->clean_post_int($_POST['id_agente']);
                $_POST['nro_operacion'] = $this->clean->clean_post_str($_POST['nro_operacion']);
                $_POST['concepto'] = $this->clean->clean_post_str($_POST['concepto']);

                $ok_data = $this->clean->validate_post_int($_POST['id_emisor'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_agente'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['nro_operacion'], true, $ok_data, 20);
                $ok_data = $this->clean->validate_post_str($_POST['concepto'], true, $ok_data, 100);
                if($ok_data){
                    $model->emisor = $_POST['id_emisor'];
                    $model->receptor = $_POST['id_agente'];
                    $model->monto = $_POST['monto'];
                    $model->nro_operacion = $_POST['nro_operacion'];
                    $model->concepto = $_POST['concepto'];
                    $result = $this->recargaagente->save_recarga_agente($model);
                    if($result==1){
                        $this->cuentaempresa->sumar_saldo($model);
                    }
                }else{
                    $result = 6;
                }
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
}