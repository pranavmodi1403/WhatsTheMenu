<?php
$db_name="whatsthemenu";
$mysql_user="root";
$mysql_pass="";
$server_name="localhost";
$conn=mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
if($conn){
	echo "Connection done";
}
else
{
	echo "Connection not done";
}
?>