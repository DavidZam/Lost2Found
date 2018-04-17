<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);
	include('../../dbFunctions.php');

	class TransportPlace {

                function selectStation($linea) {
                        $connection = connectDB();

						$lineaText = substr($linea, 11, -3);
                        $stmt = $connection->prepare("SELECT DISTINCT estacion FROM lugar_transporte WHERE linea = ?");
                        $stmt->bind_param('s', $lineaText);

                        $stmt->execute();

                        $result = $stmt->get_result();

                        while($row = $result->fetch_assoc())    {
                            $rows[] = $row;
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

		function select($tipoTte) {
			$connection = connectDB();

			$stmt = $connection->prepare("SELECT DISTINCT linea FROM lugar_transporte WHERE tipoTte = ?");
			$stmt->bind_param('s', $tipoTte);

			$stmt->execute();

			$result = $stmt->get_result();

			while($row = $result->fetch_assoc())    {
		            $rows[] = $row;
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

		function selectId($linea, $station) {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT * FROM lugar_transporte WHERE linea = ? AND estacion = ?");
			mysqli_stmt_bind_param($sql, "ss", $linea, $station);

			$query = $sql->execute();

			if(!$query)
            			die();

			$result = $sql->store_result();

			$realresult = $sql->bind_result($idLugarTte, $tipoTte, $linea, $estacion);

			$rawdata = array();

			$sql->fetch();

			$rawdata['id'] = utf8_encode($idLugarTte);
			$rawdata['tipoTte'] = utf8_encode($tipoTte);
			$rawdata['linea'] = utf8_encode($linea);
			$rawdata['estacion'] = $estacion;

	        	disconnectDB($connection);
		    	return $rawdata;
		}

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
