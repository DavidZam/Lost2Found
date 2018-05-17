<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $categoria = $jsonencode[0]->nombreTabla;
		$tipo = $jsonencode[0]->tipoAnuncio;

	    require_once("announceClass.php");

	    $announceObject = new Announce();
	    $announceListObject = $announceObject->getAnnouncesSeeker($categoria, $tipo);
	    
	    echo json_encode($announceListObject, JSON_UNESCAPED_SLASHES);
	}
?>
