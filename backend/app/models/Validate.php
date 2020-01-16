<?php
/**
 * Created by PhpStorm
 * User: CESARJOSE39
 * Date: 29/08/2019
 * Time: 13:39
 */
class Validate{
    private $pdo;
    private $log;

    public function __construct()
    {
        $this->pdo = Database::getConnection();
        $this->log = new Log();
    }
    //Validate if does not another nickname like this
    public function user($nickname){
        try{
            $sql = 'select * from user u where user_nickname = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$nickname]);
            $re = $stm->fetchAll();
            if(count($re) > 0){
                $result = true;
            } else {
                $result = false;
            }
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    public function send_email($destino,$titulo,$contenido){
        try{
            $headers = "From: Bufeo Tec Team <bufeotec@gmail.com>\n";
            $headers .= "MIME-Version: 1.0\n";
            $headers .= "Content-type: text/html; charset=utf-8\r\n";
            $mensajeF = "<h1>$titulo</h1><p>$contenido.</p><h5>Gracias por seguir confiando en nosotros.</h5><p>El equipo de <a href='https://www.bufeotec.com'>Bufeo Tec</a></p>";
            $result = mail($destino,$titulo,$mensajeF,$headers);
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    //Validate if does not another nickname like this if the user is editing his/her nickname
    public function nickname_by_id($id){
        try{
            $sql = 'select user_nickname from user where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $re = $stm->fetch();
            $result = $re->user_nickname;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = "";
        }
        return $result;
    }
    //Validate if does not another email like this if the user is editing his/her email
    public function email_by_id($id){
        try{
            $sql = 'select user_email from user where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $re = $stm->fetch();
            $result = $re->user_email;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = "";
        }
        return $result;
    }
    //Validate if does not another dni like this
    public function dni($dni){
        try{
            $sql = 'select * from person where person_dni = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$dni]);
            $re = $stm->fetchAll();
            if(count($re) > 0){
                $result = true;
            } else {
                $result = false;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    //Validate if does not another email like this
    public function email($email){
        try{
            $sql = 'select * from user where user_email = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$email]);
            $re = $stm->fetchAll();
            if(count($re) > 0){
                $result = true;
            } else {
                $result = false;
            }

        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = false;
        }
        return $result;
    }
    //Get Password of User
    public function password($id){
        try{
            $sql = 'select user_password from user where id_user = ?';
            $stm = $this->pdo->prepare($sql);
            $stm->execute([$id]);
            $re = $stm->fetch();
            $result = $re->user_password;
        } catch (Exception $e){
            $this->log->insert($e->getMessage(), get_class($this).'|'.__FUNCTION__);
            $result = "";
        }
        return $result;
    }
    function alerta_semaforo_fecha($date_1){
        $date1 = new DateTime($date_1);
        $date2 = new DateTime("now");
        $diff = $date1->diff($date2);
        if($date1<$date2){
            $color = "#FF1700";
        }else{
            if($diff->y !==0){
                $color=($diff->y > 1) ? '#34D34A' : '#34D34A';
            }elseif ($diff->m !==0){
                $color=($diff->m > 1) ? '#34D34A' : '#F3F70A';
            }elseif ($diff->d !==0){
                $color=($diff->d > 1) ? '#F3F70A' : '#F3F70A';
            }elseif ($diff->h !==0){
                $color=($diff->h > 1) ? '#F3F70A' : '#F3F70A';
            }elseif ($diff->i!==0){
                $color = '#F3F70A';
            }else{
                $color = "#FF1700";
            }
        }
        return $color;
    }
    function alerta_semaforo_peso($peso){
        if($peso>=150000){
            $color='#34D34A';//verde
        }elseif ($peso>=145000){
            $color = '#F3F70A';//amarillo
        }else{
            $color='#FF1700';//rojo
        }
        return $color;
    }
    function get_day_nominal($day){
        switch ($day){
            case 1:
                $d = "Lunes";
                break;
            case 2:
                $d = "Martes";
                break;
            case 3:
                $d = "Miércoles";
                break;
            case 4:
                $d = "Jueves";
                break;
            case 5:
                $d = "Viernes";
                break;
            case 6:
                $d = "Sábado";
                break;
            case 7:
                $d = "Domingo";
                break;
        }
        return $d;
    }
    function get_date_nominal($date,$format_given,$format_return,$language){
        $day="01";$mes_="Ene";$anho="1000";
        $final_date = $day." ".$mes_." ".$anho;
        if($format_given == "DateTime"){
            if($format_return=="Date"){
                $date_explode = explode('-',$date);
                $anho = $date_explode[0];
                $mes = $date_explode[1];
                if($language=="es"){
                    switch ($mes){
                        case '01':
                            $mes_ = "Ene";
                            break;
                        case '02':
                            $mes_ = "Feb";
                            break;
                        case '03':
                            $mes_ = "Mar";
                            break;
                        case '04':
                            $mes_ = "Abr";
                            break;
                        case '05':
                            $mes_ = "May";
                            break;
                        case '06':
                            $mes_ = "Jun";
                            break;
                        case '07':
                            $mes_ = "Jul";
                            break;
                        case '08':
                            $mes_ = "Ago";
                            break;
                        case '09':
                            $mes_ = "Set";
                            break;
                        case '10':
                            $mes_ = "Oct";
                            break;
                        case '11':
                            $mes_ = "Nov";
                            break;
                        case '12':
                            $mes_ = "Dic";
                            break;
                    }
                }
                $day = explode(' ',$date_explode[2]);
                $final_date = $day[0]." ".$mes_." ".$anho ;
            }elseif($format_return=="DateTime"){
                $date_explode = explode('-',$date);
                $anho = $date_explode[0];
                $mes = $date_explode[1];
                if($language=="es"){
                    switch ($mes){
                        case '01':
                            $mes_ = "Ene";
                            break;
                        case '02':
                            $mes_ = "Feb";
                            break;
                        case '03':
                            $mes_ = "Mar";
                            break;
                        case '04':
                            $mes_ = "Abr";
                            break;
                        case '05':
                            $mes_ = "May";
                            break;
                        case '06':
                            $mes_ = "Jun";
                            break;
                        case '07':
                            $mes_ = "Jul";
                            break;
                        case '08':
                            $mes_ = "Ago";
                            break;
                        case '09':
                            $mes_ = "Set";
                            break;
                        case '10':
                            $mes_ = "Oct";
                            break;
                        case '11':
                            $mes_ = "Nov";
                            break;
                        case '12':
                            $mes_ = "Dic";
                            break;
                    }
                }
                $day = explode(' ',$date_explode[2]);
                $time_explode = explode(':',$day[1]);
                ($time_explode[0]>12) ? $hour = $time_explode[0] - 12 : $hour = $time_explode[0];
                ($time_explode[0]>=12) ? $meridian = "pm" : $meridian = "am";
                $minute =$time_explode[1];
                $final_date = $day[0]." ".$mes_." ".$anho.", ". $hour . ":".$minute." ".$meridian ;
            }
        }elseif($format_given == "Date"){
            if($format_return=="Date"){
                $date_explode = explode('-',$date);
                $anho = $date_explode[0];
                $mes = $date_explode[1];
                if($language=="es"){
                    switch ($mes){
                        case '01':
                            $mes_ = "Ene";
                            break;
                        case '02':
                            $mes_ = "Feb";
                            break;
                        case '03':
                            $mes_ = "Mar";
                            break;
                        case '04':
                            $mes_ = "Abr";
                            break;
                        case '05':
                            $mes_ = "May";
                            break;
                        case '06':
                            $mes_ = "Jun";
                            break;
                        case '07':
                            $mes_ = "Jul";
                            break;
                        case '08':
                            $mes_ = "Ago";
                            break;
                        case '09':
                            $mes_ = "Set";
                            break;
                        case '10':
                            $mes_ = "Oct";
                            break;
                        case '11':
                            $mes_ = "Nov";
                            break;
                        case '12':
                            $mes_ = "Dic";
                            break;
                    }
                }
                $day = explode(' ',$date_explode[2]);
                $final_date = $day[0]." ".$mes_." ".$anho ;
            }
        }
        return $final_date;
    }
    function time_between_dates($date_1, $date_2){
        try{
            $date1 = new DateTime($date_1);
            if ($date_2 == "hoy") {
                $date2 = new DateTime("now");
            } else {
                $date2 = new DateTime($date_2);
            }
            $diff = $date1->diff($date2);
            if ($diff->y !== 0) {
                $time = ($diff->y > 1) ? $diff->y . ' años ' : $diff->y . ' año ';
            } elseif ($diff->m !== 0) {
                $time = ($diff->m > 1) ? $diff->m . ' meses ' : $diff->m . ' mes ';
            } elseif ($diff->d !== 0) {
                $time = ($diff->d > 1) ? $diff->d . ' días ' : $diff->d . ' día ';
            } elseif ($diff->h !== 0) {
                $time = ($diff->h > 1) ? $diff->h . ' horas ' : $diff->h . ' hora ';
            } elseif ($diff->i !== 0) {
                $time = (($diff->days * 24) * 60) + ($diff->i) . ' minutos';
            } else {
                $time = "0 min";
            }
        } catch (Exception $e){
            $time = "no disponible";
        }

        return $time;
    }

}