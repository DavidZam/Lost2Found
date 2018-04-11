<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $email = $jsonencode[0]->email;

	    require_once("userClass.php");
	    $userObject = new User();
	    $userId = $userObject->getId($email);
	    echo json_encode($userId);
	}
?>
