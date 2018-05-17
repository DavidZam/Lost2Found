<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $line = $jsonencode[0]->lineText;
        $station = $jsonencode[0]->stationText;
        $placeId = $jsonencode[0]->idLugar;

	    require_once("placeTransportClass.php");
	    $transportPlaceObject = new TransportPlace();
	    $transportPlaceObject->insert($line, $station, $placeId);
	}
?>
