<?php
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $idUser = $jsonencode[0]->id;

	    require_once("announceClass.php");

	    $announceObject = new Announce();
	    $announceListObject = $announceObject->getAnnounces($idUser);
	    //echo json_encode($announceListObject);
	    //$announceListObject = array_column($announceListObject);
	    //$utfEncodedArray = array_map("utf8_encode", $transportPlaceLines);
	    echo json_encode($announceListObject);
	}
?>
