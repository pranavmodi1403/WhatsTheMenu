<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$f_name=$_POST["feast"];
$l_lodge=$_POST["name"];
$result=mysql_query("select f_name from feast where f_name='$f_name' and l_lodgename='$l_lodge';");
if(mysql_num_rows($result) > 0)
{
	$response["success"]=2;
	echo json_encode($response);
}
else
{
	if(mysql_query("insert into feast(f_name,l_lodgename) values('$f_name','$l_lodge');"))
	{
		$response["success"]=1;
		echo json_encode($response);
	}
	else{
		$response["success"]=0;
		echo json_encode($response);
	}
}
?>