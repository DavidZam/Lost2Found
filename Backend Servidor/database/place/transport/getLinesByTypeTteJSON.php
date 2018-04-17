<?php
        ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $tipoTte = $jsonencode[0]->tipoTte;

	    require_once("placeTransportClass.php");

	    $transportPlaceObject = new TransportPlace();
	    $transportPlaceLines = $transportPlaceObject->select($tipoTte);
	    $transportPlaceLines = array_column($transportPlaceLines, 'linea');
	    $utfEncodedArray = array_map("utf8_encode", $transportPlaceLines);
	    echo json_encode($utfEncodedArray);
	}
?>
