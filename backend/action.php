<?php
if(isset($_POST['token'])){
    date_default_timezone_set('America/Lima');
    $fecha = date('Y-m-d');
    require_once 'core/Database.php';
    $pdo = Database::getConnection();
    $sql = 'insert into tokens values(NULL,?,?)';
    $stm = $pdo->prepare($sql);
    $result = $stm->execute([
        $_POST['token'],$fecha
    ]);
    if($result){
        echo "Saved";
    }else{
        echo "Failed";
    }
}
?>