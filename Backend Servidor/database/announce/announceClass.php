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
		
		
		function getNumberSeekerAnnounces($categoria, $tipo) {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT DISTINCT COUNT(*) FROM anuncio_objeto WHERE nombreTabla = ? AND tipoAnuncio = ?");
			mysqli_stmt_bind_param($sql, "ss", $categoria, $tipo);

			$query = $sql->execute();

			if(!$query)
		            	die();
			$result = $sql->store_result();
			$realresult = $sql->bind_result($numAnnounces);
			$sql->fetch();
			
	        	disconnectDB($connection);
	        
		    	return $numAnnounces;
		}
		
		
		function getAnnouncesSeeker($categoria, $tipo) {

			$connection = connectDB();

			$stmt = $connection->prepare("SELECT DISTINCT * FROM `anuncio_objeto` WHERE nombreTabla = ? AND tipoAnuncio = ?");
			$stmt->bind_param('ss', $categoria, $tipo);

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
		
		function getNumberMatchAnnounces($idUser, $categoria, $tipo, $dia, $idObjeto) {
			$connection = connectDB();

			if($tipo == "Perdida"){
				$sql = mysqli_prepare($connection, "SELECT DISTINCT COUNT(*) FROM anuncio_objeto, Telefono t1 WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio >= ? AND t1.idObjeto = ? AND t1.marca = (SELECT t2.marca FROM Telefono t2 WHERE t2.idObjeto = ?)");
				
			}else{
				$sql = mysqli_prepare($connection, "SELECT DISTINCT COUNT(*) FROM anuncio_objeto, Telefono t1 WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio <= ? AND t1.idObjeto = ? AND t1.marca = (SELECT t2.marca FROM Telefono t2 WHERE t2.idObjeto = ?)");
			}
			
			
			mysqli_stmt_bind_param($sql, "ssssss", $idUser, $categoria, $tipo, $dia, $idObjeto, $idObjeto);

			$query = $sql->execute();

			if(!$query)
		            	die();
			$result = $sql->store_result();
			$realresult = $sql->bind_result($numAnnounces);
			$sql->fetch();
			
	        	disconnectDB($connection);
	        
		    	return $numAnnounces;
		}
		
		
		function getAnnouncesMatchJSON($idUser, $categoria, $tipo, $dia) {

			$connection = connectDB();

			if($tipo == 'Perdida'){
				$stmt = $connection->prepare("SELECT DISTINCT * FROM anuncio_objeto WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio >= ?");
				
			}else if($tipo == 'Hallazgo'){
				$stmt = $connection->prepare("SELECT DISTINCT * FROM anuncio_objeto WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio <= ?");
			}
			
			$stmt->bind_param('ssss', $idUser, $categoria, $tipo, $dia);

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
		
		
	}
?>
