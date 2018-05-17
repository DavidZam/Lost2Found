<?php
	include('../dbFunctions.php');

	class User {
		// Devuelve un array con el resultado de la consulta.
		function select($email, $password) {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT * FROM usuario WHERE email = ?");
			mysqli_stmt_bind_param($sql, "s", $email);

			$query = $sql->execute();

			if(!$query)
    	        die();
			$result = $sql->store_result();

			$realresult = $sql->bind_result($id, $email, $name, $passHash);

			$rawdata = array();

			$sql->fetch();

			$correct = password_verify($password, $passHash);

			$rawdata['id'] = utf8_encode($id);
			$rawdata['email'] = utf8_encode($email);
			$rawdata['nombre'] = utf8_encode($name);
			$rawdata['correct'] = $correct;

            disconnectDB($connection);
	        return $rawdata;
		}

		function getId($email) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT id FROM usuario WHERE email = ?");
            mysqli_stmt_bind_param($sql, "s", $email);

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

        function getIdByName($name) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT id FROM usuario WHERE nombre = ?");
            mysqli_stmt_bind_param($sql, "s", $name);

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

        function getName($id) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT nombre FROM usuario WHERE id = ?");
            mysqli_stmt_bind_param($sql, "s", $id);

            $query = $sql->execute();

            if(!$query)
            	die();

            $result = $sql->store_result();

            $realresult = $sql->bind_result($name);

            $rawdata = array();

			$correct = $query;

            $sql->fetch();

            $rawdata['name'] = utf8_encode($name);
			$rawdata['correct'] = $correct;

            disconnectDB($connection);
            return $rawdata;
	    }	

		// Inserta un usuario en la base de datos
		function insert($email, $name, $password) {
	        $connection = connectDB();

			$passHash = password_hash($password, PASSWORD_BCRYPT);

	        $sql = mysqli_prepare($connection, "INSERT INTO usuario (id, email, nombre, contrasena) VALUES (null, ?, ?, ?)");

	        mysqli_stmt_bind_param($sql, "sss", $email, $name, $passHash);

	        $query = $sql->execute();

	        if(!$query)
	            echo "incorrect";
	        else
	            echo "correct";

	        disconnectDB($connection);
	        return $query;
    	}
		
		// Actualiza el nombre del usuario en la base de datos
		function updateName($nombre, $email){
			$connection = connectDB();
			$sql = mysqli_prepare($connection, "UPDATE usuario SET nombre=? WHERE email=?");
			mysqli_stmt_bind_param($sql, "ss", $nombre, $email);
			$query = $sql->execute();

			if(!$query)
				echo "incorrect";
			else
				echo "correct";

			disconnectDB($connection);
			return $query;
		}

		// Actualiza el email del usuario en la base de datos
		function updateEmail($email, $id){
			$connection = connectDB();
			$sql = mysqli_prepare($connection, "UPDATE usuario SET email=? WHERE id=?");
			mysqli_stmt_bind_param($sql, "si", $email, $id);
			$query = $sql->execute();

			if(!$query)
				echo "incorrect";
			else
				echo "correct";

			disconnectDB($connection);
			return $query;
		}
	
	
		// Actualiza la contraseña del usuario en la base de datos
		function updatePassword($pass, $email){
			$connection = connectDB();
			$passHash = password_hash($pass, PASSWORD_BCRYPT);
			$sql = mysqli_prepare($connection, "UPDATE usuario SET contrasena=? WHERE email=?");
			mysqli_stmt_bind_param($sql, "ss", $passHash, $email);
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
