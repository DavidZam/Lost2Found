<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $userId = $jsonencode[0]->id;

	    require_once("chatClass.php");
	    $chatObject = new Chat();
	    $numChats = $chatObject->getNumberChats($userId);
	    echo $numChats;
	}
?>
