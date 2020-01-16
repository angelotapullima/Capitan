<?php
class Empresa{
    private $pdo;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }
    public function registrar($usuario_id,$ubigeo_id,$nombre,$direccion,$coord_x,$coord_y,$telefono_1,$telefono_2,$descripcion,$horario_1,$horario_2,$foto){
        try {
            $sql = 'insert into empresa(
                    usuario_id,
                    ubigeo_id,
                    empresa_nombre,
                    empresa_direccion,
                    empresa_coord_x,
                    empresa_coord_y,
                    empresa_telefono_1,
                    empresa_telefono_2,
                    empresa_descripcion,
                    empresa_horario_ls,
                    empresa_horario_d,
                    empresa_valoracion,
                    empresa_foto,
                    empresa_estado
                    ) values(?,?,?,?,?,?,?,?,?,?,?,0,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $usuario_id,$ubigeo_id,$nombre,$direccion,$coord_x,$coord_y,$telefono_1,$telefono_2,$descripcion,$horario_1,$horario_2,$foto
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_empresas_por_id_ciudad($id_ciudad){
        try {
            $stm = $this->pdo->prepare("select * from empresa e inner join ubigeo u on e.ubigeo_id=u.ubigeo_id where u.ubigeo_ciudad=? and empresa_estado=1");
            $stm->execute([$id_ciudad]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_detalle_empresa($id_empresa){
        try {
            $stm = $this->pdo->prepare("select * from empresa e inner join user u on e.usuario_id = u.id_user inner join ubigeo ub on ub.ubigeo_id = e.ubigeo_id where e.empresa_id =?");
            $stm->execute([$id_empresa]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function registrar_cancha($empresa_id,$nombre,$dimensiones,$precioD,$precioN,$foto){
        try {
            $sql = 'insert into cancha(
                    empresa_id,
                    cancha_nombre,
                    cancha_dimensiones,
                    cancha_precioD,
                    cancha_precioN,
                    cancha_foto,
                    cancha_estado
                    ) values(?,?,?,?,?,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $empresa_id,$nombre,$dimensiones,$precioD,$precioN,$foto
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_reservados_por_cancha_por_fecha($id_cancha,$fecha){
        try {
            $stm = $this->pdo->prepare("select * from reserva where cancha_id =? and reserva_fecha=?");
            $stm->execute([$id_cancha,$fecha]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_cancha_por_id($id){
        try {
            $stm = $this->pdo->prepare("select * from cancha c inner join empresa e on c.empresa_id = e.empresa_id where c.cancha_id =?");
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_canchas_por_id_empresa($id_empresa){
        try {
            $stm = $this->pdo->prepare("select * from cancha where empresa_id =? and cancha_estado = 1");
            $stm->execute([$id_empresa]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_canchas_libres_por_hora($fecha,$hora){
        $hora_1 = $hora + 1;
        $hora=$hora.":00-".$hora_1.":00";
        try {
            $stm = $this->pdo->prepare("SELECT DISTINCT e.*,c.cancha_precioD,c.cancha_precioN FROM cancha c inner join empresa e on e.empresa_id=c.empresa_id where c.cancha_id NOT in (SELECT r.cancha_id from reserva r where r.reserva_fecha=? and r.reserva_hora=?) and e.empresa_estado=1 and c.cancha_estado=1 group by e.empresa_id");
            $stm->execute([$fecha,$hora]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function busqueda_avanzada_tres_params($fecha,$hora,$negocio){
        $hora_1 = $hora + 1;
        $hora=$hora.":00-".$hora_1.":00";
        try {
            $stm = $this->pdo->prepare("SELECT DISTINCT e.*,c.cancha_precioD,c.cancha_precioN FROM cancha c inner join empresa e on e.empresa_id=c.empresa_id where c.cancha_id NOT in (SELECT r.cancha_id from reserva r where r.reserva_fecha=? and r.reserva_hora=?) and e.empresa_estado=1 and e.empresa_id = ? and c.cancha_estado=1 group by e.empresa_id");
            $stm->execute([$fecha,$hora,$negocio]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function estadisticas_por_empresa($fecha_i,$fecha_f,$id_empresa){
        try {
            $stm = $this->pdo->prepare("SELECT * FROM reserva r inner join cancha c on r.cancha_id=c.cancha_id inner join empresa e on e.empresa_id=c.empresa_id where r.reserva_fecha between ? and ? and e.empresa_id = ?");
            $stm->execute([$fecha_i,$fecha_f,$id_empresa]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function registrar_reserva($id_cancha,$nombre,$fecha,$hora,$costo){
        try {
            $sql = 'insert into reserva(
                    cancha_id,
                    reserva_nombre,
                    reserva_fecha,
                    reserva_hora,
                    reserva_costo,
                    reserva_estado
                    ) values(?,?,?,?,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id_cancha,$nombre,$fecha,$hora,$costo
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_rating_empresa($id_empresa){
        try {
            $stm = $this->pdo->prepare("select sum(rating_empresa_valor) as suma, count(id_rating_empresa) as conteo from rating_empresa where id_empresa =?");
            $stm->execute([$id_empresa]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_detalle_rating_empresa($id_empresa,$id_usuario){
        try {
            $stm = $this->pdo->prepare("select rating_empresa_valor from rating_empresa where id_empresa =? and id_usuario=?");
            $stm->execute([$id_empresa,$id_usuario]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function actualizar_valorar_empresa($usuario_id,$id_empresa,$valor){
        $result = 2;
        try {
            $sql = 'update rating_empresa set rating_empresa_valor = ? where id_usuario = ? and id_empresa = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $valor,$usuario_id,$id_empresa
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function valorar_empresa($usuario_id,$id_empresa,$valor){
        try {
            $sql = 'insert into rating_empresa(
                    id_usuario,
                    id_empresa,
                    rating_empresa_valor
                    ) values(?,?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $usuario_id,$id_empresa,$valor
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}