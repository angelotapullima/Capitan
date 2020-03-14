<?php
class Agente{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }

    //Guardar o Editar Informacion de Empresa
    public function save_agente($model){
        try {
            $fecha = date('Y-m-d H:i:s');
            $sql = "insert into agente(id_cuenta, agente_nombre,agente_direccion,agente_coord_x,agente_coord_y,agente_estado) values(?,?,?,?,?,1)";
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $model->id_cuenta,
                $model->agente_nombre,
                $model->agente_direccion,
                $model->agente_coord_x,
                $model->agente_coord_y
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_agentes(){
        try {
            $sql = 'select * from agente a inner join cuenta_empresa ce on a.id_cuenta = ce.id_cuenta_empresa where a.agente_estado=1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_agentes_por_id_user($id_user){
        try {
            $sql = 'select * from agente a inner join cuenta_empresa ce on a.id_cuenta = ce.id_cuenta_empresa inner join empresa e on ce.id_empresa=e.id_empresa where a.agente_estado=1 and e.id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id_user]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_agente_por_id($id){
        try {
            $sql = 'select * from agente a inner join cuenta_empresa ce on a.id_cuenta=ce.id_cuenta_empresa where a.id_agente=? limit 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}