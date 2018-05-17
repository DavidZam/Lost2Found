<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $id = $jsonencode[0]->id;

	    require_once("announceClass.php");
	    $announceObject = new Announce();
	    $placeId = $announceObject->getPlaceId($id);
	    echo json_encode($placeId);
	}
?>
