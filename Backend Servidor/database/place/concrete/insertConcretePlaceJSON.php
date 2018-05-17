<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $id = $jsonencode[0]->id;
	    $street = $jsonencode[0]->street;
        $number = $jsonencode[0]->number;
        $postalCode = $jsonencode[0]->postalcode;

	    require_once("placeConcreteClass.php");
	    $concretePlaceObject = new ConcretePlace();
	    $concretePlaceObject->insert($id, $street, $number, $postalCode);
	}
?>
