<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $announceType = $jsonencode[0]->announceType;
            $currentTime = $jsonencode[0]->currentTime;
	    $announceDateText = $jsonencode[0]->announceDateText;
	    $announceHourText = $jsonencode[0]->announceHourText;
            $model = $jsonencode[0]->model;
	    $brand = $jsonencode[0]->brand;
	    $color = $jsonencode[0]->color;
	    $idUser = $jsonencode[0]->idUser;
            $idPlace = $jsonencode[0]->idPlace;
            $announceCategorie = $jsonencode[0]->announceCategorie;
	
	    require_once("announceClass.php");
	    $announceObject = new Announce();
	    $announceObject->insert($announceType, $currentTime, $announceDateText, $announceHourText, $model, $brand, $color, $idUser, $idPlace, $announceCategorie);
	}
?>
