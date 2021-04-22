<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$lphone=$_POST["phone"];

	$result=mysql_query("select l_menu,l_lodgearea,l_lunchtime,l_dinnertime from lodgeowner where l_phone='$lphone';");
	if (mysql_num_rows($result) > 0) {
		while($row =mysql_fetch_assoc($result)) {
		$response["success"]=1;
		$response["menu"]=$row["l_menu"];
		$response["area"]=$row["l_lodgearea"];
		$response["ltime"]=$row["l_lunchtime"];
		$response["dtime"]=$row["l_dinnertime"];
		echo json_encode($response);
		}
	}
	else
	{
		$response["success"]=0;
		$response["menu"]=0;
		$response["area"]=0;
		$response["ltime"]=0;
		$response["dtime"]=0;
		echo json_encode($response);
	}
?>