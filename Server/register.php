<?php
require "db_con.php";
$user_name=$_POST["name"];
$user_email=$_POST["email"];
$user_phone=$_POST["phone"];
$user_pass=$_POST["pass"];
$sql_query="insert into user(u_name,u_email,u_phone,u_pass) values('$user_name','$user_email','$user_phone','$user_pass');";
if(mysqli_query($conn,$sql_query))
{
	$response["success"] = 1;
	$response["message"] = "Data Inserted";
	echo json_encode($response);
}
else
{
	
	$response["success"] = 0;
	$response["message"] = "Data is not Inserted";
	echo json_encode($response);
}
?>