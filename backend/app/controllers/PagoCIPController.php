<?php
require 'app/models/PagoCIP.php';
require 'app/models/CuentaEmpresa.php';
require 'app/models/Cuenta.php';
require 'app/models/Transferencia.php';
require 'app/models/Agente.php';
class PagoCIPController{
    private $crypt;
    private $clean;
    private $nav;
    private $log;
    private $agente;
    private $cuentaempresa;
    private $cuenta;
    private $pagocip;
    private $transferencia;
    private $validate;
    public function __construct()
    {
        $this->crypt = new Crypt();
        $this->clean = new Clean();
        $this->log = new Log();
        $this->agente = new Agente();
        $this->cuentaempresa = new CuentaEmpresa();
        $this->cuenta = new Cuenta();
        $this->transferencia = new Transferencia();
        $this->pagocip = new PagoCIP();
        $this->validate = new Validate();
    }
    //Vistas
    public function pagar_cip(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $agentes=$this->agente->listar_agentes_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'pagocip/pagar_cip.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function retirar_cip(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $agentes=$this->agente->listar_agentes_por_id_user($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'pagocip/retirar_cip.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver_mis_pagos_cip(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $pagos_cip = $this->pagocip->listar_mis_pagos_cip($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'pagocip/ver_mis_pagos_cip.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function ver_mis_retiros_cip(){
        try{
            $this->nav = new Navbar();
            $navs = $this->nav->listMenu($this->crypt->decrypt($_SESSION['role'],_FULL_KEY_));
            $pagos_cip = $this->pagocip->listar_mis_retiros_cip($this->crypt->decrypt($_SESSION['id_user'],_FULL_KEY_));
            require _VIEW_PATH_ . 'header.php';
            require _VIEW_PATH_ . 'navbar.php';
            require _VIEW_PATH_ . 'pagocip/ver_mis_retiros_cip.php';
            require _VIEW_PATH_ . 'footer.php';

        } catch (Throwable $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            echo "<script language=\"javascript\">alert(\"Error Al Mostrar Contenido. Redireccionando Al Inicio\");</script>";
            echo "<script language=\"javascript\">window.location.href=\"". _SERVER_ ."\";</script>";
        }
    }
    public function save_detalle_pagocip(){
        try{
            $model = new PagoCIP();
            $ok_data = true;
            if (isset($_POST['id_pagocip']) && isset($_POST['id_agente'])){

                $_POST['id_pagocip'] = $this->clean->clean_post_int($_POST['id_pagocip']);
                $_POST['id_agente'] = $this->clean->clean_post_int($_POST['id_agente']);
                $ok_data = $this->clean->validate_post_int($_POST['id_pagocip'], true, $ok_data, 11);
                $ok_data = $this->clean->validate_post_int($_POST['id_agente'], true, $ok_data, 11);
                if($ok_data){
                    $model->id_pagocip = $_POST['id_pagocip'];
                    $model->id_agente = $_POST['id_agente'];
                    $datos = $this->pagocip->listar_pagocip_por_id($_POST['id_pagocip']);
                    $user_cuenta = $this->cuenta->listar_cuenta_por_id($datos->id_cuenta);
                    if(isset($datos->id_pagocip)){
                        //if($datos->pagocip_tipo == 1){
                            $monto = $datos->pagocip_monto * (- 1);
                            $agente = $this->agente->listar_agente_por_id($_POST['id_agente']);
                            if(isset($agente->id_agente)){
                                $id_cuenta = $agente->id_cuenta;
                                $model->monto = $monto;
                                $model->receptor = $id_cuenta;
                                $result = $this->pagocip->save_pagocip($model);
                                if($result==1){
                                    $modele = new Cuenta();
                                    $modele->monto = $datos->pagocip_monto;
                                    $modele->receptor = $datos->id_cuenta;
                                    $this->cuentaempresa->sumar_saldo($model);
                                    $this->cuenta->sumar_saldo($modele);
                                    $this->pagocip->cambiar_estado_pagocip(1,$datos->id_pagocip);
                                    $destino = $user_cuenta->user_email;
                                    //guardar transferencia
                                    $modelando_T = new Transferencia();
                                    $last_data=$this->transferencia->listar_ultima_transferencia_u_e();
                                    (isset($last_data->id_transferencia_u_e)) ?$new_code = $last_data->transferencia_u_e_nro_operacion * 1 + 1:$new_code=121000001;
                                    $modelando_T->nro_operacion = $new_code;
                                    $modelando_T->id_user = $user_cuenta->id_user;
                                    $modelando_T->id_empresa = 1;
                                    $modelando_T->id_pago = 0;
                                    $modelando_T->monto = $datos->pagocip_monto;
                                    $modelando_T->concepto = "Recarga de Saldo ".$user_cuenta->user_nickname;
                                    $this->transferencia->save_transferencia_u_e($modelando_T);
                                    //fin guardar transferencia
                                    $titulo = "Pago recibido";
                                    $contenido = "<p>Su pago fue recibido con éxito. Ya puedes disfrutar de tu nuevo saldo</p>";
                                    $this->validate->send_email($destino,$titulo,$contenido);
                                }else{
                                    $result = 2;
                                }
                            }else{
                                $result = 4;
                            }
                        /*}elseif ($datos->pagocip_tipo == 2){
                            $monto = $datos->pagocip_monto * 1;
                            $agente = $this->agente->listar_agente_por_id($_POST['id_agente']);
                            if(isset($agente->id_agente)){
                                $id_cuenta = $agente->id_cuenta;
                                $model->monto = $monto;
                                $model->receptor = $id_cuenta;
                                $result = $this->pagocip->save_pagocip($model);
                                if($result==1){
                                    $modele = new Cuenta();
                                    $modele->monto = $datos->pagocip_monto * (-1);
                                    $modele->receptor = $datos->id_cuenta;
                                    $this->cuentaempresa->sumar_saldo($model);
                                    $this->cuenta->sumar_saldo($modele);
                                    $this->pagocip->cambiar_estado_pagocip(1,$datos->id_pagocip);
                                    $destino = $user_cuenta->user_email;
                                    $titulo = "Retiro realizado";
                                    $contenido="<p>Su retiro fue recibido con éxito. Ya puedes disfrutar de tu nuevo saldo</p>";
                                    $this->validate->send_email($destino,$titulo,$contenido);
                                }else{
                                    $result = 2;
                                }
                            }else{
                                $result = 4;
                            }
                        }*/
                    }else{
                        $result = 3;
                    }
                }else{
                 $result = 6;
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
    public function buscar_pagocip_pendiente_por_codigo(){
        try{
            $ok_data = true;
            $code = 2;
            if(isset($_POST['codigo']) && isset($_POST['id_user'])){
                $_POST['codigo'] = $this->clean->clean_post_str($_POST['codigo']);
                $ok_data = $this->clean->validate_post_str($_POST['codigo'], true, $ok_data, 6);
            }
            if($ok_data){
                $codigo = $_POST['codigo'];
                $id_user = $_POST['id_user'];
                $result = $this->pagocip->listar_pagocip_pendiente_por_codigo($codigo);
                if(isset($result->id_pagocip)){
                    if($result->pagocip_tipo="Agente"){
                        $datos_agente=$this->agente->listar_agente_por_id_user($id_user);
                        if(isset($datos_agente->id_agente)){
                            $code=1;
                        }else{
                            $code=2;
                            $result=[];
                        }
                    }else{
                        $code=1;
                    }
                }else{
                    $code=2;
                    $result=[];
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $data = array("code"=>$code,"result" => $result);
        echo json_encode($data);
    }
    public function listar_mis_movimientos_agente(){
        try{
            $ok_data = true;
            if(isset($_POST['id_user'])){
                $_POST['id_user'] = $this->clean->clean_post_int($_POST['id_user']);
                $ok_data = $this->clean->validate_post_int($_POST['id_user'], true, $ok_data, 6);
            }
            if($ok_data){
                $id_user = $_POST['id_user'];
                $result=[];
                $data_pagos = $this->pagocip->listar_mis_pagos_cip($id_user);
                foreach ($data_pagos as $da){
                    $result[]=array(
                        "nro_operacion"=>$da->transferencia_u_e_nro_operacion,
                        "monto"=>$da->transferencia_u_e_monto,
                        "concepto"=>$da->transferencia_u_e_concepto,
                        "fecha"=>$da->transferencia_u_e_date,
                        "ind"=>0
                    );
                }
                $data_recargas = $this->transferencia->listar_mis_recargas_agente($id_user);
                foreach ($data_recargas as $da){
                    $result[]=array(
                        "nro_operacion"=>$da->transferencia_e_e_nro_operacion,
                        "monto"=>$da->transferencia_e_e_monto,
                        "concepto"=>$da->transferencia_e_e_concepto,
                        "fecha"=>$da->transferencia_e_e_date,
                        "ind"=>1
                    );
                }
            }else{
                $result = 6;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $data = array("result" => $result);
        echo json_encode($data);
    }
    public function buscar_pagocip_pendiente_por_codigo_retirar(){
        try{
            $ok_data = true;
            $code = 2;
            if(isset($_POST['codigo'])){
                $_POST['codigo'] = $this->clean->clean_post_str($_POST['codigo']);
                $ok_data = $this->clean->validate_post_str($_POST['codigo'], true, $ok_data, 6);
            }
            if($ok_data){
                $codigo = $_POST['codigo'];
                $result = $this->pagocip->listar_pagocip_pendiente_por_codigo_retirar($codigo);
                (isset($result->id_pagocip))? $code=1 :$code=2;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        $data = array("code"=>$code,"result" => $result);
        echo json_encode($data);
    }
}