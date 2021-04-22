<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$oid=$_POST["id"];
if(mysql_query("update orders set o_status='delivered' where o_id='$oid';"))
{
	$respone["success"]=1;
	echo json_encode($respone);
}
else
{
	$response["success"]=0;
	echo json_encode($response);
}
?>