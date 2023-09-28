<?php
include 'conn.php';
$responce=array();


$sendID  = $_GET['sendID'];
$receiverID  = $_GET['receiverID'];
$sql="SELECT * FROM `messages` 	WHERE (sendID='$sendID' AND receiverID='$receiverID') OR (sendID='$receiverID' AND receiverID='$sendID')";
$res=$con->query($sql);

if ($res->num_rows > 0) {
  // output data of each row
  while($row = $res->fetch_assoc()) {

  		$temp=array();
  		if ($row["deleted"] == 1) {
  				continue;
  		}
    	$temp["sendID"]=$row["sendID"];
			$temp["receiverID"]=$row["receiverID"];
			$temp["mesaj"]=$row["mesaj"];
			$temp["time"]=$row["time"];

		array_push($responce,$temp);
  }
} else {
  echo "0 results";
}

$x = json_encode($responce); #Converts Array into JSON
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