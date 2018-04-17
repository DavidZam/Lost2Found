<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);
	include('../../dbFunctions.php');

	class ConcretePlace {

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
	    function insert($id, $street, $number, $postalCode) {
	        $connection = connectDB();
		$sql = mysqli_prepare($connection, "INSERT INTO lugar_concreto (idLugar, calle, numero, codigoPostal) VALUES (?, ?, ?, ?)");
		mysqli_stmt_bind_param($sql, "ssss", $id, $street, $number, $postalCode);

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
