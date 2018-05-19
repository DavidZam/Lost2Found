<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $msgText = $jsonencode[0]->msgText;
            $msgHour = $jsonencode[0]->msgHour;
	    $idChat = $jsonencode[0]->idChat;
	    $idUser = $jsonencode[0]->idUser;

	    require_once("chatClass.php");
	    $msgObject = new Chat();
	    $msgObject->insertMsg($msgText, $msgHour, $idChat, $idUser);
	}
?>
