<?php
	ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    error_reporting(E_ALL);
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

	    	function getName($id) {
		$id2 = $id;
		$id3 = $id;
            	$connection = connectDB();

            	$sql = mysqli_prepare($connection, "SELECT * FROM lugar_concreto WHERE idLugar = ?");
            	mysqli_stmt_bind_param($sql, "s", $id);

            	$query = $sql->execute();

            	if(!$query) {
            		die();
            	} else {
			$result = $sql->store_result();

			$realresult = $sql->bind_result($id, $param1, $param2, $param3);

                        $sql->fetch();

			if($sql->num_rows == 0) { // Transporte
				$sql2 = mysqli_prepare($connection, "SELECT * FROM lugar_transporte WHERE idLugarTte = ?");
            			mysqli_stmt_bind_param($sql2, "s", $id2);

            			$query2 = $sql2->execute();

				if(!$query2)
					die();

            			$result2 = $sql2->store_result();

				$realresult2 = $sql2->bind_result($id2, $param12, $param22, $param32);

                        	$sql2->fetch();

				if($sql2->num_rows > 0) {
					$rawdata = array();
                                	$correct = $query2;
                                	$rawdata['param1'] = utf8_encode($param12);
                                	if(utf8_encode($param22) != null)
                                        $rawdata['param2'] = utf8_encode($param22);
                                	$rawdata['param3'] = utf8_encode($param32);
                                	$rawdata['correct'] = $correct;
				} else { // Mapa
					$sql3 = mysqli_prepare($connection, "SELECT * FROM lugar_mapa WHERE idLugar = ?");
                                	mysqli_stmt_bind_param($sql3, "s", $id3);

                                	$query3 = $sql3->execute();

					if(!$query3)
                                        die();

                                	$result3 = $sql3->store_result();

                                	$realresult3 = $sql3->bind_result($id3, $param13, $param23);

                                	$sql3->fetch();

					if($sql3->num_rows > 0) {
                                        $rawdata = array();
                                        $correct = $query3;
                                        $rawdata['param1'] = utf8_encode($param13);
                                        $rawdata['param2'] = utf8_encode($param23);
                                        $rawdata['correct'] = $correct;
					}
				}

				/*$rawdata = array();
				$correct = $query2;
				$rawdata['param1'] = utf8_encode($param12);
                        	if(utf8_encode($param22) != null)
                                	$rawdata['param2'] = utf8_encode($param22);
                        	$rawdata['param3'] = utf8_encode($param32);
                               	$rawdata['correct'] = $correct;*/
                        } else {
				$rawdata = array();
				$correct = $query;
				$rawdata['param1'] = utf8_encode($param1);
                               	$rawdata['param2'] = utf8_encode($param2);
                        	$rawdata['param3'] = utf8_encode($param3);
                                $rawdata['correct'] = $correct;
			}
		}
            	disconnectDB($connection);
            	return $rawdata;
	    }
	}
?>
