<?php

$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$oid=$_POST["id"];
$result=mysql_query("select o_id,l_lodgename,o_useraddress,o_price,o_date,o_status from orders where o_id='$oid';");
if(mysql_num_rows($result)>0)
{
	while($row=mysql_fetch_array($result))
	{
	$response["success"]=1;
	$response["id"]=$row["o_id"];
	$response["lname"]=$row["l_lodgename"];
	$response["address"]=$row["o_useraddress"];
	$response["prize"]=$row["o_price"];
	$response["date"]=$row["o_date"];
	$response["status"]=$row["o_status"];
	echo json_encode($response);
}
}
else
{
	$response["success"]=0;
	$response["id"]=null;
	$response["lname"]=null;
	$response["address"]=null;
	$response["prize"]=null;
	$response["date"]=null;
	$response["status"]=null;
	echo json_encode($response);
}

?>