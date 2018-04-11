<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    require_once("placeClass.php");
	    $placeObject = new Place();
	    $placeObject->insert();
	}
?>
