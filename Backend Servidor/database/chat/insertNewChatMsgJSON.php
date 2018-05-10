<?php
	ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);

	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $msgText = $jsonencode[0]->msgText;
        $msgHour = $jsonencode[0]->msgHour;
	    $msgRead = $jsonencode[0]->msgRead;
	    $idChat = $jsonencode[0]->idChat;
	    $idUser = $jsonencode[0]->idUser;

	    require_once("chatClass.php");
	    $msgObject = new Chat();
	    $msgObject->insertMsg($msgText, $msgHour, $msgRead, $idChat, $idUser);
	}
?>
