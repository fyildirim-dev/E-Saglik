<?php
include 'conn.php';

$sendID  = $_GET['sendID'];
$receiverID  = $_GET['receiverID'];
$mesaj  = $_GET['mesaj'];
$time  = $_GET['time'];
$sql="SELECT * FROM `messages` 	WHERE (sendID='$sendID' AND receiverID='$receiverID' AND mesaj='$mesaj' AND time='$time')";
$res=$con->query($sql);

if ($res->num_rows > 0) {
  while($row = $res->fetch_assoc()) {
  	$idcik = $row["id"];
	  $sqlk = "UPDATE `messages` SET `deleted` = '1' WHERE (id = $idcik)";
		$resk=$con->query($sqlk);
		if ($resk) {
			echo "True";
		}
  }
} 
else {
  echo "Mesaji bulamadim";
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