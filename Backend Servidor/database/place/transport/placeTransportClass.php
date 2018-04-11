<?php
	include('../../dbFunctions.php');

	class TransportPlace {

		/**
		 *	Insert a place in the database.
		 *	@param announceType
		 *	@param currentTime
		 *	@param announceDateText
		 *	@param announceHourText
		 *	@param model
		 *	@param brand
		 *	@param color
		 *	@param announceCategorie
		 */
	    function insert($line, $station, $placeId) {
	        $connection = connectDB();
		//var_dump($line);
		//var_dump($station);
		//var_dump($placeId);
		$sql = mysqli_prepare($connection, "INSERT INTO lugar_transporte (idLugarTte, linea, estacion) VALUES (?, ?, ?)");
		//var_dump($sql);
		//echo $sql;
		mysqli_stmt_bind_param($sql, "iss", $placeId, $line, $station);

	        $query = $sql->execute();
		//var_dump($query);
	        if(!$query)
	            echo "incorrect";
	        else
	            echo "correct";

	        disconnectDB($connection);
	        return $query;
	    }
	}
?>
