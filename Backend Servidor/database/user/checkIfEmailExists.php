<?php
 	include('../dbFunctions.php');
 	if(isset($_POST["json"])) {
        $json = $_POST["json"];
        $json = urldecode($json);
        $json = str_replace("\\", "",$json);
        $jsonencode = json_decode($json);

        $email = $jsonencode[0]->email;

        $connection = connectDB();
	    
	    $sql = "SELECT email FROM usuario WHERE email='$email'";
	    
	    $emailDB = $connection->query($sql);

	    if(mysqli_num_rows($emailDB) > 0) {
	       echo "exists";
	    }
	    
        disconnectDB($connection);
    }
?>
