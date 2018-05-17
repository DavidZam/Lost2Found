<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

		$idUser = $jsonencode[0]->id;
	    $categoria = $jsonencode[0]->nombreTabla;
		$tipo = $jsonencode[0]->tipoAnuncio;
		$dia = $jsonencode[0]->diaAnuncio;
		$determinante = $jsonencode[0]->param;

	    require_once("announceClass.php");
	    $announceObject = new Announce();
	    $numAnnounces = $announceObject->getNumberMatchAnnounces($idUser, $categoria, $tipo, $dia, $determinante);
	    echo $numAnnounces;
	}
?>
