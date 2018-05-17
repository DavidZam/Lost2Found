<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $idUser1 = $jsonencode[0]->idUser1;
	    $idUser2 = $jsonencode[0]->idUser2;

	    require_once("chatClass.php");
	    $chatObject = new Chat();
	    $chat = $chatObject->select($idUser1, $idUser2);
	    echo json_encode($chat);
	}
?>
