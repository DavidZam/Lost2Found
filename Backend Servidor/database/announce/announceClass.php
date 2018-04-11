<?php
	include('../dbFunctions.php');

	class Announce {
		/*
		function select($email, $password) {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT * FROM usuario WHERE email = ?");
			mysqli_stmt_bind_param($sql, "s", $email);

			$query = $sql->execute();

			if(!$query)
            	        die();
			$result = $sql->store_result();

			$realresult = $sql->bind_result($id, $email, $name, $passHash);

			$rawdata = array();

			$sql->fetch();

			$correct = password_verify($password, $passHash);

			$rawdata['email'] = utf8_encode($email);
			$rawdata['name'] = utf8_encode($name);
			$rawdata['correct'] = $correct;

	                disconnectDB($connection);
		        return $rawdata;
		}*/

		/**
		 *	Insert an announce in the database.
		 *	@param announceType
		 *	@param currentTime
		 *	@param announceDateText
		 *	@param announceHourText
		 *	@param model
		 *	@param brand
		 *	@param color
		 *	@param announceCategorie
		 */
		function insert($announceType, $currentTime, $announceDateText, $announceHourText, $model, $brand, $color, $idUser, $idPlace, $announceCategorie) {
	        $connection = connectDB();

		$sql = mysqli_prepare($connection, "INSERT INTO anuncio_objeto (id, tipoAnuncio, horaAnuncio, diaAnuncio, horaPerdidaHallazgo, modelo, marca, color, idUsuario, idLugar, nombreTabla) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		//var_dump($sql);
	        mysqli_stmt_bind_param($sql, "sssssssiis", $announceType, $currentTime, $announceDateText, $announceHourText, $model, $brand, $color, $idUser, $idPlace, $announceCategorie);

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
