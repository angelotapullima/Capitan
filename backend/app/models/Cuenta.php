<?php
class Cuenta{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    //Guardar o Editar Informacion de Empresa
    public function save_recargar_mi_cuenta($model){
        try {
            $sql = "insert into pagocip(id_cuenta, pagocip_codigo,pagocip_monto,pagocip_concepto,pagocip_estado,pagocip_date,pagocip_date_expiracion) values(?,?,?,?,?,?,?)";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->id_cuenta,
                $model->codigo,
                $model->monto,
                $model->concepto,
                $model->estado,
                $model->date,
                $model->date_expiracion
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function save_retirar($model){
        try {
            $sql = "insert into pagocip(id_cuenta, pagocip_tipo,pagocip_codigo,pagocip_monto,pagocip_concepto,pagocip_estado,pagocip_date,pagocip_date_expiracion) values(?,2,?,?,?,?,?,?)";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->id_cuenta,
                $model->codigo,
                $model->monto,
                $model->concepto,
                $model->estado,
                $model->date,
                $model->date_expiracion
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_ultimas_recargas($id){
        try {
            $sql = 'select * from pagocip p inner join cuenta c on p.id_cuenta=c.id_cuenta inner join user u on c.id_user=u.id_user where u.id_user=? order by id_pagocip desc limit 10';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_cuenta_por_id($id){
        try {
            $sql = 'select * from cuenta c inner join user u on c.id_user=u.id_user inner join person p on p.id_person=u.id_person where c.id_cuenta=? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_cuenta_por_codigo($id){
        try {
            $sql = 'select * from cuenta c inner join user u on c.id_user=u.id_user inner join person p on p.id_person=u.id_person where c.cuenta_codigo=? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_recarga_pendiente($id){
        try {
            $sql = 'select * from pagocip where id_cuenta=? and pagocip_estado = 2 limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_recargas_por_id($id){
        try {
            $sql = 'select * from pagocip where id_cuenta=?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_detalle_pagocip_por_id($id){
        try {
            $sql = 'select * from detalle_pagocip d inner join agente a on d.id_agente=a.id_agente where id_pagocip=? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function sumar_saldo($model){
        try {
            $sql = "update cuenta set cuenta_saldo=cuenta_saldo + ? where id_cuenta = ? ";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->monto,
                $model->receptor
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}