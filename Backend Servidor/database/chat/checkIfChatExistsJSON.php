<?php
	include('../dbFunctions.php');

	if(isset($_POST["json"])) {
	    $json = $_POST["json"];
	    $json = urldecode($json);
	    $json = str_replace("\\", "",$json);
	    $jsonencode = json_decode($json);

        $idUser1 = $jsonencode[0]->idUser1;
	    $idUser2 = $jsonencode[0]->idUser2;

	    $connection = connectDB();

	    $sql = "SELECT * FROM chat WHERE idUsuario1 = '$idUser1' AND idUsuario2 = '$idUser2'";

	    $chatDB = $connection->query($sql);

	    if(mysqli_num_rows($chatDB) > 0) {
	       echo "exists";
	    } else {
	       echo "no exists";
	    }

        disconnectDB($connection);
	}
?>
