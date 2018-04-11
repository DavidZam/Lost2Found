<?php
	include('../../dbFunctions.php');

	class TransportPlace {

		function select($tipoTte) {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT DISTINCT linea FROM lugar_transporte WHERE tipoTte = ?;");
			mysqli_stmt_bind_param($sql, "s", $tipoTte);

			$query = $sql->execute();

			if(!$query)
	                die();

			$result = $sql->store_result();

			$realresult = $sql->bind_result($linea);
			
			//$sql->fetch();
			
			$rawdata = array();
			

			//$rawdata = $realresult->fetchAll();

			/*while($row = mysqli_fetch_array($result, MYSQL_ASSOC)) {
				$rawdata[] = $row;
			}*/

			//$correct = $query;

			$sql->fetch();

			while($row = mysqli_fetch_array($result)) {
                                //foreach($row as $key => $value) {
				echo "aqui";
                                    $rawdata['linea'] = utf8_encode($linea);
                                //}
                        }

			//var_dump($rawdata);

			/*foreach($row as $key => $value) {
				$rawdata[] = utf8_encode($row);
			}*/
			
			//$rawdata['correct'] = $correct;

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
