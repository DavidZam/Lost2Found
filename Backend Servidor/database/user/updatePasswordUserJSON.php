<?php
	ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);

	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $email = $jsonencode[0]->email;
	    $nombre = $jsonencode[0]->nombre;
	    $pass = $jsonencode[0]->contrasena;

	    require_once("userClass.php");
	    $userObject = new User();
	    $user = $userObject->updatePassword($pass, $email);
	    json_encode($user);
	}
?>