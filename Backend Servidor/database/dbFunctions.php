<?php
	// Function to create a object representing a conection to the database.
	function connectDB() { 
        $server = "localhost";
        $usuario = "root";
        $pass = "MissingX";
        $BD = "lost2found";
        $conexion = mysqli_connect($server, $usuario, $pass, $BD);

        return $conexion;
    } 

    // Disconnect to the database.
    function disconnectDB($conexion) {
            $close = mysqli_close($conexion);
            return $close;         
    }
?>
