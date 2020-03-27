<?php
require 'app/models/Cuenta.php';
require 'app/models/PagoCIP.php';
require 'app/models/User.php';
class CuentaController{
    private $crypt;
    private $nav;
    private $log;
    private $cuenta;
    private $pagocip;
    private $user;
    private $validate;
    private $clean;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->log = new Log();
        $this->cuenta = new Cuenta();
        $this->pagocip = new PagoCIP();
        $this->user = new User();
        $this->validate = new Validate();
        $this->clean = new Clean();
    }
    //Vistas
    public function detalles_cuenta(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $datos_cuenta = $this->user->listar_cuenta_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            //$ultima_compra = $this->user->listar_ultima_compra_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            //$ultima_transferencia = $this->user->listar_ultima_transferencia_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            //$ultimas_recargas = $this->cuenta->listar_ultimas_recargas($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));

            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'cuenta/detalles_cuenta.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function recargar_mi_cuenta(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $datos_cuenta = $this->user->listar_cuenta_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            $recarga_pendiente = $this->cuenta->listar_recarga_pendiente($datos_cuenta->id_cuenta);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'cuenta/recargar_mi_cuenta.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function historial_recargas_cuenta(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $datos_cuenta = $this->user->listar_cuenta_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            $recargas = $this->cuenta->listar_recargas_por_id($datos_cuenta->id_cuenta);
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'cuenta/historial_recargas_cuenta.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function save_recargar_mi_cuenta(){
        try{
            if(isset($_POST['monto']) && isset($_POST['tipo'])){
                $fecha = date('Y-m-d H:i:s');
                $fecha_exp = date('Y-m-d H:i:s',strtotime($fecha." + 1 days"));
                $model = new Cuenta();
                if(isset($_SESSION['id_user'])){
                    $id_user = $this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_);
                }else{
                    $id_user = $_POST['id_user'];
                }
                $datos_cuenta = $this->user->listar_cuenta_por_id_user($id_user);
                $model->id_cuenta = $datos_cuenta->id_cuenta;
                $longitud = 6;
                $token = '';
                $pattern = '1234567890';
                $max = strlen($pattern)-1;
                do{
                    for($i=0;$i < $longitud;$i++) $token .= $pattern{mt_rand(0,$max)};
                    $exists = $this->pagocip->listar_pagocip_pendiente_por_codigo($token);
                    (isset($exists->id_pagocip))?$exists=true:$exists=false;
                }while($exists);
                $model->codigo = $token;
                $model->monto = $_POST['monto'];
                $model->tipo = $_POST['tipo'];
                $model->concepto = 'Recargar mi cuenta';
                $model->estado = 2;
                $model->date = $fecha;
                $model->date_expiracion = $fecha_exp;
                $result = $this->cuenta->save_recargar_mi_cuenta($model);
                if($result==1){
                    $destino = $datos_cuenta->user_email;
                    $titulo = "Solicitud de recarga";
                    $contenido = "<p>Su solicitud de recarga de su cuenta de Bufeo Tec se realizó con éxito.<br>Acérquese a cualquier agente autorizado y brinde el siguiente código: </p><h2 style='color: red; font-weight: bold;'>$token</h2><p>El monto a pagar es de S/. ".$_POST['monto']."</p>";
                    $this->validate->send_email($destino,$titulo,$contenido);
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $response = array("code" => $result,"codigo" => $token,"monto"=>$_POST['monto'],"date_expiracion"=>$fecha_exp);
        $data = array("result" => $response);
        echo json_encode($data);
    }
    public function save_retirar(){
        try{
            if(isset($_POST['monto'])){
                $fecha = date('Y-m-d H:i:s');
                $fecha_exp = date('Y-m-d H:i:s',strtotime($fecha." + 1 days"));
                $model = new Cuenta();
                $datos_cuenta = $this->user->listar_cuenta_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
                $model->id_cuenta = $datos_cuenta->id_cuenta;
                $destino = $datos_cuenta->user_email;
                $longitud = 6;
                $token = '';
                $pattern = '1234567890';
                $max = strlen($pattern)-1;
                do{
                    for($i=0;$i < $longitud;$i++) $token .= $pattern{mt_rand(0,$max)};
                    $exists = $this->pagocip->listar_pagocip_pendiente_por_codigo($token);
                    (isset($exists->id_pagocip))?$exists=true:$exists=false;
                }while($exists);
                $model->codigo = $token;
                $model->monto = $_POST['monto'];
                $model->concepto = 'Retirar';
                $model->estado = 2;
                $model->date = $fecha;
                $model->date_expiracion = $fecha_exp;
                $result = $this->cuenta->save_retirar($model);
                if($result==1){
                    $titulo = "Solicitud de retiro";
                    $contenido = "<p>Su solicitud de retiro de su cuenta de Bufeo Tec se realizó con éxito.<br>Acérquese a cualquier agente autorizado y brinde el siguiente código: </p><h2 style='color: red; font-weight: bold;'>$token</h2><p>El monto a retirar es de S/. ".$_POST['monto']."</p>";
                    $this->validate->send_email($destino,$titulo,$contenido);
                }
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $response = array("code" => $result,"codigo" => $token,"monto"=>$_POST['monto'],"date_expiracion"=>$fecha);
        $data = array("result" => $response);
        echo json_encode($data);
    }
}