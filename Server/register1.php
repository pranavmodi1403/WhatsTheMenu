<?php
 
$response = array();
 
$user_name=$_POST["name"];
$user_email=$_POST["email"];
$user_phone=$_POST["phone"];
$user_pass=$_POST["pass"];
$newpass = md5($user_pass);
require_once __DIR__ . '/db_connect.php';  
$db = new DB_CONNECT();
$result1=mysql_query("select u_name from user where u_phone='$user_phone';");
$result2=mysql_query("select l_lodgename from lodgeowner where l_phone='$user_phone';");
$result3=mysql_query("select l_lodgename from lodgeownerrequest where l_phone='$user_phone';");
if(mysql_num_rows($result1)>0)
{
	 $response["success"]=2;
	 echo json_encode($response);
}
else if(mysql_num_rows($result2)>0)
{
	$response["success"]=2;
	echo json_encode($response); 
}
else if(mysql_num_rows($result3)>0)
{
	$response["success"]=2;
	echo json_encode($response); 
}
else{
    $result = mysql_query("insert into user(u_name,u_email,u_phone,u_pass) values('$user_name','$user_email','$user_phone','$newpass');");
 
    if ($result) {
        $response["success"] = 1;
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        echo json_encode($response);
		}
	 
}
?>