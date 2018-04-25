<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $id = $jsonencode[0]->id;

	    require_once("userClass.php");
	    $userObject = new User();
	    $userName = $userObject->getName($id);
	    echo json_encode($userName);
	}
?>
