<?php
$response = array();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$l_lname=$_POST["phone"];
$result = mysql_query("select f_name FROM feast where l_lodgename='$l_lname';");
 
// check for empty result
if (mysql_num_rows($result) > 0) {
 
	$response["success"] = 1;
    $response["feastname"] = array();
 
    while ($row = mysql_fetch_array($result)) {
      
        $fname = array();
        $fname["name"] = $row["f_name"];       
		array_push($response["feastname"], $fname);
    }
    // success
    
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    // echo no users JSON
    echo json_encode($response);
}


?>