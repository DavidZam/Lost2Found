<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

		$placeId = $jsonencode[0]->placeId;
		$tipoTte = $jsonencode[0]->tipoTte;
		$linea = $jsonencode[0]->linea;
		$station = $jsonencode[0]->estacion;

	    require_once("placeTransportClass.php");
	    $transportPlaceObject = new TransportPlace();
	    $transportPlaceObject->insert($placeId, $tipoTte, $linea, $station);
	}
?>
