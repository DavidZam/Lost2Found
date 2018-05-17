<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $idUser = $jsonencode[0]->id;

	    require_once("announceClass.php");

	    $announceObject = new Announce();
	    $announceListObject = $announceObject->getAnnounces($idUser);
	    echo json_encode($announceListObject, JSON_UNESCAPED_SLASHES);
	}
?>
