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
	    $categoria = $jsonencode[0]->nombreTabla;
		$tipo = $jsonencode[0]->tipoAnuncio;
		$dia = $jsonencode[0]->diaAnuncio;
		$idObjeto = $jsonencode[0]->idObjeto;

	    require_once("announceClass.php");
	    $announceObject = new Announce();
	    $numAnnounces = $announceObject->getNumberMatchAnnounces($idUser, $categoria, $tipo, $dia, $idObjeto);
	    echo $numAnnounces;
	}
?>
