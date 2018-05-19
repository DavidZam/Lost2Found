<?php
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

    		foreach($rows as $row) {
	    		$rawdata[$i] = $rows[$i];
        		$i++;
       		}

			$result->close();

	    	disconnectDB($connection);
        	return $rawdata;
		}

		// Inserta un anuncio en la base de datos
		function insert($announceType, $currentTime, $announceDateText, $announceHourText, $color, $idUser, $idPlace, $announceCategorie) {
	        $connection = connectDB();

			$sql = mysqli_prepare($connection, "INSERT INTO anuncio_objeto (id, tipoAnuncio, horaAnuncio, diaAnuncio, horaPerdidaHallazgo, color, idUsuario, idLugar, nombreTabla) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)");
	        mysqli_stmt_bind_param($sql, "sssssiis", $announceType, $currentTime, $announceDateText, $announceHourText, $color, $idUser, $idPlace, $announceCategorie);

	        $query = $sql->execute();
	        if(!$query)
	            echo "incorrect";
	        else
	            echo "correct";

	        disconnectDB($connection);
	        return $query;
	    }
		
		function remove($idAnnounce, $announceCategory) {
			$response = "";
			$idAnnounce2 = $idAnnounce;
	        $connection = connectDB();

			$sql = mysqli_prepare($connection, "DELETE FROM `$announceCategory` WHERE idObjeto = ?");
	        mysqli_stmt_bind_param($sql, "s", $idAnnounce);

	        $query = $sql->execute();

			if($query) {
	            $sql2 = mysqli_prepare($connection, "DELETE FROM anuncio_objeto WHERE id = ?");
	        	mysqli_stmt_bind_param($sql2, "s", $idAnnounce2);

	        	$query2 = $sql2->execute();
	        	if($query2)
	            $response = "correct";
	        } else {
	        	$response = "incorrect";
	        }	        

	        disconnectDB($connection);
	        return $response;
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
		
		function getNumberMatchAnnounces($idUser, $categoria, $tipo, $dia, $determinante) {
			$connection = connectDB();
					
			if($tipo == "Perdida") {
				if(strcmp($categoria, "Telefono") == 0 || strcmp($categoria, "Cartera") == 0) {
					$stmt = mysqli_prepare($connection, "SELECT COUNT(DISTINCT id) FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio >= ? AND id = idObjeto AND marca LIKE CONCAT('%', ?, '%')");
				} else if(strcmp($categoria, "Otro") == 0) {
					$stmt = mysqli_prepare($connection, "SELECT COUNT(DISTINCT id) FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio >= ? AND id = idObjeto AND  nombre LIKE CONCAT('%', ?, '%')");
				} else if(strcmp($categoria, "Tarjeta bancaria") == 0 || strcmp($categoria, "Tarjeta transporte") == 0) {
					$stmt = mysqli_prepare($connection, "SELECT COUNT(DISTINCT id) FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio >= ? AND id = idObjeto AND datosPropietario LIKE CONCAT('%', ?, '%')");
					$stmt->bind_param("sssss", $idUser, $categoria, $tipo, $dia, $determinante);
				}
			} else {
				if(strcmp($categoria, "Telefono") == 0 || strcmp($categoria, "Cartera") == 0) {
					$stmt = mysqli_prepare($connection, "SELECT COUNT(DISTINCT id) FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio <= ? AND id = idObjeto AND marca LIKE CONCAT('%', ?, '%')");
				}else if(strcmp($categoria, "Otro") == 0) {
					$stmt = mysqli_prepare($connection, "SELECT COUNT(DISTINCT id) FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio <= ? AND id = idObjeto AND nombre LIKE CONCAT('%', ?, '%')");
				}else if(strcmp($categoria, "Tarjeta bancaria") == 0 || strcmp($categoria, "Tarjeta transporte") == 0){
					$stmt = mysqli_prepare($connection, "SELECT COUNT(DISTINCT id) FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio <= ? AND id = idObjeto AND datosPropietario LIKE CONCAT('%', ?, '%')");
				}
			}
			
			mysqli_stmt_bind_param($stmt, "sssss", $idUser, $categoria, $tipo, $dia, $determinante);
			
			$query = $stmt->execute();

			if(!$query)
		            	die();
			$result = $stmt->store_result();
			$realresult = $stmt->bind_result($numAnnounces);
			$stmt->fetch();
			
        	disconnectDB($connection);
        
	    	return $numAnnounces;
		}
		
		function getAnnouncesMatchJSON($idUser, $categoria, $tipo, $dia, $determinante) {
			$connection = connectDB();

			if($tipo == 'Perdida') {
				if(strcmp($categoria, "Telefono") == 0 || strcmp($categoria, "Cartera") == 0) {
					$stmt = $connection->prepare("SELECT DISTINCT * FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio >= ? AND id = idObjeto AND marca LIKE CONCAT('%', ?, '%')");
				} else if(strcmp($categoria, "Otro") == 0) {
					$stmt = $connection->prepare("SELECT DISTINCT * FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio >= ? AND id = idObjeto AND nombre LIKE CONCAT('%', ?, '%')");
				} else if(strcmp($categoria, "Tarjeta bancaria") == 0 || strcmp($categoria, "Tarjeta transporte") == 0) {
					$stmt = $connection->prepare("SELECT DISTINCT * FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio >= ? AND id = idObjeto AND datosPropietario LIKE CONCAT('%', ?, '%')");
				}
			} else if($tipo == 'Hallazgo') {
				if(strcmp($categoria, "Telefono") == 0 || strcmp($categoria, "Cartera") == 0) {
					$stmt = $connection->prepare("SELECT DISTINCT * FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio <= ? AND id = idObjeto AND marca LIKE CONCAT('%', ?, '%')");
				} else if(strcmp($categoria, "Otro") == 0) {
					$stmt = $connection->prepare("SELECT DISTINCT * FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio <= ? AND id = idObjeto AND nombre LIKE CONCAT('%', ?, '%')");
				} else if(strcmp($categoria, "Tarjeta bancaria") == 0 || strcmp($categoria, "Tarjeta transporte") == 0) {
					$stmt = $connection->prepare("SELECT DISTINCT * FROM anuncio_objeto, `$categoria` WHERE idUsuario != ? AND nombreTabla = ? AND tipoAnuncio != ? AND diaAnuncio <= ? AND id = idObjeto AND datosPropietario LIKE CONCAT('%', ?, '%')");
				}
			}
			
			$stmt->bind_param('sssss', $idUser, $categoria, $tipo, $dia, $determinante);

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

		function getPlaceId($id) {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT idLugar FROM anuncio_objeto WHERE id = ?");
			mysqli_stmt_bind_param($sql, "i", $id);

			$query = $sql->execute();

			if(!$query)
		        die();

			$result = $sql->store_result();
			$realresult = $sql->bind_result($placeId);
			$sql->fetch();

			$rawdata = array();
            $correct = $query;
            $rawdata['param1'] = utf8_encode($placeId);
            $rawdata['correct'] = $correct;

	        disconnectDB($connection);
	        
		    return $rawdata;
		}		
		
	}
?>
