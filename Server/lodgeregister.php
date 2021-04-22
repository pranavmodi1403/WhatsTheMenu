<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
//if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['phone']) && isset($_POST['pass'])) {
	$user_lname=$_POST["lname"];
	$user_liscence=$_POST["liscence"];
	$user_area=$_POST["area"];
	$user_ltime=$_POST["ltime"];
	$user_dtime=$_POST["dtime"];
    $user_name=$_POST["name"];
	$user_email=$_POST["email"];
	$user_phone=$_POST["phone"];
	$user_pass=$_POST["pass"];
	$newpass = md5($user_pass);
	
 
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
  $result1=mysql_query("select l_lodgename from lodgeownerrequest where l_lodgename='$user_lname';");
  $result2=mysql_query("select l_lodgename from lodgeownerrequest where l_phone='$user_phone';");
  $result3=mysql_query("select l_lodgename from lodgeowner where l_lodgename='$user_lname';");
  $result4=mysql_query("select l_lodgename from lodgeowner where l_phone='$user_phone';");
  $result5=mysql_query("select u_name from user where u_phone='$user_phone';");
  if(mysql_num_rows($result1)>0)
  {
	  $response["success"]=3;
	  echo json_encode($response);
  }
  else if(mysql_num_rows($result2)>0)
  {
		$response["success"]=2;
	  echo json_encode($response);
  }
  
  else if(mysql_num_rows($result3)>0)
  {
		$response["success"]=3;
	  echo json_encode($response);
  }
  
  else if(mysql_num_rows($result4)>0)
  {
		$response["success"]=2;
	  echo json_encode($response);
  }
  
  else if(mysql_num_rows($result5)>0)
  {
		$response["success"]=2;
	  echo json_encode($response);
  }
  else{
    if (mysql_query("insert into lodgeownerrequest(l_lodgename,l_liscence,l_lodgearea,l_lunchtime,l_dinnertime,l_ownername,l_email,l_phone,l_pass) values('$user_lname','$user_liscence','$user_area','$user_ltime','$user_dtime','$user_name','$user_email','$user_phone','$newpass');")) {
		
        $response["success"] = 1;
        echo json_encode($response);
    } else {
       
        $response["success"] = 0;
        echo json_encode($response);
		} 
  }
?>