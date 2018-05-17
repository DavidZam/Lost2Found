<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    require_once("typeObjectClass.php");
	    $typeObject = new typeObject();
	    $objectId = $typeObject->getId();
	    echo json_encode($objectId);
	}
?>
