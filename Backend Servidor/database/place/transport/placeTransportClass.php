<?php
	ini_set('display_errors', 1);
	ini_set('display_startup_errors', 1);
	error_reporting(E_ALL);
	include('../../dbFunctions.php');

	class TransportPlace {

                function selectStation($linea) {
                        $connection = connectDB();

                        //var_dump($linea);
			//echo $linea;
			//echo $linea[12];
			$lineaText = substr($linea, 11, -3);
			//$lineaText2 = substr($lineaText, 0, -4);
			//var_dump($lineaText);
                        $stmt = $connection->prepare("SELECT DISTINCT estacion FROM lugar_transporte WHERE linea = ?");
                        $stmt->bind_param('s', $lineaText);

                        $stmt->execute();
                        //var_dump($stmt);
                        $result = $stmt->get_result();
                        //var_dump($result);
                        while($row = $result->fetch_assoc())    {
                            $rows[] = $row;
                        }
                        $rawdata = array();
                        $i = 0;
			//echo $rawdata;
			//var_dump($rawdata);
                        foreach($rows as $row)    {
                                $rawdata[$i] = $rows[$i];
                                $i++;
                        }
                        //var_dump($rawdata);

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
