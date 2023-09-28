<?php
include 'conn.php';
$responce=array();

$sql="SELECT * FROM `users` WHERE doctor=0";
$res=$con->query($sql);

if ($res->num_rows > 0) {
  while($row = $res->fetch_assoc()) {

  		$temp=array();
			$temp["tc"]=$row["tc"];
    	$temp["ad"]=$row["ad"];
			$temp["soyad"]=$row["soyad"];
			$temp["email"]=$row["email"];

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