<?php
require 'app/models/Transferencia.php';
require 'app/models/Cuenta.php';
require 'app/models/User.php';
class TransferenciaController{
    private $crypt;
    private $nav;
    private $log;
    private $cuenta;
    private $clean;
    private $user;
    private $transferencia;
    private $validate;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->log = new Log();
        $this->cuenta = new Cuenta();
        $this->clean = new Clean();
        $this->user = new User();
        $this->transferencia = new Transferencia();
        $this->validate = new Validate();
    }
    //Vistas
    public function transferir(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $datos_cuenta = $this->user->listar_cuenta_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'transferencia/transferir.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver_mis_transferencias(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $transferencias = $this->transferencia->listar_mis_transferencias($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            $transferencias_recibidas = $this->transferencia->listar_mis_transferencias_recibidas($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'transferencia/listar_mis_transferencias.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function save_transferencia(){
        try{
            $model = new Transferencia();
            $ok_data = true;
            if(isset($_POST['monto']) && isset($_POST['id_cuenta'])){
                $_POST['id_cuenta'] = $this->clean->clean_post_int($_POST['id_cuenta']);
                $ok_data = $this->clean->validate_post_int($_POST['id_cuenta'], true, $ok_data, 11);
                if($ok_data){
                    $monto = $_POST['monto'];
                    $model->receptor = $_POST['id_cuenta'];
                    $model->monto = $monto * 1;
                    $datos_cuenta = $this->user->listar_cuenta_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
                    if(isset($datos_cuenta->id_cuenta)){
                        $model->emisor = $datos_cuenta->id_cuenta;
                        $model->concepto = "Transferencia";
                        $model->nro_operacion = "1111111";
                        $saldo = $datos_cuenta->cuenta_saldo * 1;
                        if($saldo>=$monto){
                            $result = $this->transferencia->save_transferencia($model);
                            if($result==1){
                                $modele = new Cuenta();
                                $modele->monto = $monto * (-1);
                                $modele->receptor = $datos_cuenta->id_cuenta;
                                $this->cuenta->sumar_saldo($model);
                                $this->cuenta->sumar_saldo($modele);
                                $destino = $datos_cuenta->user_email;
                                $titulo = "Transferencia Realizada";
                                $contenido = "Su transferencia fue realizada con éxito.";
                                $this->validate->send_email($destino,$titulo,$contenido);
                            }else{
                                $result = 2;
                            }
                        }else{
                            $result = 3;
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
    public function buscar_cuenta_por_codigo(){
        try{
            $ok_data = true;
            $code = 2;
            if(isset($_POST['codigo'])){
                $_POST['codigo'] = $this->clean->clean_post_str($_POST['codigo']);
                $ok_data = $this->clean->validate_post_str($_POST['codigo'], true, $ok_data, 12);
            }
            if($ok_data){
                $codigo = $_POST['codigo'];
                $result = $this->cuenta->listar_cuenta_por_codigo($codigo);
                (isset($result->id_cuenta))? $code=1 :$code=2;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $data = array("code"=>$code,"result" => $result);
        echo json_encode($data);
    }
}