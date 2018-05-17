<?php
	 include('../dbFunctions.php');

	 if(isset($_POST["json"])) {
        $json = $_POST["json"];
        $json = urldecode($json);
        $json = str_replace("\\", "",$json);
        $jsonencode = json_decode($json);

        $email = $jsonencode[0]->email;
        $name = $jsonencode[0]->nombre;

        $connection = connectDB();
	    
	    $sql1 = "SELECT email FROM usuario WHERE email='$email'";
	    $sql2 = "SELECT nombre FROM usuario WHERE nombre='$name'";
	    
	    $emailDB = $connection->query($sql1);
	    $nameDB = $connection->query($sql2);

	    if(mysqli_num_rows($emailDB) > 0 || mysqli_num_rows($nameDB) > 0) {
	       echo "exists";
	    }

        disconnectDB($connection);
    }
?>
