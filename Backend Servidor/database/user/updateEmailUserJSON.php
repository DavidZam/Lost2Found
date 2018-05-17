<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $email = $jsonencode[0]->email;
	    $id = $jsonencode[0]->id;

	    require_once("userClass.php");

	    $userObject = new User();
	    $user = $userObject->updateEmail($email, $id);
	    json_encode($user);
	}
?>
