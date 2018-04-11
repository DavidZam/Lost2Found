<?php
	include('../dbFunctions.php');

	class Place {

		function select() {
			$connection = connectDB();

			$sql = mysqli_prepare($connection, "SELECT * FROM lugar ORDER BY ID DESC LIMIT 1");

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
		function insert() {
	        $connection = connectDB();

			$sql = mysqli_prepare($connection, "INSERT INTO lugar (id) VALUES (null)");

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
