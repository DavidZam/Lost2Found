<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $textMsg = $jsonencode[0]->textMsg;
	    $hourMsg = $jsonencode[0]->hourMsg;

	    require_once("chatClass.php");
	    $chatObject = new Chat();
	    $userId = $chatObject->getUserId($textMsg, $hourMsg);
	    echo json_encode($userId);
	}
?>
