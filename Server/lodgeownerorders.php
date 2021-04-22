<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$lphone=$_POST["lphone"];
$result1=mysql_query("select l_lodgename from lodgeowner where l_phone='$lphone';");
$row=mysql_fetch_assoc($result1);
$l_lname=$row["l_lodgename"];
$result2=mysql_query("select o_id,u_phone,o_date from orders where l_lodgename='$l_lname' and o_status='pending';");
if(mysql_num_rows($result2)>0)
{
	$response["success"] = 1;
    $response["values"] = array();
	while ($row = mysql_fetch_array($result2)) {
      
        $fname = array();
        $fname["id"] = $row["o_id"];
		$fname["name"]=$row["u_phone"];
		$fname["date"]=$row["o_date"];
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