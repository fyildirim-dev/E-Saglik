<?php
include 'conn.php';
$responce=array();


$doctorName  = $_GET['doctorName'];
$sql="SELECT * FROM `users` WHERE ad = '" .$doctorName. "'";
$res=$con->query($sql);

if ($res->num_rows > 0) {
  while($row = $res->fetch_assoc()) {

  		$temp=array();

  		$sqlk="SELECT AVG(point) FROM `meetings` WHERE doctor = '" .$row["tc"] . "'";
			$resk=$con->query($sqlk);
			$puan = $resk->fetch_assoc();
			if (is_null($puan["AVG(point)"])) {
				continue;
			}
			$temp["tc"]=$row["tc"];
			$temp["ad"]=$row["ad"];
			$temp["soyad"]=$row["soyad"];
    	$temp["email"]=$row["email"];
    	$temp["point"]=round($puan["AVG(point)"], 1);

		array_push($responce,$temp);
  }
} else {
  echo "0 results";
}

$x = json_encode($responce); 
echo $x;


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