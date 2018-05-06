<?php
	/*ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);
	mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);*/
	include('../../dbFunctions.php');

	class MapPlace {

		/**
		 *	Insert a concrete place in the database.
		 *	@param announceType
		 *	@param currentTime
		 *	@param announceDateText
		 *	@param announceHourText
		 *	@param model
		 *	@param brand
		 *	@param color
		 *	@param announceCategorie
		 */
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
