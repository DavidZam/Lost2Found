<?php
        ini_set('display_errors', 1);
        ini_set('display_startup_errors', 1);
        error_reporting(E_ALL);
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
	}
?>
