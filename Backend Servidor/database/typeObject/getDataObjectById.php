<?php
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
