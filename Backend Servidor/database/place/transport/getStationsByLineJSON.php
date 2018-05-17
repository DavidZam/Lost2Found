<?php
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
