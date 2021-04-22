<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$l_lname=$_POST["phone"];
$fname=$_POST["name"];
$result=mysql_query("select f_id from feast where l_lodgename='$l_lname' and f_name='$fname';");
$row = mysql_fetch_assoc($result);
$feastid=$row["f_id"];
if(mysql_query("Delete from feast where f_id='$feastid';"))
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