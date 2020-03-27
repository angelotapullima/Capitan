<?php
class Transferencia{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    //Guardar o Editar Informacion de Empresa
    public function save_transferencia($model){
        try {
            $fecha = date('Y-m-d H:i:s');
            $sql = "insert into transferencia_u_u(transferencia_u_u_nro_operacion, id_usuario_emisor,id_usuario_receptor,transferencia_u_u_monto,transferencia_u_u_concepto,transferencia_u_u_date,transferencia_u_u_estado) values(?,?,?,?,?,?,1)";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->nro_operacion,
                $model->emisor,
                $model->receptor,
                $model->monto,
                $model->concepto,
                $fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function save_transferencia_u_e($model){
        try {
            $fecha = date('Y-m-d H:i:s');
            $sql = 'insert into transferencia_u_e (transferencia_u_e_nro_operacion, id_usuario,id_empresa,id_pago,transferencia_u_e_monto,transferencia_u_e_concepto,transferencia_u_e_date,transferencia_u_e_estado) values (?,?,?,?,?,?,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->nro_operacion,
                $model->id_user,
                $model->id_empresa,
                $model->id_pago,
                $model->monto,
                $model->concepto,
                $fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function delete_transferencia_u_e_por_pago_id($id){
        try{
            $sql = 'delete from transferencia_u_e where id_pago = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_mis_transferencias($id_user){
        try {
            $sql = 'select * from transferencia_u_u t inner join cuenta c on t.id_usuario_emisor = c.id_cuenta where c.id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_user]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }public function listar_mis_transferencias_recibidas($id_user){
        try {
            $sql = 'select * from transferencia_u_u t inner join cuenta c on t.id_usuario_receptor = c.id_cuenta where c.id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_user]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }

}