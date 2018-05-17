<?php
    // Funcion que crea un objeto que representa la conexion con la base de datos
	function connectDB() { 
        $server = "localhost";
        $usuario = "root";
        $pass = "MissingX";
        $BD = "lost2found";
        $conexion = mysqli_connect($server, $usuario, $pass, $BD);
    	mysqli_set_charset($conexion, "UTF8");
    	return $conexion;
    } 

    // Funcion que cierra la conexion con la base de datos
    function disconnectDB($conexion) {
        $close = mysqli_close($conexion);
        return $close;         
    }
?>
