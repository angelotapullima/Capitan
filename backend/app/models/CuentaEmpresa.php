<?php
class CuentaEmpresa{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    //Guardar o Editar Informacion de Empresa
    public function save_cuentaempresa($model){
        try {
            $fecha = date('Y-m-d H:i:s');
            $sql = "insert into cuenta_empresa(id_empresa, cuentae_codigo,cuentae_saldo,cuentae_moneda,cuentae_date,cuentae_estado) values(?,?,0,'S/.',?,1)";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->id_empresa,
                $model->cuentae_codigo,
                $fecha
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_cuentae_por_codigo($codigo){
        try {
            $sql = 'select * from cuenta_empresa ce inner join empresa e on ce.id_empresa=e.id_empresa where cuentae_codigo=? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$codigo]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_cuentae_por_id($id){
        try {
            $sql = 'select * from cuenta_empresa ce inner join empresa e on ce.id_empresa=e.id_empresa where id_cuenta_empresa=? limit 1';
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
            $sql = "update cuenta_empresa set cuentae_saldo=cuentae_saldo + ? where id_cuenta_empresa = ? ";
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
    public function listar_ultimas_recargas_por_codigo($cod){
        try {
            $sql = 'select * from transferencia_e_e t inner join cuenta_empresa ce on t.id_empresa_receptor=ce.id_cuenta_empresa where ce.cuentae_codigo=? order by id_transferencia_e_e desc limit 10';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$cod]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}