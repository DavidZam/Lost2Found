<?php
	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

	    $idAnnounce = $jsonencode[0]->idAnuncio;
	    $announceCategory = $jsonencode[0]->categoria;
	
	    require_once("announceClass.php");
	    $announceDeleted = new Announce();
	    $announceDeleted = $announceDeleted->remove($idAnnounce, $announceCategory);
	    echo json_encode($announceDeleted);
	}
?>
