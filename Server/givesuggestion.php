<?php

$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$fname=$_POST["fname"];
$lname=$_POST["lphone"];
$uname=$_POST["uphone"];
$result=mysql_query("select l_phone,u_phone from suggestions where l_phone='$lname' and u_phone='$uname';");
if(mysql_num_rows($result)==0)
{
	if(mysql_query("insert into suggestions(s_name,l_phone,u_phone) values('$fname','$lname','$uname');"))
	{
		$response["success"]=1;
		echo json_encode($response);
	}
	else
	{
		$response["success"]=0;
		echo json_encode($response);
	}
}
else
{
	$response["success"]=2;
	echo json_encode($response);
}
?>