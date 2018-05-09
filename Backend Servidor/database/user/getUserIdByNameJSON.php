<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $name = $jsonencode[0]->name;

	    require_once("userClass.php");
	    $userObject = new User();
	    $userId = $userObject->getIdByName($name);
	    echo json_encode($userId);
	}
?>
