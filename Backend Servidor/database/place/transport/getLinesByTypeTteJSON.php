<?php
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
