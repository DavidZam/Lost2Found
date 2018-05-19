<?php
	include('../dbFunctions.php');
    require_once("encryptionClass.php");

	class Chat {
		function insert($chatTitle, $idUser1, $idUser2) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "INSERT INTO chat (id, nombreChat, idUsuario1, idUsuario2) VALUES (null, ?, ?, ?)");
            $result = mysqli_stmt_bind_param($sql, "sii", $chatTitle, $idUser1, $idUser2);

            $query = $sql->execute();

            if(!$query)
                echo "incorrect";
            else
                echo "correct";

            disconnectDB($connection);
            return $query;
        }

        function select($idUser1, $idUser2) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT * FROM chat WHERE idUsuario1 = ? AND idUsuario2 = ?");
            mysqli_stmt_bind_param($sql, "ss", $idUser1, $idUser2);

            $query = $sql->execute();

            if(!$query)
                die();

            $result = $sql->store_result();

            $realresult = $sql->bind_result($id, $nombreChat, $idUser1, $idUser2);

            $rawdata = array();

            $sql->fetch();

            $correct = $query;

            $rawdata['id'] = utf8_encode($id);
            $rawdata['nombreChat'] = utf8_encode($nombreChat);
            $rawdata['idUser1'] = utf8_encode($idUser1);
            $rawdata['idUser2'] = utf8_encode($idUser2);
            $rawdata['correct'] = $correct;

            disconnectDB($connection);
            return $rawdata;
        }

        function selectId($chatTitle, $idUser1, $idUser2) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT id FROM chat WHERE nombreChat = ? AND idUsuario1 = ? AND idUsuario2 = ?");
            mysqli_stmt_bind_param($sql, "sss", $chatTitle, $idUser1, $idUser2);

            $query = $sql->execute();

            if(!$query)
                die();

            $result = $sql->store_result();

            $realresult = $sql->bind_result($id);

            $rawdata = array();

            $sql->fetch();

            $correct = $query;

            $rawdata['id'] = utf8_encode($id);
            $rawdata['correct'] = $correct;

            disconnectDB($connection);
            return $rawdata;
        }

        function insertMsg($msgText, $msgHour, $idChat, $idUser) {
            $connection = connectDB();

            $encryptionObject = new Encryption();
            $textEncrypt = $encryptionObject->encrypt($msgText);

            $sql = mysqli_prepare($connection, "INSERT INTO msg (id, texto, horaMsg, idChat, idUsuario) VALUES (null, ?, ?, ?, ?)");
            $result = mysqli_stmt_bind_param($sql, "ssii", $textEncrypt, $msgHour, $idChat, $idUser);

            $query = $sql->execute();

            if(!$query)
                echo "incorrect";
            else
                echo "correct";

            disconnectDB($connection);
            return $query;
        }

        function getNumberChats($id) {
            $id2 = $id;
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT DISTINCT COUNT(*) FROM chat WHERE idUsuario1 = ? OR idUsuario2 = ?");
            mysqli_stmt_bind_param($sql, "ii", $id, $id2);

            $query = $sql->execute();

            if(!$query)
                die();

            $result = $sql->store_result();

            $realresult = $sql->bind_result($numChats);

            $sql->fetch();
            
            disconnectDB($connection);
        
            return $numChats;
        }

        function getChats($id) {
	        $id2 = $id;
            $connection = connectDB();

            $stmt = $connection->prepare("SELECT DISTINCT * FROM chat WHERE idUsuario1 = ? OR idUsuario2 = ?");
            $stmt->bind_param('ii', $id, $id2);

            $stmt->execute();

            $result = $stmt->get_result();

            while($row = $result->fetch_assoc())    {
                $rows[] = $row;
                $rows[] = ".";
            }

            $rawdata = array();
            $i = 0;

            foreach($rows as $row) {
                $rawdata[$i] = $rows[$i];
                $i++;
            }

            $result->close();

            disconnectDB($connection);
            return $rawdata;
        }

        function getNumberMsgs($id) {
            $connection = connectDB();

            $sql = mysqli_prepare($connection, "SELECT DISTINCT COUNT(*) FROM msg WHERE idChat = ?");
            mysqli_stmt_bind_param($sql, "i", $id);

            $query = $sql->execute();

            if(!$query)
                die();

            $result = $sql->store_result();

            $realresult = $sql->bind_result($numMsgs);

            $sql->fetch();

            disconnectDB($connection);

            return $numMsgs;
        }

        function getMsgs($id) {
            $connection = connectDB();

            $encryptionObject = new Encryption();

            $stmt = $connection->prepare("SELECT DISTINCT * FROM msg WHERE idChat = ?");
            $stmt->bind_param('i', $id);

            $stmt->execute();

            $result = $stmt->get_result();
	    $j = 0;
            while($row = $result->fetch_assoc()) {
                $rows[] = $row;
            }

	    $textDecrypted = array();
            $textEncrypted = array_column($rows, "texto");

            foreach($textEncrypted as $value) {
                  $textDecrypted[$j] = $encryptionObject->decrypt($value);
		  $reemplazo[$j] = array("texto" => $textDecrypted[$j]);
                  $j++;
            }

            $rawdata = array();
            $i = 0;

            foreach($rows as $row) {
                $rawdata[$i] = $rows[$i];
		$rawdata[$i]["texto"] = $reemplazo[$i]["texto"];
                $i++;
            }

            $result->close();

            disconnectDB($connection);
            return $rawdata;
        }

        function getUserId($textMsg, $hourMsg) {
            $connection = connectDB();

            $encryptionObject = new Encryption();
	    $textEncrypt = $encryptionObject->encrypt($textMsg);

            $sql = mysqli_prepare($connection, "SELECT idUsuario FROM msg WHERE texto = ? AND horaMsg = ?");
            $res = mysqli_stmt_bind_param($sql, 'ss', $textEncrypt, $hourMsg);

            $query = $sql->execute();

            if(!$query)
                die();

            $result = $sql->store_result();

            $realresult = $sql->bind_result($idUsuario);

            $rawdata = array();
            $sql->fetch();

            $correct = $query;
            

            $rawdata['id'] = utf8_encode($idUsuario);
            $rawdata['correct'] = $correct;

            disconnectDB($connection);
            return $rawdata;
        }
	}
?>
