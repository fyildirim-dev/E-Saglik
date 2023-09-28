<?php
include 'conn.php';

$sendID  = $_GET['sendID'];
$receiverID  = $_GET['receiverID'];
$sql="SELECT * FROM `meetings` 	WHERE (patient='$sendID' AND doctor='$receiverID')";
$res=$con->query($sql);

if ($res->num_rows > 0) {
	echo "True";
} else {
  echo "False";
}


function utf8ize($d) {
	if (is_array($d)) {
		foreach ($d as $k => $v) 
		{
			$d[$k] = utf8ize($v);
		}
	} 
	else if (is_string ($d)) {
		return utf8_encode($d);
	}
	return $d;
}


?>