<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $line = $jsonencode[0]->lineText;
        $station = $jsonencode[0]->stationText;

	    require_once("placeTransportClass.php");
	    $transportPlace = new TransportPlace();
	    $transportPlaceObject= $transportPlace->selectId($line, $station);
	    echo json_encode($transportPlaceObject);
	}
?>
