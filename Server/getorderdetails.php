<?php

$response = array();
//require_once __DIR__ . '/db_connect.php';
//$db = new DB_CONNECT();
include "db_con.php";
$uname=$_POST["uname"];
$result=mysqli_query($conn,"select o_id,l_lodgename,o_date from orders where u_phone='$uname';");
if(mysqli_num_rows($result)>0)
{
	
	$response["success"] = 1;
    $response["values"] = array();
	while ($row = mysqli_fetch_array($result)) {
      
        $fname = array();
        $fname["id"] = $row["o_id"];
		$fname["name"]=$row["l_lodgename"];
		$fname["date"]=$row["o_date"];
		array_push($response["values"], $fname);
    }
    echo json_encode($response);
}
else
{
	$response["success"] = 0;
	echo json_encode($response);
}

?>