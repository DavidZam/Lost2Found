<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $idChat = $jsonencode[0]->id;

	    require_once("chatClass.php");

	    $chatObject = new Chat();
	    $msgListObject = $chatObject->getMsgs($idChat);
	    echo json_encode($msgListObject, JSON_UNESCAPED_UNICODE);
	}
?>
