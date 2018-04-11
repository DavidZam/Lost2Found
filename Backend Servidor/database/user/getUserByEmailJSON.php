<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);
 
	    $email = $jsonencode[0]->email;
            $password = $jsonencode[0]->contrasena;

	    require_once("userClass.php");
	    $userObject = new User();
	    $user = $userObject->select($email, $password);
	    echo json_encode($user);
	}
?>
