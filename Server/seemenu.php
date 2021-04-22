<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$user_phone=$_POST["phone"];

	$result=mysql_query("select l_menu,l_lodgearea,l_lunchtime,l_dinnertime,l_menuprice from lodgeowner where l_lodgename='$user_phone';");
	if (mysql_num_rows($result) > 0) {
		while($row =mysql_fetch_assoc($result)) {
		$response["success"]=1;
		$response["menu"]=$row["l_menu"];
		$response["area"]=$row["l_lodgearea"];
		$response["ltime"]=$row["l_lunchtime"];
		$response["dtime"]=$row["l_dinnertime"];
		$response["price"]=$row["l_menuprice"];
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
		$response["price"]=0;
		echo json_encode($response);
	}
?>