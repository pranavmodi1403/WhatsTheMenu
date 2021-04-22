<?php

$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$lphone=$_POST["l_phone"];
$result1=mysql_query("select l_lodgename from lodgeowner where l_phone='$lphone';");
$row = mysql_fetch_assoc($result1);
$lname=$row["l_lodgename"];
if(mysql_query("delete from suggestions where l_phone='$lname';"))
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