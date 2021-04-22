<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$response=array();
if(mysql_query("insert into lodgeowner (select * from lodgeownerrequest);"))
{
	mysql_query("delete from lodgeownerrequest;");

	$respone["success"]=1;
	echo json_encode($respone);
	
}
else
{
	$response["success"]=0;
	echo json_encode($response);
}
?>
