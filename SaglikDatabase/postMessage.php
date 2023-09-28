<?php

include 'conn.php';
$responce=array();

if(isset($_POST['sendID'],$_POST['receiverID'],$_POST['mesaj']))
{
	$sendID=$_POST['sendID'];
	$receiverID=$_POST['receiverID'];
	$mesaj=$_POST['mesaj'];
	

	$query="INSERT INTO `messages`(`sendID`, `receiverID`, `mesaj`) VALUES ('$sendID','$receiverID','$mesaj')";
	$res=mysqli_query($con,$query);
	if($res)
	{
		$responce["id"]=mysqli_insert_id($con);
		$responce["msg"]="Contact Inserted";
		$responce["responce"]="1";
	}
	else {
		$responce["id"]="NA";
		$responce["msg"]="Error Inserting Contact";
		$responce["responce"]="0";
	}
}


$x = json_encode(utf8ize($responce));
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