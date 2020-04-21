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
            $cuentaes=$this->empresa->listar_empresas_por_id_ciudad('Iquitos');
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
            $model2 = new Agente();
            $ok_data=true;
            if(isset($_POST['id_agente']) && isset($_POST['monto'])){
                $_POST['id_agente'] = $this->clean->clean_post_int($_POST['id_agente']);
                $ok_data = $this->clean->validate_post_int($_POST['id_agente'], true, $ok_data, 11);
                if($ok_data){
                    $result=2;
                    $last_data=$this->recargaagente->listar_ultima_transferencia_e_e();
                    if(isset($last_data->id_transferencia_e_e)){
                        $new_code= $last_data->transferencia_e_e_nro_operacion * 1 + 1;
                        $model->emisor =1;
                        $model->receptor = $_POST['id_agente'];
                        $model2->receptor = 1;
                        $model->monto = $_POST['monto'];
                        $model2->monto = $_POST['monto'];
                        $model->nro_operacion = $new_code;
                        $file_path = 'media/t_e_e/'.$new_code.'.jpg';
                        $model->imagen = $file_path;
                        $model->concepto = "Recarga de Agente";
                        $result = $this->recargaagente->save_recarga_agente($model);
                        if($result==1){
                            $this->cuentaempresa->sumar_saldo($model);
                            $this->cuentaempresa->sumar_saldo($model2);
                            move_uploaded_file($_FILES['imagen']['tmp_name'], $file_path);
                        }
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