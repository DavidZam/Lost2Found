<?php
        ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);

	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    /*$typeAnnounce = $jsonencode[0]->tipoAnuncio;
        $currentTime = $jsonencode[0]->currentTime;
        $announceDay = $jsonencode[0]->diaAnuncio;
        $announceHour = $jsonencode[0]->horaPerdidaHallazgo;
        $color = $jsonencode[0]->color;
        $categorie = $jsonencode[0]->categorie;*/

	    require_once("typeObjectClass.php");
	    $typeObject = new typeObject();
	    $objectId = $typeObject->getId(); // $typeAnnounce, $currentTime, $announceDay, $announceHour, $color, $categorie
	    echo json_encode($objectId);
	}
?>
