<?php
	ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $id = $jsonencode[0]->id;

	    require_once("placeClass.php");
	    $placeObject = new Place();
	    $placeName = $placeObject->getName($id);
	    echo json_encode($placeName);
	}
?>
