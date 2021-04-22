<?php

$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$lphone=$_POST["l_phone"];
$result1=mysql_query("select l_lodgename from lodgeowner where l_phone='$lphone';");
$row = mysql_fetch_assoc($result1);
$lname=$row["l_lodgename"];
$result2=mysql_query("select f_name from feast where l_lodgename='$lphone';");
if(mysql_num_rows($result2)>0)
{
	$response["success"]=1;
	$response["values"] = array();
	while ($row1 = mysql_fetch_array($result2)) {
		$values=array();
        $values["fname"] = $row1["f_name"];
		$val=$values["fname"];
		$result3=mysql_query("select count(s_name)\"s_name\" from suggestions where s_name='$val' and l_phone='$lname';");
		$row2=mysql_fetch_assoc($result3);
		$values["count"]=$row2["s_name"];
		array_push($response["values"], $values);
		
    }
	echo json_encode($response);
}
else
{
	$response["success"]=0;
	echo json_encode($response);
}


?>