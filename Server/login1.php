<?php
$response = array();

$user_phone=$_POST["phone"];
$user_pass=$_POST["pass"];
$newpass =md5($user_pass);
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$result1 = mysql_query("select * from user where u_phone='$user_phone' and u_pass='$newpass';");
$result2 = mysql_query("select * from lodgeowner where l_phone='$user_phone' and l_pass='$newpass';");
$result3=mysql_query("select * from lodgeownerrequest where l_phone='$user_phone' and l_pass='$newpass';");

   
        if (mysql_num_rows($result1) > 0) {
			$response["success"] = 1;
			echo json_encode($response);
		}
		else if(mysql_num_rows($result2) > 0){
         
            $response["success"] = 2;
			echo json_encode($response);
		}
		else if(mysql_num_rows($result3) > 0){
         
            $response["success"] = 3;
			echo json_encode($response);
		}
		else {
    
            $response["success"] = 0;
			echo json_encode($response);
		}

?>