<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$user_phone=$_POST["phone"];
$user_menu=$_POST["lmenu"];
$user_price=$_POST["lmenuprice"];
if(mysql_query("update lodgeowner set l_menu='$user_menu' where l_phone='$user_phone';"))
{
	if(mysql_query("update lodgeowner set l_menuprice='$user_price' where l_phone='$user_phone';"))
	{
		$response["success"]=1;
		echo json_encode($response);
	}
	else{
		$response["success"]=0;
	echo json_encode($response);
	}
}
else
{
	$response["success"]=0;
	echo json_encode($response);
}
?>