<?php

$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$l_lname=$_POST["lname"];
$u_name=$_POST["uname"];
$u_address=$_POST["address"];
$result=mysql_query("select l_menuprice from lodgeowner where l_lodgename='$l_lname';");
$row = mysql_fetch_assoc($result);
$price=$row["l_menuprice"];
if(mysql_query("insert into orders(l_lodgename,o_useraddress,u_phone,o_price,o_status) values('$l_lname','$u_address','$u_name','$price','pending');"))
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