<?php
class Empresa{
    private $pdo;
    //private $pdo2;
    private $log;
    public function __construct(){
        $this->pdo = Database::getConnection();
        //$this->pdo2 = Database_Auth::getConnection();
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
    public function listar_mis_negocios_reserva($id){
        try{
            $sql = 'select * from empresa_usuario eu inner join empresa e on eu.empresa_id=e.empresa_id where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function obtener_chanchas_disponibles($id){
        try{
            $sql = 'select * from colaboracion c inner join equipo e on c.equipo_id=e.equipo_id where e.usuario_id = ? and c.colaboracion_estado = 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function obtener_chanchas_disponibles_por_equipo($id){
        try{
            $sql = 'select * from colaboracion c inner join equipo e on c.equipo_id=e.equipo_id where c.equipo_id = ? and c.colaboracion_estado = 1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function obtener_detalle_chancha($id){
        try{
            $sql = 'select * from colaboracion c inner join detalle_colaboracion dc on c.colaboracion_id=dc.id_colaboracion inner join user u on dc.id_user=u.id_user where c.colaboracion_id = ? and dc.detalle_colaboracion_estado=1';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetchAll();
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
    public function existe_reserva($id_cancha,$fecha,$hora){
        try {
            $stm = $this->pdo->prepare("SELECT * from reserva where cancha_id=? and reserva_fecha=? and reserva_hora=?");
            $stm->execute([$id_cancha,$fecha,$hora]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function estadisticas_por_empresa_fecha($fecha,$id_empresa){
        try {
            $stm = $this->pdo->prepare("SELECT * FROM reserva r inner join cancha c on r.cancha_id=c.cancha_id inner join empresa e on e.empresa_id=c.empresa_id where r.reserva_fecha = ? and e.empresa_id = ?");
            $stm->execute([$fecha,$id_empresa]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }public function estadisticas_por_empresa($fecha_i,$fecha_f,$id_empresa){
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
    public function registrar_reserva($id_cancha,$tipopago,$pago_id,$nombre,$fecha,$hora,$pago1,$pago1_date,$pago2,$pago2_date,$estado,$microtime){
        try {
            $sql = 'insert into reserva(
                    cancha_id,
                    reserva_tipopago,
                    pago_id,
                    reserva_nombre,
                    reserva_fecha,
                    reserva_hora,
                    reserva_pago1,
                    reserva_pago1_date,
                    reserva_pago2,
                    reserva_pago2_date,
                    reserva_estado,
                    reserva_microtime
                    ) values(?,?,?,?,?,?,?,?,?,?,?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id_cancha,$tipopago,$pago_id,$nombre,$fecha,$hora,$pago1,$pago1_date,$pago2,$pago2_date,$estado,$microtime
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function registrar_pago($pago_id_user,$pago_equipo_id,$id_colaboracion,$pago_tipo,$pago_monto,$pago_comision,$pago_total,$pago_date,$pago_microtime){
        try {
            $sql = 'insert into pago(
                    id_user,
                    equipo_id,
                    id_colaboracion,
                    pago_tipo,
                    pago_monto,
                    pago_comision,
                    pago_total,
                    pago_date,
                    pago_estado,
                    pago_microtime
                    ) values(?,?,?,?,?,?,?,?,1,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $pago_id_user,$pago_equipo_id,$id_colaboracion,$pago_tipo,$pago_monto,$pago_comision,$pago_total,$pago_date,$pago_microtime
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function listar_pago_por_mt($microtime){
        try {
            $stm = $this->pdo->prepare("select * from pago where pago_microtime =?");
            $stm->execute([$microtime]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }public function listar_rating_empresa($id_empresa){
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
    public function listar_rating_empresa_agrupado($id_empresa){
        try {
            $stm = $this->pdo->prepare("select rating_empresa_valor,count(rating_empresa_valor) as conteo from rating_empresa where id_empresa =? group by rating_empresa_valor");
            $stm->execute([$id_empresa]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function listar_valoraciones($id_empresa){
        try {
            $stm = $this->pdo->prepare("select * from rating_empresa re inner join user u on re.id_usuario=u.id_user where re.id_empresa =?");
            $stm->execute([$id_empresa]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = [];
        }
        return $result;
    }
    public function pagar_restante($pago2,$fecha,$id){
        try {
            $sql = 'update reserva set reserva_pago2 = ?,reserva_pago2_date=?,reserva_estado=1 where reserva_id = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $pago2,$fecha,$id
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function obtener_saldo_actual($id){
        try {
            $sql = 'select * from user u inner join cuenta c on u.id_user=c.id_user where c.id_user=?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = $stm->fetch();
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }public function actualizar_colaboracion_estado_0($id){
        try {
            $sql = 'update colaboracion set colaboracion_estado = 0 where colaboracion_id = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function actualizar_detalle_colaboracion_estado_0($id){
        try {
            $sql = 'update detalle_colaboracion set detalle_colaboracion_estado = 0 where id_colaboracion = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }public function actualizar_valorar_empresa($usuario_id,$id_empresa,$valor,$comment){
        try {
            $sql = 'update rating_empresa set rating_empresa_valor = ?,rating_empresa_comentario=? where id_usuario = ? and id_empresa = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $valor,$comment,$usuario_id,$id_empresa
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
    public function valorar_empresa($usuario_id,$id_empresa,$valor,$comment){
        try {
            $sql = 'insert into rating_empresa(
                    id_usuario,
                    id_empresa,
                    rating_empresa_valor,
                    rating_empresa_comentario
                    ) values(?,?,?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $usuario_id,$id_empresa,$valor,$comment
            ]);
            $result = 1;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = 2;
        }
        return $result;
    }
}