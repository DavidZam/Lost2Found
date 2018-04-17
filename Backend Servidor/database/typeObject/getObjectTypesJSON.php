<?php
        ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);

	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    require_once("typeObjectClass.php");
	    $typeObject = new typeObject();
	    $typeObjectArray = $typeObject->select();
	    $typeObjectArray = array_column($typeObjectArray, 'nombreTabla');
	    $utfEncodedArray = array_map("utf8_encode", $typeObjectArray);
	    echo json_encode($utfEncodedArray);
	}
?>
