<?php
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
	include('../dbFunctions.php');

	class Chat {
		function insert($chatTitle, $idUser1, $idUser2, $lastMsg) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "INSERT INTO chat (id, nombreChat, idUsuario1, idUsuario2) VALUES (null, ?, ?, ?)");
            mysqli_stmt_bind_param($sql, "sii", $chatTitle, $idUser1, $idUser2);

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
