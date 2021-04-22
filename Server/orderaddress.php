<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$user_phone=$_POST["phone"];
$result=mysql_query("Select u_address from user where u_phone='$user_phone';");
if(mysql_num_rows($result) > 0)
{
	$response["success"]=1;
	$row = mysql_fetch_assoc($result);
	$response["address"]=$row["u_address"];
	echo json_encode($response);
}
else
{
	$response["success"]=0;
	$row = mysql_fetch_assoc($result);
	$response["address"]=$row["u_address"];
	echo json_encode($response);
}
?>