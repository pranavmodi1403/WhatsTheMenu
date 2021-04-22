<?php

$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$lname=$_POST["phone"];
$result=mysql_query("select l_phone from lodgeowner where l_lodgename='$lname';");
$row = mysql_fetch_assoc($result);
$lphone=$row["l_phone"];
$result1=mysql_query("select f_name from feast where l_lodgename='$lphone';");
if(mysql_num_rows($result1)>0)
{
	
	$response["success"] = 1;
    $response["values"] = array();
	while ($row = mysql_fetch_array($result1)) {
      
        $fname = array();
        $fname["id"] = $row["f_name"];
		array_push($response["values"], $fname);
    }
    echo json_encode($response);
}
else
{
	$response["success"]=0;
	echo json_encode($response);
}




?>