<?php
class RecargaAgente{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    //Guardar o Editar Informacion de Empresa
    public function save_recarga_agente($model){
        try {
            $fecha = date('Y-m-d H:i:s');
            $sql = "insert into transferencia_e_e(id_empresa_emisor, id_empresa_receptor,transferencia_e_e_monto,transferencia_e_e_nro_operacion,transferencia_e_e_concepto,transferencia_e_e_estado,transferencia_e_e_date) values(?,?,?,?,?,1,?)";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->emisor,
                $model->receptor,
                $model->monto,
                $model->nro_operacion,
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
    public function listar_recargas_agente(){
        try {
            $sql = 'select * from transferencia_e_e tee inner join cuenta_empresa ce on tee.id_empresa_receptor=ce.id_cuenta_empresa inner join agente a on a.id_cuenta=ce.id_cuenta_empresa';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}