<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $email = $jsonencode[0]->email;
	    $nombre = $jsonencode[0]->nombre;

	    require_once("userClass.php");
	    $userObject = new User();
	    $user = $userObject->updateName($nombre, $email);
	    echo json_encode($user);
	}
?>
