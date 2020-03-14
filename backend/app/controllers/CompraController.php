<?php
require 'app/models/Cuenta.php';
require 'app/models/CuentaEmpresa.php';
require 'app/models/Compra.php';
class CompraController{
    private $crypt;
    private $nav;
    private $log;
    private $cuenta;
    private $clean;
    private $cuentaempresa;
    private $compra;
    private $validate;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->log = new Log();
        $this->clean = new Clean();
        $this->cuenta = new Cuenta();
        $this->cuentaempresa = new CuentaEmpresa();
        $this->compra = new Compra();
        $this->validate = new Validate();
    }

    public function ver_mis_compras(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $compras = $this->compra->listar_mis_compras($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'compra/ver_mis_compras.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function save(){
        try{
            $model = new Compra();
            $ok_data = true;
            if(isset($_POST['id_user']) && isset($_POST['id_empresa']) && isset($_POST['id_proyecto']) && isset($_POST['id_venta_asoc'])&& isset($_POST['monto'])&& isset($_POST['concepto'])&& isset($_POST['nro_operacion']) ){
                $_POST['id_user'] = $this->clean->clean_post_int($_POST['id_user']);
                $_POST['id_empresa'] = $this->clean->clean_post_int($_POST['id_empresa']);
                $_POST['id_proyecto'] = $this->clean->clean_post_int($_POST['id_proyecto']);
                $_POST['id_venta_asoc'] = $this->clean->clean_post_int($_POST['id_venta_asoc']);
                $_POST['concepto'] = $this->clean->clean_post_str($_POST['concepto']);

                $ok_data = $this->clean->validate_post_int($_POST['id_user'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_empresa'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_proyecto'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_venta_asoc'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_str($_POST['concepto'], true, $ok_data, 50);
            }
            if($ok_data){
                $model->id_user = $_POST['id_user'];
                $model->id_empresa = $_POST['id_empresa'];
                $model->id_proyecto = $_POST['id_proyecto'];
                $model->id_venta_asoc = $_POST['id_venta_asoc'];
                $model->monto = $_POST['monto'];
                $model->concepto = $_POST['concepto'];
                $model->nro_operacion = $_POST['nro_operacion'];
                /*$model->id_user = 1;
                $model->id_empresa = 3;
                $model->id_proyecto = 1;
                $model->id_venta_asoc = 1;
                $model->monto = 15;
                $model->concepto = 'Compra de gorra';
                $model->nro_operacion = '1111111';*/
                $result = $this->compra->save($model);
                if($result ==1){
                    $user_cuenta = $this->cuenta->listar_cuenta_por_id($model->id_user);

                    $modelu = new Cuenta();
                    $modelu->monto = $model->monto * (-1);
                    $modelu->receptor = $model->id_user;

                    $modele = new CuentaEmpresa();
                    $modele->monto = $model->monto * 1;
                    $modele->receptor = $model->id_empresa;

                    $this->cuentaempresa->sumar_saldo($modele);
                    $this->cuenta->sumar_saldo($modelu);

                    $destino = $user_cuenta->user_email;
                    $titulo = "Compra Realizada";
                    $contenido = "Su compra fue realizada con éxito.";
                    $this->validate->send_email($destino,$titulo,$contenido);
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        echo $result;
    }
}