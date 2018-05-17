<?php
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
