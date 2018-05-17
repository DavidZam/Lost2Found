<?php
	include('../../dbFunctions.php');

	class MapPlace {

		// Inserta un lugar del mapa (latitud, longitud) en la base de datos
	    function insert($id, $latitude, $longitude) {
	        $connection = connectDB();
			$sql = mysqli_prepare($connection, "INSERT INTO lugar_mapa (idLugar, latitud, longitud) VALUES (?, ?, ?)");
			mysqli_stmt_bind_param($sql, "sdd", $id, $latitude, $longitude);

			$query = $sql->execute();
			if(!$query)
	            	echo "incorrect";
	        else
	            	echo "correct";

	        disconnectDB($connection);
	        return $query;
	    }
	}
?>
