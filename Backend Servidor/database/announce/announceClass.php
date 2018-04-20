<?php
	ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
	include('../dbFunctions.php');

	class Announce {
		
		function select($id) {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT DISTINCT COUNT(*) FROM anuncio_objeto WHERE idUsuario = ?");
			mysqli_stmt_bind_param($sql, "s", $id);

			$query = $sql->execute();

			if(!$query)
		            	die();
			$result = $sql->store_result();
			$realresult = $sql->bind_result($numAnnounces);
			$sql->fetch();
			
	        	disconnectDB($connection);
	        
		    	return $numAnnounces;
		}

		function getAnnounces($id) {

			$connection = connectDB();

			$stmt = $connection->prepare("SELECT DISTINCT * FROM `anuncio_objeto` WHERE idUsuario = ?");
			$stmt->bind_param('s', $id);

			$stmt->execute();

			$result = $stmt->get_result();

			while($row = $result->fetch_assoc())    {
		            $rows[] = $row;
			    $rows[] = ".";
            		}
			$rawdata = array();
			$i = 0;

            		foreach($rows as $row)    {
        	    		$rawdata[$i] = $rows[$i];
	            		$i++;
           		}

			$result->close();

		    	disconnectDB($connection);
	        	return $rawdata;
		}

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
		function insert($announceType, $currentTime, $announceDateText, $announceHourText, $color, $idUser, $idPlace, $announceCategorie) { //  $model, $brand,
	        $connection = connectDB();

		$sql = mysqli_prepare($connection, "INSERT INTO anuncio_objeto (id, tipoAnuncio, horaAnuncio, diaAnuncio, horaPerdidaHallazgo, color, idUsuario, idLugar, nombreTabla) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)");
		//var_dump($sql);
	        mysqli_stmt_bind_param($sql, "sssssiis", $announceType, $currentTime, $announceDateText, $announceHourText, $color, $idUser, $idPlace, $announceCategorie); // model, brand

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
