<?php
require "db_con.php";
$user_phone=$_POST["phone"];
$user_pass=$_POST["pass"];
$sql_query="select * from user where u_phone like '$user_phone' and u_pass like '$user_pass';";
$result=mysqli_query($conn,$sql_query);
if(mysqli_num_rows($result)>0)
{
	echo "login success";
}
else
{
	echo "login not success";
}
?>