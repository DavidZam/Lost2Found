<?php
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);

	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $category = $jsonencode[0]->category;

	    require_once("typeObjectClass.php");
	    $typeObject = new typeObject();
	    $category = $typeObject->getCategory($category);
	    echo json_encode($category);
	}
?>
