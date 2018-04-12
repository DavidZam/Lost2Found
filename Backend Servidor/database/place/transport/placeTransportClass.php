<?php
	include('../../dbFunctions.php');

	class TransportPlace {

		function select($tipoTte) {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT DISTINCT linea FROM lugar_transporte WHERE tipoTte = ?");
			mysqli_stmt_bind_param($sql, "s", $tipoTte);

			$query = $sql->execute();

			if(!$query)
	                die();

			$result = $sql->store_result();

			while($row = $result->fetch_array())    {
                $rows[] = $row;
            }
			
			$rawdata = array();

			$i = 0;

            foreach($rows as $row)    {
            	$rawdata[$i] = $rows[$i];
            	 $i++;
           	}
            var_dump($rawdata);

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
