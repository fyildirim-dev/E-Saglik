<?php
include 'conn.php';
$responce=array();


$policlinic  = $_GET['policlinic'];
$sql="SELECT * FROM `policlinics`  WHERE (policlinic='$policlinic')";
$res=$con->query($sql);

if ($res->num_rows > 0) {
  // output data of each row
  while($row = $res->fetch_assoc()) {
        $sqll = "select * from users where tc = '" . $row["doctor"] . "'";
        $result = $con->query($sqll);
        $roww = $result->fetch_assoc();
        
        $temp=array();
        $temp["tc"]=$roww["tc"];
        $temp["ad"]=$roww["ad"];
        $temp["soyad"]=$roww["soyad"];
        $temp["email"]=$roww["email"];

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