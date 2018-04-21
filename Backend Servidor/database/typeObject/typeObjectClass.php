<?php
        ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);
	include('../dbFunctions.php');

	class typeObject {
		function select() {
			$connection = connectDB();

			$stmt = $connection->prepare("SELECT nombreTabla FROM tipo");

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

        function getId() { // $typeAnnounce, $currentTime, $announceDay, $announceHour, $color, $categorie
            $connection = connectDB();

            // SELECT id FROM anuncio_objeto WHERE tipoAnuncio = 'Perdida' AND horaAnuncio = '14:01' AND diaAnuncio = '20/03/2018' AND horaPerdidaHallazgo = '15:30' AND color = '-16777216' AND nombreTabla = 'Cartera';
            //$sql = mysqli_prepare($connection, "SELECT id FROM anuncio_objeto WHERE tipoAnuncio = ? AND horaAnuncio = ? AND diaAnuncio = ? AND horaPerdidaHallazgo = ? AND color = ? AND nombreTabla = ?;");
            $sql = mysqli_prepare($connection, "SELECT MAX(id) FROM anuncio_objeto");

            $query = $sql->execute();

            if(!$query)
                die();

            $result = $sql->store_result();

            $realresult = $sql->bind_result($id);

            $rawdata = array();

            $correct = $query;

            $sql->fetch();

            $rawdata['id'] = utf8_encode($id);
            $rawdata['correct'] = $correct;

            disconnectDB($connection);
            return $rawdata;
        }

        function insert($objectId, $categorie, $param1, $param2, $param3, $param4) {
            $connection = connectDB();

            if(strcmp($categorie, "Tarjeta transporte") == 0) {
                $sql = mysqli_prepare($connection, "INSERT INTO `$categorie` (idObjeto, datosPropietario) VALUES (?, ?)");
                mysqli_stmt_bind_param($sql, "ss", $objectId, $param1);
            } else if(strcmp($categorie, "Telefono") == 0) {
                $sql = mysqli_prepare($connection, "INSERT INTO $categorie (idObjeto, marca, modelo, IMEI, tara) VALUES (?, ?, ?, ?, ?)");
                mysqli_stmt_bind_param($sql, "sssss", $objectId, $param1, $param2, $param3, $param4);
            } else {
		//$param2 = utf8_encode($param2);
		//var_dump($param2);
		var_dump($objectId);
		var_dump($param1);
		var_dump($param2);
                $sql = mysqli_prepare($connection, "INSERT INTO `$categorie` VALUES (?, ?, ?)");
                mysqli_stmt_bind_param($sql, "sss", $objectId, $param1, $param2);
            }

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
