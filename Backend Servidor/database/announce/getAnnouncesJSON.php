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
	    //var_dump($announceListObject);
	    //echo $announceListObject;
	    /*foreach($announceListObject as $key => $value) {
		$announceListObject[$key] = utf8_encode((string)$value);
	    }*/
	    //$utfEncodedArray = array_map("utf8_encode", $announceListObject);
	    //$result = json_encode($announceListObject, JSON_UNESCAPED_UNICODE);
	    echo json_encode($announceListObject, JSON_UNESCAPED_SLASHES);
	}
?>
