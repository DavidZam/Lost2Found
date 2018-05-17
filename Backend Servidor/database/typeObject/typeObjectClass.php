<?php
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

        function getId() {
            $connection = connectDB();

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

        function getData($id, $announceCategorie) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT * FROM `$announceCategorie` WHERE idObjeto = ?");
            mysqli_stmt_bind_param($sql, "s", $id);

            $query = $sql->execute();

            if(!$query)
                    die();

                    $result = $sql->store_result();

    	    if(strcmp($announceCategorie, "Tarjeta transporte") == 0) {
    	            $realresult = $sql->bind_result($id, $param1);

    		        $sql->fetch();
                    $rawdata = array();
                    $rawdata['param1'] = $param1;

            } else if(strcmp($announceCategorie, "Telefono") == 0) {
        		$realresult = $sql->bind_result($id, $param1, $param2, $param3);

        		$sql->fetch();
                $rawdata = array();
                $rawdata['param1'] = $param1;
        		$rawdata['param2'] = $param2;
        		$rawdata['param3'] = $param3;
            } else {
        		$realresult = $sql->bind_result($id, $param1, $param2);
        		$sql->fetch();
                $rawdata = array();
        		$rawdata['param1'] = $param1;
        		$rawdata['param2'] = $param2;
            }
            $correct = $query;
            $rawdata['correct'] = $correct;

            disconnectDB($connection);
            return $rawdata;
        }

        function insert($objectId, $categorie, $param1, $param2, $param3) {
            $connection = connectDB();

            if(strcmp($categorie, "Tarjeta transporte") == 0) {
                $sql = mysqli_prepare($connection, "INSERT INTO `$categorie` (idObjeto, datosPropietario) VALUES (?, ?)");
                mysqli_stmt_bind_param($sql, "ss", $objectId, $param1);
            } else if(strcmp($categorie, "Telefono") == 0) {
                $sql = mysqli_prepare($connection, "INSERT INTO $categorie (idObjeto, marca, modelo, tara) VALUES (?, ?, ?, ?)");
                mysqli_stmt_bind_param($sql, "ssss", $objectId, $param1, $param2, $param3);
            } else {
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

        function getCategory($category) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT nombreTabla FROM conversor WHERE tipoObjetoFrances = ?");
            mysqli_stmt_bind_param($sql, "s", $category);

            $query = $sql->execute();

            if(!$query)
                die();

            $result = $sql->store_result();

            $realresult = $sql->bind_result($category);

            $rawdata = array();

            $sql->fetch();

            if($sql->num_rows == 0) { // Otro
                $category = "Otro";
                $rawdata['category'] = utf8_encode($category);
            } else {
                $rawdata['category'] = utf8_encode($category);
            }

            $correct = $query;

            $rawdata['correct'] = $correct;

            disconnectDB($connection);
            return $rawdata;
        }
		
	}
?>
