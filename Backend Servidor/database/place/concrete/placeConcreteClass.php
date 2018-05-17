<?php
	include('../../dbFunctions.php');

	class ConcretePlace {

		// Inserta un lugar concreto (direccion) en la base de datos
	    function insert($id, $street, $number, $postalCode) {
	        $connection = connectDB();
			$sql = mysqli_prepare($connection, "INSERT INTO lugar_concreto (idLugar, calle, numero, codigoPostal) VALUES (?, ?, ?, ?)");
			mysqli_stmt_bind_param($sql, "ssss", $id, $street, $number, $postalCode);

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
