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
	    $latitude = $jsonencode[0]->latitud;
        $longitude = $jsonencode[0]->longitud;

	    require_once("placeMapClass.php");
	    $mapPlaceObject = new MapPlace();
	    $mapPlaceObject->insert($id, $latitude, $longitude);
	}
?>
