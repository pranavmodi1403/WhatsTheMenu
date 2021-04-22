<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$user_phone=$_POST["phone"];
$user_pass=$_POST["pass"];
if(mysql_query("update user set u_pass='$user_pass' where u_phone='$user_phone';"))
{
	$response["success"]=1;
	echo json_encode($response);
}
else
{
	$response["success"]=0;
	echo json_encode($response);
}
?>