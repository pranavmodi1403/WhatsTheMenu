<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$user_phone=$_POST["phone"];
$user_birthdate=$_POST["u_birthdate"];
$user_address=$_POST["u_address"];
if(mysql_query("update user set u_birthdate='$user_birthdate' where u_phone='$user_phone';"))
{
	if(mysql_query("update user set u_address='$user_address' where u_phone='$user_phone';")){
	$response["success"]=1;
	echo json_encode($response);
	}
}
else
{
	$response["success"]=0;
	echo json_encode($response);
}
?>