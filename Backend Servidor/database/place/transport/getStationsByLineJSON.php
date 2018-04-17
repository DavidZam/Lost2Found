<?php
	ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);

	    require_once("placeTransportClass.php");
	    $transportPlaceObject = new TransportPlace();
	    $transportPlaceStations = $transportPlaceObject->selectStation($json);
	    $transportPlaceStations = array_column($transportPlaceStations, 'estacion');
	    $utfEncodedArray = array_map("utf8_encode", $transportPlaceStations);
	    echo json_encode($utfEncodedArray);
	}
?>
