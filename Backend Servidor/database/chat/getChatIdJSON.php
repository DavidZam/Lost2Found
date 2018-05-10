<?php
	ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);

	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $chatTitle = $jsonencode[0]->nombreChat;
	    $idUser1 = $jsonencode[0]->idUser1;
	    $idUser2 = $jsonencode[0]->idUser2;

	    require_once("chatClass.php");
	    $chatObject = new Chat();
	    $chatId = $chatObject->selectId($chatTitle, $idUser1, $idUser2);
	    echo json_encode($chatId);
	}
?>
