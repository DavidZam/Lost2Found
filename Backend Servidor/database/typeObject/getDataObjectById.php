<?php
	ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $id = $jsonencode[0]->id;
	    $announceCategorie = $jsonencode[0]->announceCategorie;

	    require_once("typeObjectClass.php");
	    $typeObject = new typeObject();
	    $objectData = $typeObject->getData($id, $announceCategorie);
            echo json_encode($objectData, JSON_UNESCAPED_UNICODE);
	}
?>
