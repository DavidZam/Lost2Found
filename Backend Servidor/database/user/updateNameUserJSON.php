<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);
	    
	    $nombre<?php
	    require_once("userClass.php");
	    $userObject = new User();
	    $user = $userObject->updateName($nombre);
	    echo json_encode($user);
	}
?>
