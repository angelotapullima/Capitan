<?php
define('SERVER_API_KEY','AIzaSyBt7FTpK-IPbVsC-A2xnN2s0ut3Du2wQHE');
require_once 'core/Database.php';
$pdo = Database::getConnection();
$sql = 'SELECT * FROM tokens';
$stm = $pdo->prepare($sql);
$stm->execute();
$tokens = $stm->fetchAll(PDO::FETCH_ASSOC);
foreach ($tokens as $token){
    $registrationIds[] = $token['token'];
}
$tokens=['dKQtxgDYOG4:APA91bGVw1g3jdiYc6hd97AnAXh5oODrvWcUfgweTXpnP3I5Fjfiy2R9uOyUw4yDBKQhK207VEaOfbzQNyB8S0GSIJSF8bdy49dNKlfwXiiA4VOcDAsU1WLfrdSukgdaW8l_dInLbrIC'];
$header = [
    'Authorization: Key=' . SERVER_API_KEY,
    'Content-Type: Application/json'
];
$msg = [
  'title' => 'Probando Notificacion',
  'body' => 'Este es el cuerpo del mensaje',
  'icon' => 'media/user/default.png',
  'image' => 'media/user/default.png',
];
$payload = [
    'registration_ids' => $registrationIds,
    'data' => $msg
];
$curl = curl_init();
curl_setopt_array($curl, array(
    CURLOPT_URL => "https://fcm.googleapis.com/fcm/send",
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_CUSTOMREQUEST => "POST",
    CURLOPT_POSTFIELDS => json_encode($payload),
    CURLOPT_HTTPHEADER => $header
));
$response = curl_exec($curl);
$err = curl_error($curl);
curl_close($curl);
if ($err) {
    echo "cURL Error #:" . $err;
} else {
    echo $response;
}
?>