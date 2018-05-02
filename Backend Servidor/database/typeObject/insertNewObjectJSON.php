<?php
	ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);

	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $objectId = $jsonencode[0]->objectId;
            $categorie = $jsonencode[0]->categorie;
	    $param1 = $jsonencode[0]->param1;
	    $param2 = $jsonencode[0]->param2;
            $param3 = $jsonencode[0]->param3;

	    require_once("typeObjectClass.php");
	    $newObjectObject = new typeObject();
	    $newObjectObject->insert($objectId, $categorie, $param1, $param2, $param3);
	}
?>
