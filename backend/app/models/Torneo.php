<?php
use Exception;
require_once 'core/Database.php';
class Torneo{
    private $pdo;
    public function __construct(){
        $this->pdo = Database::getConnection();
    }
    public function registrar_equipo($usuario_id,$nombre,$foto,$join){
        $result = 2;
        try {
            $sql = 'insert into equipo(
                    usuario_id,
                    equipo_nombre,
                    equipo_foto,
                    equipo_valoracion,
                    equipo_join,
                    equipo_estado
                    ) values(?,?,?,0,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $usuario_id,$nombre,$foto,$join
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function registrar_torneo($usuario_id,$nombre,$descripcion,$fecha,$hora,$lugar,$organizador,$costo,$tipo,$file_path){
        $result = 2;
        try {
            $fecha_hora= date('Y-m-d H:i:s');
            $sql = 'insert into torneo(
                    usuario_id,
                    torneo_nombre,
                    torneo_descripcion,
                    torneo_fecha,
                    torneo_hora,
                    torneo_lugar,
                    torneo_organizador,
                    torneo_costo,
                    torneo_fechahora,
                    torneo_tipo,
                    torneo_imagen,
                    torneo_estado
                    ) values(?,?,?,?,?,?,?,?,?,?,?,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $usuario_id,$nombre,$descripcion,$fecha,$hora,$lugar,$organizador,$costo,$fecha_hora,$tipo,$file_path
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function registrar_grupo($id_torneo,$grupo_nombre){
        $result = 2;
        try {
            $sql = 'insert into torneo_grupo(id_torneo,grupo_nombre) values(?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id_torneo,$grupo_nombre
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function registrar_instancia($id_torneo,$instancia_nombre,$instancia_tipo){
        $result = 2;
        try {
            $sql = 'insert into torneo_instancia(torneo_id,torneo_instancia_nombre,torneo_instancia_tipo) values(?,?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id_torneo,$instancia_nombre,$instancia_tipo
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function registrar_equipo_usuario($equipo,$usuario_id){
        $result = 2;
        try {
            $sql = 'insert into equipo_usuario(
                    equipo_id,
                    usuario_id
                    ) values(?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $equipo,$usuario_id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function registrar_partido($id_torneo_instancia,$id_equipo_local,$id_equipo_visita,$fecha,$hora){
        $result = 2;
        try {
            $sql = 'insert into torneo_partido(
                    id_torneo_instancia,
                    id_equipo_local,
                    id_equipo_visita,
                    marcador_local,
                    marcador_visita,
                    torneo_partido_fecha,
                    torneo_partido_hora,
                    torneo_partido_estado
                    ) values(?,?,?,0,0,?,?,0)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $id_torneo_instancia,$id_equipo_local,$id_equipo_visita,$fecha,$hora
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function crear_estadistica($equipo,$temporada,$semana){
        $result = 2;
        try {
            $sql = 'insert into estadisticas(
                    equipo_id,
                    temporada,
                    semana,
                    puntaje_acumulado,
                    puntaje_semanal,
                    retos_enviados,
                    retos_recibidos,
                    retos_ganados,
                    retos_empatados,
                    retos_perdidos,
                    torneos
                    ) values(?,?,?,0,0,0,0,0,0,0,0)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $equipo,$temporada,$semana
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function cambiar_puntajes($equipo,$p_a,$p_s){
        $result = 2;
        try {
            $sql = 'update estadisticas set puntaje_acumulado = ?, puntaje_semanal =? where equipo_id =?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $p_a,$p_s,$equipo
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function sumar_estadistica($equipo,$criterio,$valor){
        $result = 2;
        try {
            switch ($criterio){
                case 'retos_enviados':
                    $sql = 'update estadisticas set retos_enviados = ? where equipo_id = ? ';
                    break;
                case 'retos_recibidos':
                    $sql = 'update estadisticas set retos_recibidos = ? where equipo_id = ? ';
                    break;
                case 'retos_ganados':
                    $sql = 'update estadisticas set retos_ganados = ? where equipo_id = ? ';
                    break;
                case 'retos_empatados':
                    $sql = 'update estadisticas set retos_empatados = ? where equipo_id = ? ';
                    break;
                case 'retos_perdidos':
                    $sql = 'update estadisticas set retos_perdidos = ? where equipo_id = ? ';
                    break;
                case 'torneos':
                    $sql = 'update estadisticas set torneos = ? where equipo_id = ? ';
                    break;
                default:
                    $sql = "";
                    break;
            }
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $valor,$equipo
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function retar_equipo($retador_id,$retado_id,$fecha,$hora,$lugar){
        $result = 2;
        try {
            $sql = 'insert into reto(
                    retador_id,
                    retado_id,
                    reto_fecha,
                    reto_hora,
                    reto_lugar,
                    reto_respuesta,
                    ganador_id,
                    ganador_estado,
                    reto_estado
                    ) values(?,?,?,?,?,0,0,0,1)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $retador_id,$retado_id,$fecha,$hora,$lugar
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function responder_reto($respuesta,$id){
        $result = 2;
        try {
            $sql = 'update reto set reto_respuesta = ? where reto_id =?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $respuesta,$id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function registrar_equipo_en_torneo($torneo_id,$equipo_id){
        $result = 2;
        try {
            $sql = 'insert into torneo_equipo(id_torneo_grupo,equipo_id) values(?,?)';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $torneo_id,$equipo_id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function dar_resultado($ganador_id,$reto_id){
        $result = 2;
        try {
            $sql = 'update reto set ganador_id = ? where reto_id =?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $ganador_id,$reto_id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function cambiar_ganador_estado($estado,$reto_id){
        $result = 2;
        try {
            $sql = 'update reto set ganador_estado = ? where reto_id =?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([
                $estado,$reto_id
            ]);
            $result = 1;
        } catch (Exception $e){
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function listar_equipos_por_id_usuario($id_usuario){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id where eu.usuario_id = ? and e.equipo_estado = 1");
            $stm->execute([$id_usuario]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_estadisticas(){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from equipo e inner join estadisticas et on e.equipo_id = et.equipo_id where e.equipo_estado = 1");
            $stm->execute([]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_equipos(){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from equipo e inner join usuario u on e.usuario_id=u.usuario_id where e.equipo_estado = 1");
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_equipos_por_torneo($id_torneo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from equipo e inner join torneo_equipo te on e.equipo_id = te.equipo_id inner join torneo_grupo tg on tg.id_torneo_grupo=te.id_torneo_grupo inner join torneo t on t.torneo_id=tg.id_torneo inner join usuario u on e.usuario_id = u.usuario_id where t.torneo_id = ? and e.equipo_estado = 1");
            $stm->execute([$id_torneo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_equipos_por_torneo_not($id_torneo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("SELECT * FROM torneo_equipo te inner join equipo e on e.equipo_id=te.equipo_id inner join usuario u on e.usuario_id=u.usuario_id where te.equipo_id NOT IN (SELECT te.equipo_id from torneo_equipo te WHERE te.torneo_id = ?) and e.equipo_estado = 1 GROUP by te.equipo_id");
            $stm->execute([$id_torneo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_usuario_en_equipo($usuario_id,$equipo_id){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id inner join usuario u on e.usuario_id=u.usuario_id where eu.usuario_id = ? and eu.equipo_id=? and e.equipo_estado = 1");
            $stm->execute([$usuario_id,$equipo_id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_reto_por_id($reto_id){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from reto where reto_id = ? limit 1");
            $stm->execute([$reto_id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }public function listar_torneo_por_id($torneo_id){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from torneo where torneo_id = ?");
            $stm->execute([$torneo_id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_torneos(){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from torneo t inner join usuario u on t.usuario_id=u.usuario_id where t.torneo_estado = 1");
            $stm->execute();
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_mis_retos($id_usuario){
        $result = [];
        try {
            $stm = $this->pdo->prepare("SELECT r.reto_id, 
	(SELECT e.equipo_nombre FROM equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id WHERE e.equipo_id=r.retador_id and eu.usuario_id=?) as nombre_1,
    (SELECT e.equipo_id FROM equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id WHERE e.equipo_id=r.retador_id and eu.usuario_id=?) as equipo_id_1,
    (SELECT e.equipo_foto FROM equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id WHERE e.equipo_id=r.retador_id and eu.usuario_id=?) as foto_1,
    (SELECT e.equipo_nombre FROM equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id WHERE e.equipo_id=r.retado_id and eu.usuario_id=?) as nombre_2,
    (SELECT e.equipo_id FROM equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id WHERE e.equipo_id=r.retado_id and eu.usuario_id=?) as equipo_id_2,
    (SELECT e.equipo_foto FROM equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id WHERE e.equipo_id=r.retado_id and eu.usuario_id=?) as foto_2,
    r.retador_id,r.retado_id,r.reto_fecha,r.reto_hora,r.reto_lugar,r.reto_estado,r.reto_respuesta,r.ganador_id,r.ganador_estado 
    FROM reto r");
            $stm->execute([$id_usuario,$id_usuario,$id_usuario,$id_usuario,$id_usuario,$id_usuario]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_equipos_por_id_usuario_not($id_usuario){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from equipo e inner join equipo_usuario eu on e.equipo_id = eu.equipo_id inner join usuario u on eu.usuario_id=u.usuario_id where eu.usuario_id <> ? and e.usuario_id<> ? and e.equipo_estado = 1 group by eu.equipo_id order by e.equipo_valoracion");
            $stm->execute([$id_usuario,$id_usuario]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_detalle_equipo($id_equipo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from equipo_usuario eu inner join usuario u on eu.usuario_id = u.usuario_id where eu.equipo_id = ?");
            $stm->execute([$id_equipo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function buscar_jugadores_nuevos($id_equipo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("SELECT * FROM usuario u where u.usuario_id not in (SELECT us.usuario_id FROM usuario us inner join equipo_usuario eu on eu.usuario_id=us.usuario_id WHERE eu.equipo_id=? ) and u.usuario_estado = 1");
            $stm->execute([$id_equipo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_detalle_empresa($id_empresa){
        $result = new Empresa;
        try {
            $stm = $this->pdo->prepare("select * from empresa e inner join usuario u on e.usuario_id = u.usuario_id inner join ubigeo ub on ub.ubigeo_id = e.ubigeo_id where e.empresa_id =?");
            $stm->execute([$id_empresa]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_equipo_por_id($id_equipo){
        $result = new Torneo;
        try {
            $stm = $this->pdo->prepare("select * from equipo e inner join usuario u on e.usuario_id = u.usuario_id where e.equipo_id =?");
            $stm->execute([$id_equipo]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function existe_reto_pendiente($retador_id,$retado_id){
        $result = new Torneo;
        try {
            $stm = $this->pdo->prepare("select * from reto where retador_id = ? and retado_id =? and reto_estado = 1 or retador_id = ? and retado_id =? and reto_estado = 1 limit 1");
            $stm->execute([$retador_id,$retado_id,$retado_id,$retador_id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function registrar_cancha($empresa_id,$nombre,$dimensiones,$precioD,$precioN,$foto){
        $result = 2;
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
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function listar_reservados_por_cancha_por_fecha($id_cancha,$fecha){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from reserva where cancha_id =? and reserva_fecha=?");
            $stm->execute([$id_cancha,$fecha]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_canchas_por_id_empresa($id_empresa){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from cancha where empresa_id =? and cancha_estado = 1");
            $stm->execute([$id_empresa]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function registrar_reserva($id_cancha,$nombre,$fecha,$hora,$costo){
        $result = 2;
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
            //throw new Exception($e->getMessage());
        }
        return $result;
    }
    public function obtener_ultimo_equipo_id(){
        $result = new Torneo;
        try {
            $stm = $this->pdo->prepare('select * from equipo order by equipo_id desc limit 1');
            $stm->execute();
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_estadisticas_por_id_equipo($equipo_id){
        $result = new Torneo;
        try {
            $stm = $this->pdo->prepare('select * from estadisticas where equipo_id =?');
            $stm->execute([$equipo_id]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_publicaciones($id_torneo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 and publicaciones_id_torneo = ? order by publicaciones_id desc");
            $stm->execute([$id_torneo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_ultima_publicacion($id_torneo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 and publicaciones_id_torneo = ? order by publicaciones_id desc limit 1");
            $stm->execute([$id_torneo]);
            $result = $stm->fetch();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_publicaciones_limite($id_torneo,$limite_inf){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 and publicaciones_id < ? and publicaciones_id_torneo = ? order by publicaciones_id desc limit 10");
            $stm->execute([$id_torneo,$limite_inf]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_publicaciones_limite_sup($id_torneo,$limite_sup){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from publicaciones p inner join usuario u on u.usuario_id=p.usuario_id where p.publicaciones_estado=1 and publicaciones_id > ? and publicaciones_id_torneo = ? order by publicaciones_id desc");
            $stm->execute([$id_torneo,$limite_sup]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_grupos_por_id_torneo($id_torneo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from torneo_grupo where id_torneo = ? order by id_torneo_grupo asc");
            $stm->execute([$id_torneo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_instancias_por_id_torneo($id_torneo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from torneo_instancia where torneo_id = ? order by id_torneo_instancia desc");
            $stm->execute([$id_torneo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_equipos_por_id_grupo($id_grupo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select * from torneo_equipo te inner join equipo e on te.equipo_id=e.equipo_id where id_torneo_grupo = ?");
            $stm->execute([$id_grupo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_partidos_por_id_instancia($id_instancia){
        $result = [];
        try {
            $stm = $this->pdo->prepare("select tp.*,(select e.equipo_nombre from equipo e where e.equipo_id=tp.id_equipo_local) as nombre_local,(select e.equipo_foto from equipo e where e.equipo_id=tp.id_equipo_local) as foto_local,(select e.equipo_nombre from equipo e where e.equipo_id=tp.id_equipo_visita) as nombre_visita,(select e.equipo_foto from equipo e where e.equipo_id=tp.id_equipo_visita) as foto_visita from torneo_partido tp where id_torneo_instancia = ?");
            $stm->execute([$id_instancia]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
    public function listar_partidos_terminados_equipo_fase1($id_equipo){
        $result = [];
        try {
            $stm = $this->pdo->prepare("SELECT * from torneo_partido tp inner join torneo_instancia ti on tp.id_torneo_instancia=ti.id_torneo_instancia where tp.id_equipo_local=? and ti.torneo_instancia_tipo=1 and tp.torneo_partido_estado = 1 or tp.id_equipo_visita=? and ti.torneo_instancia_tipo=1  and tp.torneo_partido_estado = 1");
            $stm->execute([$id_equipo,$id_equipo]);
            $result = $stm->fetchAll();
        } catch (Exception $e){
        }
        return $result;
    }
}