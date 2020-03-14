<?php
class PagoCIP{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    //Guardar o Editar Informacion de Empresa
    public function save_pagocip($model){
        try {
            $fecha = date('Y-m-d H:i:s');
            $sql = "insert into detalle_pagocip(id_pagocip, id_agente,detalle_pagocip_date) values(?,?,?)";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->id_pagocip,
                $model->id_agente,
                $fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_mis_pagos_cip($id_Agente){
        try {
            $sql = 'select * from detalle_pagocip dp inner join pagocip p on dp.id_pagocip=p.id_pagocip inner join agente a on a.id_agente=dp.id_agente inner join cuenta_empresa ce on ce.id_cuenta_empresa=a.id_cuenta inner join empresa em on em.id_empresa = ce.id_empresa  where em.id_user = ? and p.pagocip_tipo = 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_Agente]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_mis_retiros_cip($id_Agente){
        try {
            $sql = 'select * from detalle_pagocip dp inner join pagocip p on dp.id_pagocip=p.id_pagocip inner join agente a on a.id_agente=dp.id_agente inner join cuenta_empresa ce on ce.id_cuenta_empresa=a.id_cuenta inner join empresa em on em.id_empresa = ce.id_empresa  where em.id_user = ? and p.pagocip_tipo = 2';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_Agente]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_pagocip_pendiente_por_codigo($codigo){
        try {
            $sql = 'select * from pagocip p inner join cuenta c on p.id_cuenta=c.id_cuenta inner join user u on c.id_user = u.id_user inner join person pe on pe.id_person=u.id_person where p.pagocip_codigo = ? and p.pagocip_estado = 2 limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$codigo]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_pagocip_pendiente_por_codigo_retirar($codigo){
        try {
            $sql = 'select * from pagocip p inner join cuenta c on p.id_cuenta=c.id_cuenta inner join user u on c.id_user = u.id_user inner join person pe on pe.id_person=u.id_person where p.pagocip_codigo = ? and p.pagocip_estado = 2 and p.pagocip_tipo = 2 limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$codigo]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_pagocip_por_id($id){
        try {
            $sql = 'select * from pagocip p where p.id_pagocip= ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_detalle_pagocip_por_id_pagocip($id){
        try {
            $sql = 'select * from pagocip p inner join detalle_pagocip d on p.id_pagocip = d.id_pagocip inner join agente a on a.id_agente=d.id_agente where p.id_pagocip= ? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function cambiar_estado_pagocip($est,$id){
        try {
            $sql = "update pagocip set pagocip_estado = ? where id_pagocip = ?";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $est,$id
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}